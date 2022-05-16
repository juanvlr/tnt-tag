package fr.juanvalero.tnttag.core.event;

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
        this.game.getPlayers().forEach(player -> {
            player.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, TickUtils.getTicks(20), 1));
            this.worldService.spawnFirework(player.getLocation());
        });
    }
}
