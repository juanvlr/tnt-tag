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

public class MoonEvent extends Event {

    private final Game game;

    @Inject
    public MoonEvent(Game game) {
        this.game = game;
    }

    @Override
    public void run() {
        this.game.getPlayers().forEach(player -> {
            player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, TickUtils.getTicks(30), 3));
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, TickUtils.getTicks(30), 2));
        });
    }
}
