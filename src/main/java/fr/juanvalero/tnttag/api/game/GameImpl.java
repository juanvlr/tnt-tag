/*
 * Copyright (c) 2022 - Juan Valero
 */

package fr.juanvalero.tnttag.api.game;

import fr.juanvalero.tnttag.api.configuration.Configuration;
import fr.juanvalero.tnttag.api.game.display.GameComponents;
import fr.juanvalero.tnttag.api.game.display.ScoreboardCreditUpdater;
import fr.juanvalero.tnttag.api.game.event.Event;
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
import fr.juanvalero.tnttag.api.world.WorldService;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.title.Title;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.messaging.ChannelNotRegisteredException;
import org.bukkit.scheduler.BukkitRunnable;

import javax.inject.Inject;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
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

    private final WorldService worldService;
    private final PlayerCollection alivePlayers;
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
                    EventService eventService,
                    WorldService worldService) {
        this.scoreboardService = scoreboardService;
        this.scoreboardCreditUpdater = scoreboardCreditUpdater;
        this.plugin = plugin;
        this.explosionRunnableFactory = explosionRunnableFactory;
        this.autoStartRunnableFactory = autoStartRunnableFactory;
        this.itemService = itemService;
        this.eventService = eventService;
        this.worldService = worldService;
        this.alivePlayers = new PlayerCollectionImpl();
        this.taggedPlayers = new PlayerCollectionImpl();
        this.state = GameState.WAITING;
    }

    @Override
    public void start() {
        if (this.configuration.allowItems()) {
            int itemAmount = this.configuration.getItemAmount();
            this.alivePlayers.forEach(player -> {
                List<ItemStack> items = this.itemService.getRandomItems(itemAmount);

                Inventory inventory = player.getInventory();

                for (int slot = 0; slot < itemAmount; slot++) {
                    inventory.setItem(slot, items.get(slot));
                }

                player.updateInventory();
            });
        }

        this.alivePlayers.forEach(player -> {
            Scoreboard scoreboard = this.scoreboardService.getScoreboard(player);
            scoreboard.empty();

            scoreboard.updateLine(2, GameComponents.getAlivePlayerAmountMessage(this.alivePlayers.count()));

            player.showTitle(GameComponents.getStartMessage());

            this.configuration.getStartLocation().ifPresent(player::teleport);

            player.playSound(player.getLocation(), Sound.BLOCK_PORTAL_TRAVEL, 1f, 1f);

            this.scoreboardCreditUpdater.removePlayer(player);
        });

        this.state = GameState.IN_GAME;

        this.nextRound(false);
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

        this.state = GameState.STOPPED;

        Bukkit.getOnlinePlayers().forEach(this.scoreboardService::deleteScoreboard);

        new BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.getOnlinePlayers().forEach(player -> {
                    try {
                        ByteArrayOutputStream out = new ByteArrayOutputStream();
                        DataOutputStream data = new DataOutputStream(out);

                        data.writeUTF("Connect");
                        data.writeUTF("lobby");

                        data.flush();

                        player.sendPluginMessage(GameImpl.this.plugin, "BungeeCord", out.toByteArray());
                    } catch (ChannelNotRegisteredException e) {
                        player.kick(Component.text("Partie terminée"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

                GameImpl.this.state = GameState.WAITING;
            }
        }.runTaskLater(this.plugin, TickUtils.getTicks(10));
    }

    @Override
    public boolean hasStarted() {
        return this.state == GameState.IN_GAME;
    }

    @Override
    public boolean isClosed() {
        return this.hasStarted() || this.state == GameState.STOPPED || this.alivePlayers.count() == GameConstants.MAXIMUM_PLAYER_COUNT;
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

        PlayerInventory damagerInventory = damager.getInventory();

        damagerInventory.setHelmet(null);
        damagerInventory.setItem(8, null);
        damagerInventory.setItem(7, null);

        AttributeInstance damagerSpeedAttribute = damager.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED);
        damagerSpeedAttribute.setBaseValue(damagerSpeedAttribute.getBaseValue()); // Resets his speed

        Bukkit.broadcast(Component.text(defender.getName(), NamedTextColor.DARK_GRAY)
                .append(Component.text(" est marqué(e)", NamedTextColor.GRAY)));

        this.taggedPlayers.add(defender);
        this.tagPlayer(defender);
    }

    @Override
    public void blowUp() {
        this.taggedPlayers.forEach(victim -> {
            this.kill(victim, GameComponents.getExplosionMessage(victim));
            victim.showTitle(Title.title(Component.text("DÉFAITE", NamedTextColor.RED), Component.empty()));
        });
    }

    @Override
    public void join(Player joiner) {
        this.alivePlayers.add(joiner);

        joiner.setGameMode(GameMode.ADVENTURE);
        this.clean(joiner);

        this.scoreboardService.createScoreboard(joiner, GameComponents.getGameTitle());

        this.scoreboardCreditUpdater.addPlayer(joiner);

        this.alivePlayers.forEach(player -> {
                    player.sendMessage(GameComponents.getConnectionMessage(joiner, this.alivePlayers.count()));

                    // We don't want to update the scoreboard if the game is starting
                    // Regular starting : the minimum amount of players has already been reached
                    // Fast starting : we don't need a minimum amount of players
                    if (!this.isStarting()) {
                        // Update the amount of missing players
                        this.updateMissingPlayerCount(player);
                    }
                }
        );

        if (!this.isFastStarting() && this.alivePlayers.count() == GameConstants.MINIMUM_PLAYER_COUNT) {
            // We now have the needed amount of players to start the game
            this.state = GameState.REGULAR_STARTING;

            this.autoStartRunnable = this.autoStartRunnableFactory.createAutoStartRunnable(60);
            this.autoStartRunnable.runTaskTimer(this.plugin, 0L, TickUtils.TICKS_PER_SECOND);
        }

        this.configuration.getLobbyLocation().ifPresent(joiner::teleport);
    }

    @Override
    public void leave(Player leaver) {
        if (this.state == GameState.STOPPED) {
            this.alivePlayers.remove(leaver);

            return;
        }

        if (this.hasStarted()) {
            this.kill(leaver, GameComponents.getDisconnectionDeathMessage(leaver));
        } else {
            this.alivePlayers.remove(leaver);

            this.alivePlayers.forEach(player ->
                    player.sendMessage(GameComponents.getDisconnectionMessage(leaver, this.alivePlayers.count()))
            );

            if (this.state == GameState.WAITING) {
                this.alivePlayers.forEach(this::updateMissingPlayerCount);
            } else if ((this.isFastStarting() && this.alivePlayers.count() == 1) || (this.isRegularStarting() && this.alivePlayers.count() < GameConstants.MINIMUM_PLAYER_COUNT)) {
                // There are not enough players left to start the game
                this.autoStartRunnable.cancel();
                this.alivePlayers.forEach(player -> {
                    this.updateMissingPlayerCount(player);
                    player.clearTitle();
                }); // Reset the scoreboard to the waiting state and the title if any
                this.state = GameState.WAITING;
            }

            this.scoreboardCreditUpdater.removePlayer(leaver);
        }

        this.scoreboardService.deleteScoreboard(leaver);
    }

    @Override
    public PlayerCollection getAlivePlayers() {
        return this.alivePlayers;
    }

    @Override
    public PlayerCollection getTaggedPlayers() {
        return this.taggedPlayers;
    }

    @Override
    public boolean isTagged(Player player) {
        return this.taggedPlayers.contains(player);
    }

    @Override
    public boolean isSpectator(Player player) {
        return !this.alivePlayers.contains(player);
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
                .updateLine(4, GameComponents.getMissingPlayerCountMessage(this.alivePlayers.count()));
    }

    /**
     * Jumps to the next round by choosing tagged players and starting the explosion timer.
     *
     * @param shouldTriggerEvent Determines if an event should be triggered
     */
    private void nextRound(boolean shouldTriggerEvent) {
        int taggedPlayersAmount = this.alivePlayers.count() / 2;
        this.taggedPlayers = this.alivePlayers.getRandoms(taggedPlayersAmount);

        this.taggedPlayers.forEach(this::tagPlayer);

        this.explosionRunnable = this.explosionRunnableFactory.createExplosionRunnable();
        this.explosionRunnable.runTaskTimer(this.plugin, 0L, TickUtils.TICKS_PER_SECOND);

        if (shouldTriggerEvent && this.configuration.isEventEnabled()) {
            if (new Random().nextInt(2) == 0) {
                Event event = this.eventService.getRandomEvent();
                event.run();

                this.alivePlayers.forEach(player -> player.showTitle(
                                Title.title(
                                        Component.text(event.getName(), NamedTextColor.BLUE),
                                        event.getDuration() > 0
                                                ? (Component.text(event.getDuration()).append(Component.text(" secondes")))
                                                : (Component.text("Instantanée")
                                        )
                                )
                        )
                );
                Bukkit.broadcastMessage("§9=================================\n" + "§e["+ event.getName() + "]§9\n\n" + event.getdescription() + "\n=================================");
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

        this.alivePlayers.remove(victim);

        if (this.taggedPlayers.contains(victim)) {
            this.taggedPlayers.remove(victim);

            Location location = victim.getLocation();
            TNTPrimed tnt = (TNTPrimed) location.getWorld().spawnEntity(location, EntityType.PRIMED_TNT);
            tnt.setFuseTicks(0);

            location.getWorld().spawnParticle(Particle.EXPLOSION_LARGE, location, 10);

            Bukkit.getOnlinePlayers().forEach(player -> player.playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1f, 1f));
        }

        Bukkit.getOnlinePlayers().forEach(player -> {
                    player.sendMessage(reason);
                    this.scoreboardService.getScoreboard(player)
                            .updateLine(2, GameComponents.getAlivePlayerAmountMessage(this.alivePlayers.count()));
                }
        );

        if (this.alivePlayers.count() == 1) {
            // Last player, we've found the winner !
            Player winner = this.alivePlayers.getFirst();
            Bukkit.broadcast(GameComponents.getWinMessage(winner));

            winner.showTitle(Title.title(Component.text("VICTOIRE", NamedTextColor.GREEN), Component.empty()));

            this.stop();

            return;
        }

        if (this.taggedPlayers.isEmpty()) {
            this.explosionRunnable.cancel();
            this.nextRound(true);
        }
    }

    private void tagPlayer(Player player) {
        PlayerInventory inventory = player.getInventory();

        inventory.setHelmet(new ItemStack(Material.TNT));
        inventory.setItem(8,
                new ItemStackBuilder(Material.COMPASS)
                        .withName(Component.text("Tracker"))
                        .build()
        );
        inventory.setItem(7,
                new ItemStackBuilder(Material.TNT)
                        .build()
        );

        AttributeInstance defenderSpeedAttribute = player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED);
        defenderSpeedAttribute.setBaseValue(defenderSpeedAttribute.getBaseValue() * 1.05); // Small speed boost

        player.setCompassTarget(
                this.getAlivePlayers()
                        .filter(p -> !this.isTagged(p))
                        .getClosestLocation(player)
        );

        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 1f, 1f);
        player.showTitle(GameComponents.getTagMessage());
        this.worldService.spawnFirework(player.getLocation());
    }
}
