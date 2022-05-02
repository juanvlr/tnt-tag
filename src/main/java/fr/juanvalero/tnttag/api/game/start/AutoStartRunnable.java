package fr.juanvalero.tnttag.api.game.start;

import fr.juanvalero.tnttag.api.game.Game;
import fr.juanvalero.tnttag.api.game.GameMessages;
import fr.juanvalero.tnttag.api.scoreboard.ScoreboardService;
import org.bukkit.scheduler.BukkitRunnable;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;

public class AutoStartRunnable extends BukkitRunnable {

    private static final int WAITING_TIME = 60;
    private static final List<Integer> DISPLAY_AT = Arrays.asList(60, 30, 15, 10, 5, 4, 3, 2, 1);

    private final ScoreboardService scoreboardService;
    private final Game game;
    private int timer = WAITING_TIME;

    @Inject
    public AutoStartRunnable(ScoreboardService scoreboardService, Game game) {
        this.scoreboardService = scoreboardService;
        this.game = game;
    }

    @Override
    public void run() {
        this.game.getPlayers().forEach(player -> {
            player.setLevel(this.timer);
            player.setExp((float) this.timer / (float) WAITING_TIME);
        });

        if (this.timer == 0) {
            // We reached the timer, cancel the task and start the game
            this.cancel();

            this.game.start();

            return;
        }

        this.game.getPlayers().forEach(player -> {
            // Update the remaining time on the scoreboard
            this.scoreboardService.getScoreboard(player)
                    .updateLine(4, GameMessages.getRemainingTimeMessage(this.timer));
        });

        if (DISPLAY_AT.contains(this.timer)) {
            this.game.getPlayers().forEach(player -> player.showTitle(GameMessages.getRemainingTimeTitle(this.timer)));
        }

        this.timer--;
    }
}
