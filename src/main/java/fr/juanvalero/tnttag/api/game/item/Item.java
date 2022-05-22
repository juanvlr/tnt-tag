/*
 * Copyright (c) 2022 - Juan Valero
 */

package fr.juanvalero.tnttag.api.game.item;

import de.tr7zw.changeme.nbtapi.NBTItem;
import fr.juanvalero.tnttag.api.game.Game;
import fr.juanvalero.tnttag.api.utils.item.ItemStackBuilder;
import fr.juanvalero.tnttag.api.utils.scheduler.TickUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
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

    protected final Game game;
    private final Plugin plugin;

    protected final Map<UUID, Integer> currentUsers;

    public Item(Game game, Plugin plugin) {
        this.game = game;
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
     * Returns the material of the item.
     *
     * @return The item material
     */
    protected abstract Material getMaterial();

    /**
     * Returns the name of the item.
     *
     * @return The item name
     */
    protected abstract String getName();

    /**
     * Returns the lore of the item.
     *
     * @return The item lore
     */
    protected abstract Component[] getLore();

    /**
     * Returns the time before which the item can be reused.
     *
     * @return The item cooldown
     */
    protected abstract int getCooldown();

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
     * @param player The player who clicked
     * @return {@code true} if the item should be used, {@code false} otherwise
     */
    public boolean consume(Player player) {
        return false;
    }

    /**
     * Turns the item into an {@link ItemStack}.
     *
     * @return The resulting {@link ItemStack}
     */
    public ItemStack asItemStack() {
        ItemStack itemStack = new ItemStackBuilder(this.getMaterial())
                .withName(
                        Component
                                .text("[", NamedTextColor.WHITE)
                                .append(Component.text(this.getName(), NamedTextColor.RED))
                                .append(Component.text("]", NamedTextColor.WHITE))
                )
                .withLore(this.getLore())
                .build();

        NBTItem nbtItem = new NBTItem(itemStack);
        nbtItem.setInteger(ID_FIELD, this.getId());
        nbtItem.mergeNBT(itemStack);

        return itemStack;
    }

    /**
     * Performs a click on the item.
     *
     * @param player         The player who clicked
     * @param isSoundEnabled Determines if a sound is played
     */
    public void click(Player player, boolean isSoundEnabled) {
        UUID playerId = player.getUniqueId();

        if (this.currentUsers.containsKey(playerId)) {
            // The cooldown for this player is not over yet
            player.sendMessage(
                    Component.text("Vous récupérez votre ", NamedTextColor.RED)
                            .append(Component.text(this.getName()))
                            .append(Component.text(" dans "))
                            .append(Component.text(this.currentUsers.get(playerId)))
                            .append(Component.text(" secondes"))
            );

            return;
        }

        if (isSoundEnabled) {
            player.playSound(player.getLocation(), Sound.ITEM_ARMOR_EQUIP_DIAMOND, 1f, 1f);
        }

        this.currentUsers.put(playerId, this.getCooldown());

        new BukkitRunnable() {

            private int timer = Item.this.getCooldown();

            @Override
            public void run() {
                if (this.timer == 0) {
                    Item.this.currentUsers.remove(playerId);
                    this.cancel();

                    if (Item.this.game.hasStarted() && !Item.this.game.isSpectator(player)) {
                        player.sendMessage(
                                Component.text("Votre item ", NamedTextColor.GREEN)
                                        .append(Component.text(Item.this.getName()))
                                        .append(Component.text(" est de nouveau disponible"))
                        );

                        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f);
                    }

                    return;
                }

                this.timer--;
                Item.this.currentUsers.put(playerId, this.timer);
            }
        }.runTaskTimer(this.plugin, 0, TickUtils.TICKS_PER_SECOND);

        this.action(player);
    }
}
