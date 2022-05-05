package fr.juanvalero.tnttag.api.game;

import fr.juanvalero.tnttag.api.configuration.Configuration;
import fr.juanvalero.tnttag.api.configuration.inject.InjectConfiguration;
import fr.juanvalero.tnttag.api.game.display.GameMessages;
import fr.juanvalero.tnttag.api.game.display.ScoreboardCreditUpdater;
import fr.juanvalero.tnttag.api.game.player.PlayerCollection;
import fr.juanvalero.tnttag.api.game.player.PlayerCollectionImpl;
import fr.juanvalero.tnttag.api.game.start.AutoStartRunnableFactory;
import fr.juanvalero.tnttag.api.scoreboard.ScoreboardService;
import fr.juanvalero.tnttag.api.utils.scheduler.TickUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.GameMode;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import javax.inject.Inject;

public class GameImpl implements Game {

    private final ScoreboardCreditUpdater scoreboardCreditUpdater;

    private final ScoreboardService scoreboardService;
    @InjectConfiguration
    private Configuration configuration;
    private final Plugin plugin;
    private final AutoStartRunnableFactory autoStartRunnableFactory;
    private final PlayerCollection players;
    private GameState state;
    private BukkitRunnable autoStartRunnable;

    @Inject
    public GameImpl(ScoreboardService scoreboardService,
                    ScoreboardCreditUpdater scoreboardCreditUpdater,
                    Plugin plugin,
                    AutoStartRunnableFactory autoStartRunnableFactory) {
        this.scoreboardService = scoreboardService;
        this.scoreboardCreditUpdater = scoreboardCreditUpdater;
        this.plugin = plugin;
        this.autoStartRunnableFactory = autoStartRunnableFactory;
        this.players = new PlayerCollectionImpl();
        this.state = GameState.WAITING;
    }

    @Override
    public void start() {
        if (this.hasStarted()) {
            throw new IllegalStateException("The game has already started");
        }

        this.cancelAutoStart();

        this.players.forEach(player -> {
            this.scoreboardService.getScoreboard(player).eraseLines(3, 4);

            player.sendMessage(Component.text("La partie commence !"));

            player.teleport(this.configuration.getStartLocation());
        });

        this.state = GameState.IN_GAME;
    }

    @Override
    public void startDelayed() {
        if (this.hasStarted()) {
            throw new IllegalStateException("The game has already start");
        }

        if (this.isFastStarting()) {
            throw new IllegalStateException("The game is already fast starting");
        }

        if (this.state == GameState.REGULAR_STARTING) {
            // The game was already starting, it means that someone speeds up the time
            // We cancel the runnable before running the new one
            this.cancelAutoStart();
        }

        this.autoStartRunnable = this.autoStartRunnableFactory.createAutoStartRunnable(10);
        this.autoStartRunnable.runTaskTimer(this.plugin, 0L, TickUtils.TICKS_PER_SECOND);

        this.state = GameState.FAST_STARTING;
    }

    @Override
    public void stop() {
        if (this.state == GameState.STOPPING) {
            throw new IllegalStateException("The game is already stopped");
        }

        this.state = GameState.STOPPING;
    }

    @Override
    public boolean isFastStarting() {
        return this.state == GameState.FAST_STARTING;
    }

    @Override
    public boolean isClosed() {
        return this.hasStarted() || this.state == GameState.STOPPING || this.players.count() == GameConstants.MAXIMUM_PLAYER_COUNT;
    }

    @Override
    public void addPlayer(Player player) {
        if (this.isClosed()) {
            throw new IllegalStateException(String.format("Can't add %s to the game since it already started", player.getName()));
        }

        this.players.add(player);

        this.clean(player);

        this.scoreboardService.createScoreboard(player, GameMessages.getGameTitle());

        this.scoreboardCreditUpdater.addPlayer(player);

        // Broadcast
        this.players.forEach(p -> {
                    p.sendMessage(GameMessages.getConnectionMessage(p, this.players.count()));

                    // We don't want to update the scoreboard if the game is starting
                    // Regular starting : the minimum amount of players has already been reached
                    // Fast starting : we don't need a minimum amount of players
                    if (!this.isStarting() && this.players.count() < GameConstants.MINIMUM_PLAYER_COUNT) {
                        // Update the amount of missing players
                        this.updateMissingPlayerCount(p);
                    }
                }
        );

        if (!this.isFastStarting() && this.players.count() == GameConstants.MINIMUM_PLAYER_COUNT) {
            // We now have the needed amount of players to start the game
            this.state = GameState.REGULAR_STARTING;

            this.autoStartRunnable = this.autoStartRunnableFactory.createAutoStartRunnable(60);
            this.autoStartRunnable.runTaskTimer(this.plugin, 0L, TickUtils.TICKS_PER_SECOND);
        }

        player.teleport(this.configuration.getLobbyLocation());
    }

    @Override
    public void removePlayer(Player player) {
        this.players.remove(player);

        this.scoreboardService.deleteScoreboard(player);

        this.scoreboardCreditUpdater.removePlayer(player);

        if (this.state == GameState.WAITING || this.isStarting()) {
            // Broadcast
            this.players.forEach(p -> p.sendMessage(GameMessages.getDisconnectionMessage(player, this.players.count())));
        }

        if (this.isFastStarting() && this.players.isEmpty()) {
            // Cancel the fast starting as there is no player
            this.cancelAutoStart();

            this.state = GameState.WAITING;
        } else if (this.state == GameState.REGULAR_STARTING && this.players.count() < GameConstants.MINIMUM_PLAYER_COUNT) {
            // There are not enough players left to start the game
            this.players.forEach(this::updateMissingPlayerCount); // Reset the scoreboard to the waiting state

            this.cancelAutoStart();

            this.state = GameState.WAITING;
        }
    }

    private void cancelAutoStart() {
        this.autoStartRunnable.cancel();
        this.autoStartRunnable = null;
    }

    @Override
    public PlayerCollection getPlayers() {
        return this.players;
    }

    /**
     * Checks if the game is starting.
     *
     * @return {@code true} if the game is starting, {@code false} otherwise.
     */
    private boolean isStarting() {
        return this.state == GameState.REGULAR_STARTING || this.isFastStarting();
    }

    /**
     * Checks if the game has started.
     *
     * @return {@code true} if the game has started, {@code false} otherwise
     */
    private boolean hasStarted() {
        return this.state == GameState.IN_GAME;
    }

    private void clean(Player player) {
        player.setGameMode(GameMode.ADVENTURE);

        player.getInventory().clear();

        player.setLevel(0);
        player.setExp(0.f);

        //noinspection ConstantConditions
        player.setHealth(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getDefaultValue());
        player.setFoodLevel(20);
    }

    private void updateMissingPlayerCount(Player p) {
        this.scoreboardService.getScoreboard(p)
                .updateLine(4, GameMessages.getMissingPlayerCountMessage(this.players.count()));
    }
}
