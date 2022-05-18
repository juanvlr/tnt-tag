/*
 * Copyright (c) 2022 - Juan Valero
 */

package fr.juanvalero.tnttag.api.game.start;

import com.google.inject.assistedinject.Assisted;
import fr.juanvalero.tnttag.api.game.Game;
import fr.juanvalero.tnttag.api.game.display.GameComponents;
import fr.juanvalero.tnttag.api.scoreboard.ScoreboardService;
import org.bukkit.scheduler.BukkitRunnable;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;

/**
 * Task after which the game will start.
 */
public class AutoStartRunnable extends BukkitRunnable {

    private static final List<Integer> DISPLAY_AT = Arrays.asList(60, 30, 15, 10, 5, 4, 3, 2, 1);

    private final ScoreboardService scoreboardService;
    private final Game game;
    private final int startingTime;
    private int remainingTime;

    @Inject
    public AutoStartRunnable(ScoreboardService scoreboardService, Game game, @Assisted int time) {
        this.scoreboardService = scoreboardService;
        this.game = game;
        this.startingTime = time;
        this.remainingTime = time;
    }

    @Override
    public void run() {
        if (this.remainingTime == 0) {
            // We reached the timer, start the game
            this.cancel();
            this.game.start();

            return;
        }

        this.game.getPlayers().forEach(player -> {
            // Update the remaining time in the level bar and on the scoreboard
            player.setLevel(this.remainingTime);
            player.setExp((float) this.remainingTime / (float) this.startingTime);

            this.scoreboardService.getScoreboard(player)
                    .updateLine(4, GameComponents.getRemainingTimeMessage(this.remainingTime));
        });

        if (DISPLAY_AT.contains(this.remainingTime)) {
            this.game.getPlayers().forEach(player -> player.showTitle(GameComponents.getRemainingTimeTitle(this.remainingTime)));
        }

        this.remainingTime--;
    }

    @Override
    public synchronized void cancel() throws IllegalStateException {
        super.cancel();

        this.game.getPlayers().forEach(player -> {
            player.setLevel(0);
            player.setExp(0.f);
        });
    }
}
