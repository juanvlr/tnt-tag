/*
 * Copyright (c) 2022 - Juan Valero
 */

package fr.juanvalero.tnttag.api.scoreboard;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.ScoreboardManager;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Default {@link ScoreboardService} implementation.
 */
public class ScoreboardServiceImpl implements ScoreboardService {

    private final ScoreboardManager scoreboardManager;
    private final ScoreboardFactory scoreboardFactory;
    private final Map<UUID, Scoreboard> scoreboards;

    @Inject
    public ScoreboardServiceImpl(ScoreboardManager scoreboardManager, ScoreboardFactory scoreboardFactory) {
        this.scoreboardManager = scoreboardManager;
        this.scoreboardFactory = scoreboardFactory;
        this.scoreboards = new HashMap<>();
    }

    @Override
    public void createScoreboard(Player player, Component title) {
        UUID playerId = player.getUniqueId();

        if (this.scoreboards.containsKey(playerId)) {
            throw new IllegalStateException(String.format("Player with id %s has already a scoreboard", playerId));
        }

        Scoreboard scoreboard = this.scoreboardFactory.createScoreboard(player, title);
        this.scoreboards.put(playerId, scoreboard);
    }

    @Override
    public void deleteScoreboard(Player player) {
        UUID playerId = player.getUniqueId();

        if (!this.scoreboards.containsKey(playerId)) {
            return;
        }

        this.scoreboards.remove(playerId);

        player.setScoreboard(this.scoreboardManager.getNewScoreboard());
    }

    @Override
    public Scoreboard getScoreboard(Player player) {
        UUID playerId = player.getUniqueId();

        if (!this.scoreboards.containsKey(playerId)) {
            throw new IllegalStateException(String.format("No player with id %s has a scoreboard", playerId));
        }

        return this.scoreboards.get(playerId);
    }
}
