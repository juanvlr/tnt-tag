/*
 * Copyright (c) 2022 - Juan Valero
 */

package fr.juanvalero.tnttag.listener.entity;

import fr.juanvalero.tnttag.api.game.Game;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import javax.inject.Inject;

public class EntityDamageListener implements Listener {

    private final Game game;

    @Inject
    public EntityDamageListener(Game game) {
        this.game = game;
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            if (event.getCause() != EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        Entity defender = event.getEntity();
        if (defender instanceof Player) {
            Entity damager = event.getDamager();

            if (damager instanceof Player) {
                // Keep knockback without dealing damage
                event.setDamage(0.);

                this.game.tag((Player) damager, (Player) defender);
            }
        }
    }
}
