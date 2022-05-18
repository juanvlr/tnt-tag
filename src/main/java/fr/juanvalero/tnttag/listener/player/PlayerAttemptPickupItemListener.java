/*
 * Copyright (c) 2022 - Juan Valero
 */

package fr.juanvalero.tnttag.listener.player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAttemptPickupItemEvent;

public class PlayerAttemptPickupItemListener implements Listener {

    @EventHandler
    public void onPlayerAttemptPickupItem(PlayerAttemptPickupItemEvent event) {
        event.setCancelled(true);
    }
}
