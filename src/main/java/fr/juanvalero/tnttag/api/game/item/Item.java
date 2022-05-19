/*
 * Copyright (c) 2022 - Juan Valero
 */

package fr.juanvalero.tnttag.api.game.item;

import de.tr7zw.changeme.nbtapi.NBTItem;
import fr.juanvalero.tnttag.api.utils.scheduler.TickUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.Sound;
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

    protected final Map<UUID, Integer> currentUsers;

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
            player.sendMessage(
                    Component.text("Utilisable dans ")
                            .append(Component.text(this.currentUsers.get(playerId)))
                            .append(Component.text(" secondes"))
            );

            return;
        }

        player.playSound(player.getLocation(), Sound.ITEM_ARMOR_EQUIP_DIAMOND, 1f, 1f);

        this.currentUsers.put(playerId, this.getCooldown());

        new BukkitRunnable() {

            private int timer = Item.this.getCooldown();

            @Override
            public void run() {
                if (this.timer == 0) {
                    Item.this.currentUsers.remove(playerId);
                    this.cancel();

                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f);

                    return;
                }

                this.timer--;
                Item.this.currentUsers.put(playerId, this.timer);
            }
        }.runTaskTimer(this.plugin, 0, TickUtils.TICKS_PER_SECOND);

        this.action(player);
    }
}
