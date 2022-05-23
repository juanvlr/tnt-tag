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
        this.game.getAlivePlayers().forEach(player -> {
            player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, TickUtils.getTicks(30), 3));
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, TickUtils.getTicks(30), 2));
        });
    }

    @Override
    public String getName() {
        return "Gravité Lunaire";
    }

    @Override
    public String getdescription() {
        return "§9Un p'tit voyage dans l'espace ?" + "\n\n" +  "§9Cet évènement donne l'effet §e[Jump Boost] §9ainsi que l'effet §e[Slow Fall] §9à tous les joueurs de la partie pendant 30 secondes.";
    }

    @Override
    public int getDuration() {
        return 30;
    }
}
