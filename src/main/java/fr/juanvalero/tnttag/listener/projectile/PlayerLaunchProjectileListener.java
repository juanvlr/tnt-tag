/*
 * Copyright (c) 2022 - Juan Valero
 */

package fr.juanvalero.tnttag.listener.projectile;

import com.destroystokyo.paper.event.player.PlayerLaunchProjectileEvent;
import fr.juanvalero.tnttag.api.game.Game;
import fr.juanvalero.tnttag.api.game.item.ItemService;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import javax.inject.Inject;

public class PlayerLaunchProjectileListener implements Listener {

    private final Game game;
    private final ItemService itemService;

    @Inject
    public PlayerLaunchProjectileListener(Game game, ItemService itemService) {
        this.game = game;
        this.itemService = itemService;
    }

    @EventHandler
    public void onPlayerLaunchProjectile(PlayerLaunchProjectileEvent event) {
        if (!this.game.hasStarted()) {
            event.setCancelled(true);

            return;
        }

        this.itemService.launchProjectile(event.getItemStack(), event.getProjectile());
    }
}
