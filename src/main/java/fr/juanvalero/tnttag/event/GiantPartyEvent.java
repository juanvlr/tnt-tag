/*
 * Copyright (c) 2022 - Juan Valero
 */

package fr.juanvalero.tnttag.event;

import fr.juanvalero.tnttag.api.game.Game;
import fr.juanvalero.tnttag.api.game.event.Event;
import fr.juanvalero.tnttag.api.utils.scheduler.TickUtils;
import fr.juanvalero.tnttag.api.world.WorldService;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import javax.inject.Inject;

public class GiantPartyEvent extends Event {

    private final Game game;
    private final WorldService worldService;

    @Inject
    public GiantPartyEvent(Game game, WorldService worldService) {
        this.game = game;
        this.worldService = worldService;
    }

    @Override
    public void run() {
        this.game.getAlivePlayers().forEach(player -> {
            player.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, TickUtils.getTicks(20), 1));
            this.worldService.spawnFirework(player.getLocation());
        });
    }

    @Override
    public String getName() {
        return "Fête Géante";
    }

    @Override
    public String getdescription() {
        return "§9Un p'tit bout de gâteau ?" + "\n\n" + "Cet évènement donne l'effet §e[Glowing] §9et des feux d'artifices apparaissent sur tous les joueurs de la partie pendant 20 secondes.";
    }

    @Override
    public int getDuration() {
        return 20;
    }
}
