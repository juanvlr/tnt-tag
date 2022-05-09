package fr.juanvalero.tnttag.core.listener.projectile;

import com.destroystokyo.paper.event.player.PlayerLaunchProjectileEvent;
import fr.juanvalero.tnttag.api.game.item.ItemService;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import javax.inject.Inject;

public class PlayerLaunchProjectileListener implements Listener {

    private final ItemService itemService;

    @Inject
    public PlayerLaunchProjectileListener(ItemService itemService) {
        this.itemService = itemService;
    }

    @EventHandler
    public void onPlayerLaunchProjectile(PlayerLaunchProjectileEvent event) {
        this.itemService.launchProjectile(event.getItemStack(), event.getProjectile());
    }
}
