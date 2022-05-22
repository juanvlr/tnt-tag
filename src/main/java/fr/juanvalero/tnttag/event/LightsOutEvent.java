/*
 * Copyright (c) 2022 - Juan Valero
 */

package fr.juanvalero.tnttag.event;

import fr.juanvalero.tnttag.api.game.Game;
import fr.juanvalero.tnttag.api.game.event.Event;
import fr.juanvalero.tnttag.api.utils.scheduler.TickUtils;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import javax.inject.Inject;

public class LightsOutEvent extends Event {

    private final Game game;

    @Inject
    public LightsOutEvent(Game game) {
        this.game = game;
    }

    @Override
    public void run() {
        this.game.getAlivePlayers().forEach(player -> player.addPotionEffect(
                        new PotionEffect(PotionEffectType.BLINDNESS, TickUtils.getTicks(30), 1)
                )
        );
    }

    @Override
    public String getName() {
        return "Extinction";
    }

    @Override
    public int getDuration() {
        return 30;
    }
}
