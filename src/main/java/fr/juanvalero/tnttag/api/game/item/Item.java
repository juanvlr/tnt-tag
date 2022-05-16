package fr.juanvalero.tnttag.api.game.item;

import de.tr7zw.changeme.nbtapi.NBTItem;
import fr.juanvalero.tnttag.api.utils.scheduler.TickUtils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * A special item that triggers an action when right-clicking on it.
 * The item cooldown can be specified using {@link #getCooldown()}.
 * The action to execute when clicking on the item can be configured using {@link #action(Player)}.
 * Items can be enabled or disabled using the configuration gui.
 */
public abstract class Item {

    public static final String ID_FIELD = "id";

    protected final Plugin plugin;

    protected final Map<UUID, Player> currentUsers;

    public Item(Plugin plugin) {
        this.plugin = plugin;
        this.currentUsers = new HashMap<>();
    }

    /**
     * Returns the id of the item.
     *
     * @return The item id
     */
    public abstract int getId();

    /**
     * Returns the time before which the item can be reused.
     *
     * @return The item cooldown
     */
    protected abstract int getCooldown();

    /**
     * Returns the {@link ItemStack} associated to the item.
     *
     * @return The corresponding item {@link ItemStack}
     */
    protected abstract ItemStack getItemStack();

    /**
     * The action to execute when clicking the item.
     *
     * @param player The player who clicked on the item
     */
    protected abstract void action(Player player);

    /**
     * Determines if the item should be used when clicking on it.
     * Sets it to {@code true} prevents the click event from being cancelled.
     *
     * @return {@code true} if the item should be used, {@code false} otherwise
     */
    public boolean isUsed() {
        return false;
    }

    /**
     * Turns the item into an {@link ItemStack}.
     *
     * @return The resulting {@link ItemStack}
     */
    public ItemStack asItemStack() {
        ItemStack itemStack = this.getItemStack();

        NBTItem nbtItem = new NBTItem(itemStack);
        nbtItem.setInteger(ID_FIELD, this.getId());
        nbtItem.mergeNBT(itemStack);

        return itemStack;
    }

    /**
     * Performs a click on the item.
     *
     * @param player    The player who clicked
     * @param inventory The inventory in which the item is located
     * @param slot      The inventory index in which the item is located
     */
    public void click(Player player, Inventory inventory, int slot) {
        UUID playerId = player.getUniqueId();

        if (this.currentUsers.containsKey(playerId)) {
            // The cooldown for this player is not over yet
            return;
        }

        inventory.setItem(slot, null);

        this.currentUsers.put(playerId, player);

        new BukkitRunnable() {

            @Override
            public void run() {
                Item.this.currentUsers.remove(playerId);
                inventory.setItem(slot, Item.this.asItemStack());
            }
        }.runTaskLater(this.plugin, TickUtils.getTicks(this.getCooldown()));

        this.action(player);
    }
}
