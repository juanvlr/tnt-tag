/*
 * Copyright (c) 2022 - Juan Valero
 */

package fr.juanvalero.tnttag.listener.entity;

import fr.juanvalero.tnttag.api.configuration.Configuration;
import fr.juanvalero.tnttag.api.game.Game;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

import javax.inject.Inject;

public class EntityExplodeListener implements Listener {

    @Inject
    private Configuration configuration;

    private final Game game;

    @Inject
    public EntityExplodeListener(Game game) {
        this.game = game;
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        if (event.getEntityType() == EntityType.PRIMED_TNT) {
            if (!this.configuration.isTntDestructive()) {
                event.setCancelled(true);
            }

            this.game.getPlayers().forEach(player -> player.playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1f, 1f));
        }
    }
}
