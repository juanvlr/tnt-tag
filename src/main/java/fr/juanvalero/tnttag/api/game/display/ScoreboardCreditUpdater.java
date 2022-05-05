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

    public void addPlayer(Player player) {
        Scoreboard scoreboard = this.scoreboardService.getScoreboard(player);

        BukkitRunnable scoreboardUpdater = new BukkitRunnable() {
            private int ticks = 0;

            @Override
            public void run() {
                scoreboard.updateLine(2, GameMessages.getCreditMessage(this.ticks % 3));
                this.ticks++;
            }
        };

        scoreboardUpdater.runTaskTimer(this.plugin, 0, TickUtils.TICKS_PER_SECOND);

        this.scoreboards.put(player.getUniqueId(), scoreboardUpdater);
    }

    public void removePlayer(Player player) {
        BukkitRunnable scoreboardUpdater = this.scoreboards.remove(player.getUniqueId());
        scoreboardUpdater.cancel();
    }
}
