package fr.juanvalero.tnttag.api.game.item;

import de.tr7zw.changeme.nbtapi.NBTItem;
import fr.juanvalero.tnttag.api.utils.scheduler.TickUtils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public abstract class Item {

    protected final Plugin plugin;
    protected boolean isUsable;

    public Item(Plugin plugin) {
        this.plugin = plugin;
        this.isUsable = true;
    }

    public abstract int getId();

    protected abstract int getCooldown();

    protected abstract ItemStack getItemStack();

    protected abstract void action(Player player);

    public boolean isUsed() {
        return false;
    }

    public ItemStack asItemStack() {
        ItemStack itemStack = this.getItemStack();

        NBTItem nbtItem = new NBTItem(itemStack);
        nbtItem.setInteger("id", this.getId());
        nbtItem.mergeNBT(itemStack);

        return itemStack;
    }

    public void click(Player player, Inventory inventory, int slot) {
        if (!this.isUsable) {
            return;
        }

        inventory.setItem(slot, null);

        this.isUsable = false;

        new BukkitRunnable() {

            @Override
            public void run() {
                Item.this.isUsable = true;
                inventory.setItem(slot, Item.this.asItemStack());
            }
        }.runTaskLater(this.plugin, TickUtils.getTicks(this.getCooldown()));

        this.action(player);
    }
}
