/*
 * Copyright (c) 2022 - Juan Valero
 */

package fr.juanvalero.tnttag.api.game.explosion;

import fr.juanvalero.tnttag.api.game.Game;
import fr.juanvalero.tnttag.api.scoreboard.ScoreboardService;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.scheduler.BukkitRunnable;

import javax.inject.Inject;

/**
 * Task after which tnts explode.
 */
public class ExplosionRunnable extends BukkitRunnable {

    private static final int EXPLOSION_TIME = 60;

    private final Game game;
    private final ScoreboardService scoreboardService;
    private int time;

    @Inject
    public ExplosionRunnable(Game game, ScoreboardService scoreboardService) {
        this.game = game;
        this.scoreboardService = scoreboardService;
        this.time = EXPLOSION_TIME;
    }

    @Override
    public void run() {
        if (this.time == 0) {
            this.cancel();
            this.game.blowUp();

            return;
        }

        this.game.getPlayers().forEach(player -> {
            this.scoreboardService.getScoreboard(player).updateLine(4,
                    Component
                            .text("Explosion dans ")
                            .append(Component.text(this.time).color(NamedTextColor.GREEN))
                            .append(Component.text(" seconde(s)"))
            );

                    player.setLevel(this.time);
                    player.setExp((float) this.time / (float) EXPLOSION_TIME);
                }
        );

        this.time--;
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
