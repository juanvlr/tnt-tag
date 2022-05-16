package fr.juanvalero.tnttag.api.game;

import fr.juanvalero.tnttag.api.configuration.Configuration;
import fr.juanvalero.tnttag.api.game.display.GameComponents;
import fr.juanvalero.tnttag.api.game.display.ScoreboardCreditUpdater;
import fr.juanvalero.tnttag.api.game.event.EventService;
import fr.juanvalero.tnttag.api.game.explosion.ExplosionRunnableFactory;
import fr.juanvalero.tnttag.api.game.item.ItemService;
import fr.juanvalero.tnttag.api.game.player.PlayerCollection;
import fr.juanvalero.tnttag.api.game.player.PlayerCollectionImpl;
import fr.juanvalero.tnttag.api.game.start.AutoStartRunnableFactory;
import fr.juanvalero.tnttag.api.scoreboard.Scoreboard;
import fr.juanvalero.tnttag.api.scoreboard.ScoreboardService;
import fr.juanvalero.tnttag.api.utils.item.ItemStackBuilder;
import fr.juanvalero.tnttag.api.utils.scheduler.TickUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import javax.inject.Inject;
import java.util.List;
import java.util.Random;

/**
 * Default {@link Game} implementation.
 */
@SuppressWarnings("ConstantConditions")
public class GameImpl implements Game {

    @Inject
    private Configuration configuration;

    private final ScoreboardService scoreboardService;
    private final ScoreboardCreditUpdater scoreboardCreditUpdater;
    private final Plugin plugin;
    private final ExplosionRunnableFactory explosionRunnableFactory;
    private final AutoStartRunnableFactory autoStartRunnableFactory;
    private final ItemService itemService;
    private final EventService eventService;
    private final PlayerCollection players;
    private PlayerCollection taggedPlayers;
    private GameState state;
    private BukkitRunnable explosionRunnable;
    private BukkitRunnable autoStartRunnable;

    @Inject
    public GameImpl(ScoreboardService scoreboardService,
                    ScoreboardCreditUpdater scoreboardCreditUpdater,
                    Plugin plugin,
                    ExplosionRunnableFactory explosionRunnableFactory,
                    AutoStartRunnableFactory autoStartRunnableFactory,
                    ItemService itemService,
                    EventService eventService) {
        this.scoreboardService = scoreboardService;
        this.scoreboardCreditUpdater = scoreboardCreditUpdater;
        this.plugin = plugin;
        this.explosionRunnableFactory = explosionRunnableFactory;
        this.autoStartRunnableFactory = autoStartRunnableFactory;
        this.itemService = itemService;
        this.eventService = eventService;
        this.players = new PlayerCollectionImpl();
        this.taggedPlayers = new PlayerCollectionImpl();
        this.state = GameState.WAITING;
    }

    @Override
    public void start() {
        if (this.configuration.allowItems()) {
            int itemAmount = this.configuration.getItemAmount();
            List<ItemStack> items = this.itemService.getRandomItems(itemAmount);

            this.players.forEach(player -> {
                Inventory inventory = player.getInventory();

                for (int slot = 0; slot < itemAmount; slot++) {
                    inventory.setItem(slot, items.get(slot));
                }

                player.updateInventory();
            });
        }

        this.players.forEach(player -> {
            Scoreboard scoreboard = this.scoreboardService.getScoreboard(player);
            scoreboard.updateLine(4, GameComponents.getAlivePlayerAmountMessage(this.players.count()));

            player.showTitle(GameComponents.getStartMessage());

            this.configuration.getStartLocation().ifPresent(player::teleport);
        });

        this.state = GameState.IN_GAME;

        this.nextRound();
    }

    @Override
    public boolean hasStarted() {
        return this.state == GameState.IN_GAME;
    }

    @Override
    public void startDelayed() {
        if (this.isRegularStarting()) {
            // The game was already starting, it means that someone speeds up the time
            // We cancel the runnable before running the new one
            this.autoStartRunnable.cancel();
        }

        this.autoStartRunnable = this.autoStartRunnableFactory.createAutoStartRunnable(10);
        this.autoStartRunnable.runTaskTimer(this.plugin, 0L, TickUtils.TICKS_PER_SECOND);

        this.state = GameState.FAST_STARTING;
    }

    @Override
    public boolean isFastStarting() {
        return this.state == GameState.FAST_STARTING;
    }

    @Override
    public void stop() {
        this.explosionRunnable.cancel();

        Bukkit.getOnlinePlayers().forEach(player -> this.scoreboardService.getScoreboard(player).eraseLines(3, 4, 5, 6));

        this.state = GameState.STOPPED;
    }

    @Override
    public boolean isClosed() {
        return this.hasStarted() || this.state == GameState.STOPPED || this.players.count() == GameConstants.MAXIMUM_PLAYER_COUNT;
    }

    @Override
    public void tag(Player damager, Player defender) {
        if (!this.taggedPlayers.contains(damager)) {
            return;
        }

        if (this.taggedPlayers.contains(defender)) {
            return;
        }

        // Defender got tagged

        this.taggedPlayers.remove(damager);

        damager.getInventory().setHelmet(null);
        damager.getInventory().setItem(8, null);

        AttributeInstance damagerSpeedAttribute = damager.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED);
        damagerSpeedAttribute.setBaseValue(damagerSpeedAttribute.getBaseValue()); // Resets his speed

        damager.clearTitle(); // Just in case

        this.taggedPlayers.add(defender);
        this.tagPlayer(defender);
    }

    @Override
    public void blowUp() {
        this.taggedPlayers.forEach(victim -> {
            this.kill(victim, GameComponents.getExplosionMessage(victim));

            if (this.configuration.isTntDestructive()) {
                victim.getLocation().getWorld().spawnEntity(victim.getLocation(), EntityType.PRIMED_TNT);
            }
        });
    }

    @Override
    public void join(Player joiner) {
        this.players.add(joiner);

        joiner.setGameMode(GameMode.ADVENTURE);
        this.clean(joiner);

        this.scoreboardService.createScoreboard(joiner, GameComponents.getGameTitle());

        this.scoreboardCreditUpdater.addPlayer(joiner);

        this.players.forEach(player -> {
                    player.sendMessage(GameComponents.getConnectionMessage(joiner, this.players.count()));

                    // We don't want to update the scoreboard if the game is starting
                    // Regular starting : the minimum amount of players has already been reached
                    // Fast starting : we don't need a minimum amount of players
                    if (!this.isStarting()) {
                        // Update the amount of missing players
                        this.updateMissingPlayerCount(player);
                    }
                }
        );

        if (!this.isFastStarting() && this.players.count() == GameConstants.MINIMUM_PLAYER_COUNT) {
            // We now have the needed amount of players to start the game
            this.state = GameState.REGULAR_STARTING;

            this.autoStartRunnable = this.autoStartRunnableFactory.createAutoStartRunnable(60);
            this.autoStartRunnable.runTaskTimer(this.plugin, 0L, TickUtils.TICKS_PER_SECOND);
        }

        this.configuration.getLobbyLocation().ifPresent(joiner::teleport);
    }

    @Override
    public void leave(Player leaver) {
        if (this.hasStarted()) {
            this.kill(leaver, GameComponents.getDisconnectionDeathMessage(leaver));
        } else {
            this.players.remove(leaver);

            if (this.state != GameState.STOPPED) {
                this.players.forEach(player ->
                        player.sendMessage(GameComponents.getDisconnectionMessage(leaver, this.players.count()))
                );

                if (this.state == GameState.WAITING) {
                    this.players.forEach(this::updateMissingPlayerCount);
                } else if ((this.isFastStarting() && this.players.count() == 1) || (this.isRegularStarting() && this.players.count() < GameConstants.MINIMUM_PLAYER_COUNT)) {
                    // There are not enough players left to start the game
                    this.autoStartRunnable.cancel();
                    this.players.forEach(player -> {
                        this.updateMissingPlayerCount(player);
                        player.clearTitle();
                    }); // Reset the scoreboard to the waiting state and the title if any
                    this.state = GameState.WAITING;
                }
            }
        }

        this.scoreboardService.deleteScoreboard(leaver);
        this.scoreboardCreditUpdater.removePlayer(leaver);
    }

    @Override
    public PlayerCollection getPlayers() {
        return this.players;
    }

    @Override
    public PlayerCollection getTaggedPlayers() {
        return this.taggedPlayers;
    }

    @Override
    public boolean isTagged(Player player) {
        return this.taggedPlayers.contains(player);
    }

    /**
     * Checks if the game is regularly starting.
     *
     * @return {@code true} if the game is regularly starting, {@code false} otherwise
     */
    private boolean isRegularStarting() {
        return this.state == GameState.REGULAR_STARTING;
    }

    /**
     * Checks if the game is starting.
     *
     * @return {@code true} if the game is starting, {@code false} otherwise
     */
    private boolean isStarting() {
        return this.isRegularStarting() || this.isFastStarting();
    }

    /**
     * Cleans a player.
     *
     * @param player The player to clean
     */
    private void clean(Player player) {
        player.clearTitle();

        player.getActivePotionEffects().forEach(effect -> player.removePotionEffect(effect.getType()));

        player.getInventory().clear();

        player.setLevel(0);
        player.setExp(0.f);

        //noinspection ConstantConditions
        player.setHealth(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getDefaultValue());
        player.setFoodLevel(20);
    }

    /**
     * Updates the number of missing players before starting the game.
     *
     * @param player The player for which to display the number of missing players
     */
    private void updateMissingPlayerCount(Player player) {
        this.scoreboardService.getScoreboard(player)
                .updateLine(4, GameComponents.getMissingPlayerCountMessage(this.players.count()));
    }

    /**
     * Jumps to the next round by choosing tagged players and starting the explosion timer.
     */
    private void nextRound() {
        int taggedPlayersAmount = this.players.count() / 2;
        this.taggedPlayers = this.players.getRandoms(taggedPlayersAmount);

        this.taggedPlayers.forEach(this::tagPlayer);

        this.explosionRunnable = this.explosionRunnableFactory.createExplosionRunnable();
        this.explosionRunnable.runTaskTimer(this.plugin, 0L, TickUtils.TICKS_PER_SECOND);

        if (this.configuration.isEventEnabled()) {
            if (new Random().nextInt(2) == 0) {
                this.eventService.getRandomEvent().run();
            }
        }
    }

    /**
     * Kills a player and checks for a win.
     *
     * @param victim The player to kill
     * @param reason The reason why the player was killed
     */
    private void kill(Player victim, Component reason) {
        victim.setGameMode(GameMode.SPECTATOR);
        this.clean(victim);

        Bukkit.getOnlinePlayers().forEach(player -> {
                    player.sendMessage(reason);
                    this.scoreboardService.getScoreboard(player)
                            .updateLine(4, GameComponents.getAlivePlayerAmountMessage(this.players.count()));
                }
        );

        this.players.remove(victim);

        if (this.players.count() == 1) {
            // Last player, we've found the winner !
            Player winner = this.players.getFirst();
            Bukkit.getOnlinePlayers().forEach(player -> player.sendMessage(GameComponents.getWinMessage(winner)));

            this.stop();

            return;
        }

        if (this.taggedPlayers.contains(victim)) {
            this.taggedPlayers.remove(victim);

            if (this.taggedPlayers.isEmpty()) {
                this.explosionRunnable.cancel();
                this.nextRound();
            }
        }
    }

    private void tagPlayer(Player taggedPlayer) {
        taggedPlayer.getInventory().setHelmet(new ItemStack(Material.TNT));
        taggedPlayer.getInventory().setItem(8,
                new ItemStackBuilder(Material.COMPASS)
                        .withName(Component.text("Tracker"))
                        .build()
        );

        AttributeInstance defenderSpeedAttribute = taggedPlayer.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED);
        defenderSpeedAttribute.setBaseValue(defenderSpeedAttribute.getBaseValue() * 1.05); // Small speed boost

        taggedPlayer.setCompassTarget(
                this.getPlayers()
                        .filter(player -> !this.isTagged(player))
                        .getClosest(taggedPlayer)
                        .getLocation()
        );

        taggedPlayer.showTitle(GameComponents.getTagMessage());
    }
}