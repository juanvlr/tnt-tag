package fr.juanvalero.tnttag.api.game;

import fr.juanvalero.tnttag.api.configuration.Configuration;
import fr.juanvalero.tnttag.api.configuration.inject.InjectConfiguration;
import fr.juanvalero.tnttag.api.game.player.PlayerCollection;
import fr.juanvalero.tnttag.api.game.player.PlayerCollectionImpl;
import fr.juanvalero.tnttag.api.game.start.AutoStartRunnableFactory;
import fr.juanvalero.tnttag.api.scoreboard.Scoreboard;
import fr.juanvalero.tnttag.api.scoreboard.ScoreboardService;
import fr.juanvalero.tnttag.api.utils.TickUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GameImpl implements Game {

    private final ScoreboardService scoreboardService;
    private final Plugin plugin;
    private final AutoStartRunnableFactory autoStartRunnableFactory;
    private final PlayerCollection players;
    private final Map<UUID, BukkitRunnable> scoreboardUpdaters;
    @InjectConfiguration
    private Configuration configuration;
    private GameState state;
    private BukkitRunnable autoStartRunnable;

    @Inject
    public GameImpl(ScoreboardService scoreboardService,
                    Plugin plugin,
                    AutoStartRunnableFactory autoStartRunnableFactory) {
        this.scoreboardService = scoreboardService;
        this.plugin = plugin;
        this.autoStartRunnableFactory = autoStartRunnableFactory;
        this.players = new PlayerCollectionImpl();
        this.state = GameState.WAITING;
        this.scoreboardUpdaters = new HashMap<>();
    }

    @Override
    public void start() {
        if (this.hasStarted()) {
            throw new IllegalStateException("The game has already started");
        }

        this.players.forEach(player -> {
            this.scoreboardService.getScoreboard(player).eraseLines(3, 4);

            player.sendMessage(Component.text("La partie commence !"));

            player.teleport(this.configuration.getStartLocation());
        });

        this.state = GameState.IN_GAME;
    }

    @Override
    public void stop() {
        if (this.state == GameState.STOPPED) {
            throw new IllegalStateException("The game is already stopped");
        }

        this.state = GameState.STOPPED;
    }

    @Override
    public boolean isClosed() {
        return this.hasStarted() || this.state == GameState.STOPPED || this.players.count() == GameConstants.MAXIMUM_PLAYER_COUNT;
    }

    @Override
    public void addPlayer(Player player) {
        if (this.isClosed()) {
            throw new IllegalStateException("The game is closed");
        }

        this.players.add(player);

        this.clean(player);

        player.teleport(this.configuration.getLobbyLocation());

        Scoreboard scoreboard = this.scoreboardService.createScoreboard(player, GameMessages.getGameTitle());

        // Scoreboard credit updater
        BukkitRunnable scoreboardUpdater = new BukkitRunnable() {
            private int ticks = 0;

            @Override
            public void run() {
                scoreboard.updateLine(2, GameMessages.getCreditMessage(this.ticks % 3));
                this.ticks++;
            }
        };

        this.scoreboardUpdaters.put(player.getUniqueId(), scoreboardUpdater);

        scoreboardUpdater.runTaskTimer(this.plugin, 0, TickUtils.TICKS_PER_SECOND);

        // Broadcast
        this.players.forEach(p -> {
                    p.sendMessage(GameMessages.getConnectionMessage(p, this.players.count()));

                    if (this.players.count() < GameConstants.MINIMUM_PLAYER_COUNT) {
                        // Update the amount of missing players
                        this.updateMissingPlayerCount(p);
                    }
                }
        );

        if (this.players.count() == GameConstants.MINIMUM_PLAYER_COUNT) {
            // We now have the needed amount of players to start the game
            this.autoStartRunnable = this.autoStartRunnableFactory.createAutoStartRunnable();
            this.autoStartRunnable.runTaskTimer(this.plugin, 0L, TickUtils.TICKS_PER_SECOND);
        }
    }

    @Override
    public void removePlayer(Player player) {
        this.players.remove(player);

        this.scoreboardService.deleteScoreboard(player);

        BukkitRunnable scoreboardUpdater = this.scoreboardUpdaters.remove(player.getUniqueId());
        scoreboardUpdater.cancel();

        if (this.state == GameState.WAITING) {
            // Broadcast
            this.players.forEach(p -> p.sendMessage(GameMessages.getDisconnectionMessage(player, this.players.count())));

            if (this.players.count() < GameConstants.MINIMUM_PLAYER_COUNT && this.autoStartRunnable != null) {
                // There are not enough players left to start the game
                this.players.forEach(p -> {
                    p.setLevel(0);
                    p.setExp(0.f);

                    // Reset the scoreboard to the waiting state
                    this.updateMissingPlayerCount(p);
                });

                this.autoStartRunnable.cancel();
                this.autoStartRunnable = null;
            }
        }
    }

    @Override
    public PlayerCollection getPlayers() {
        return this.players;
    }

    /**
     * Checks if the game has started.
     *
     * @return {@code true} if the game has started, {@code false} otherwise
     */
    private boolean hasStarted() {
        // TODO Add GameState.STOPPED too ?
        return this.state == GameState.IN_GAME;
    }

    private void clean(Player player) {
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
