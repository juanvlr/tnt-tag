/*
 * Copyright (c) 2022 - Juan Valero
 */

package fr.juanvalero.tnttag.listener.projectile;

import fr.juanvalero.tnttag.api.game.item.ItemService;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

import javax.inject.Inject;

public class ProjectileHitListener implements Listener {

    private final ItemService itemService;

    @Inject
    public ProjectileHitListener(ItemService itemService) {
        this.itemService = itemService;
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        Projectile projectile = event.getEntity();

        if (projectile.getShooter() instanceof Player) {
            Block block = event.getHitBlock();

            if (block != null) {
                this.itemService.getProjectile(projectile).ifPresent(projectileItem -> projectileItem.hurt(block));
            }
        }
    }
}
