package fr.juanvalero.tnttag.core.listener.player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAttemptPickupItemEvent;

public class PlayerAttemptPickupItemListener implements Listener {

    @EventHandler
    public void onPlayerAttemptPickupItem(PlayerAttemptPickupItemEvent event) {
        event.setCancelled(true);
    }
}
