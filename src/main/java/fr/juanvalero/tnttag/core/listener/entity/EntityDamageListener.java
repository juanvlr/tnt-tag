package fr.juanvalero.tnttag.core.listener.entity;

import fr.juanvalero.tnttag.api.game.Game;
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
        if (event.getEntity() instanceof Player defender) {
            if (event.getDamager() instanceof Player damager) {
                // Keep knockback without dealing damage
                event.setDamage(0.);

                this.game.tag(damager, defender);
            }
        }
    }
}
