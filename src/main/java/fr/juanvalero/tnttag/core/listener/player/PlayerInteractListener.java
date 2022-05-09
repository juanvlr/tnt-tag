package fr.juanvalero.tnttag.core.listener.player;

import fr.juanvalero.tnttag.api.game.item.ItemService;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import javax.inject.Inject;

public class PlayerInteractListener implements Listener {

    private final ItemService itemService;

    @Inject
    public PlayerInteractListener(ItemService itemService) {
        this.itemService = itemService;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Action action = event.getAction();

        if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
            ItemStack itemStack = event.getItem();

            if (itemStack != null) {
                this.itemService.getItem(itemStack).ifPresent(item -> {
                    Player player = event.getPlayer();
                    PlayerInventory inventory = player.getInventory();
                    int slot = inventory.getHeldItemSlot();

                    item.click(player, inventory, slot);

                    if (!item.isUsed()) {
                        event.setCancelled(true);
                    }
                });
            }
        }
    }
}
