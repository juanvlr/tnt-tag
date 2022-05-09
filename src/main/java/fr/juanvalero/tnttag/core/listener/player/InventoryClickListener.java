package fr.juanvalero.tnttag.core.listener.player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getClickedInventory() == event.getWhoClicked().getInventory()) {
            event.setCancelled(true);
        }
    }
}
