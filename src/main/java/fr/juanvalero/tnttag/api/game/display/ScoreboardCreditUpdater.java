/*
 * Copyright (c) 2022 - Juan Valero
 */

package fr.juanvalero.tnttag.api.game.display;

import fr.juanvalero.tnttag.api.scoreboard.Scoreboard;
import fr.juanvalero.tnttag.api.scoreboard.ScoreboardService;
import fr.juanvalero.tnttag.api.utils.scheduler.TickUtils;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Auto scoreboard credit updater.
 * When a player is joining or leaving the game, {@link #addPlayer(Player)} and {@link #removePlayer(Player)} methods
 * must be called in order for him to receive credits.
 */
public class ScoreboardCreditUpdater {

    private final Plugin plugin;
    private final ScoreboardService scoreboardService;
    private final Map<UUID, BukkitRunnable> scoreboards;

    @Inject
    public ScoreboardCreditUpdater(Plugin plugin, ScoreboardService scoreboardService) {
        this.plugin = plugin;
        this.scoreboardService = scoreboardService;
        this.scoreboards = new HashMap<>();
    }

    /**
     * Adds a player to the scoreboard.
     *
     * @param player The player to add
     */
    public void addPlayer(Player player) {
        Scoreboard scoreboard = this.scoreboardService.getScoreboard(player);

        BukkitRunnable scoreboardUpdater = new BukkitRunnable() {
            private int ticks = 0;

            @Override
            public void run() {
                scoreboard.updateLine(2, GameComponents.getCreditMessage(this.ticks % 3));
                this.ticks++;
            }
        };

        scoreboardUpdater.runTaskTimer(this.plugin, 0, TickUtils.getTicks(3));

        this.scoreboards.put(player.getUniqueId(), scoreboardUpdater);
    }

    /**
     * Removes a player from the scoreboard.
     *
     * @param player The player to remove
     */
    public void removePlayer(Player player) {
        BukkitRunnable scoreboardUpdater = this.scoreboards.remove(player.getUniqueId());
        scoreboardUpdater.cancel();
    }
}
