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

public class DrunkennessEvent extends Event {

    private final Game game;

    @Inject
    public DrunkennessEvent(Game game) {
        this.game = game;
    }

    @Override
    public void run() {
        this.game.getAlivePlayers().forEach(player -> {
            player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, TickUtils.getTicks(20), 1));
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, TickUtils.getTicks(20), 2));
        });
    }

    @Override
    public String getName() {
        return "Ivresse";
    }

    @Override
    public String getdescription() {
        return "§9L'abus d'alcool est dangereux pour la santé." + "\n\n" + "§9Cet évènement donne l'effet §e[Nausée] §9ainsi que l'effet §e[Speed] §9à tous les joueurs de la partie pendant 20 secondes.";
    }

    @Override
    public int getDuration() {
        return 20;
    }
}
