/*
 * Copyright (c) 2022 - Juan Valero
 */

package fr.juanvalero.tnttag.api.game.item;

import fr.juanvalero.tnttag.api.utils.scheduler.TickUtils;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

/**
 * An {@link Item} that can be thrown by the player.
 * The action to execute when the projectile hurts the block can be configured using {@link #hurt(Block)}.
 */
public abstract class ProjectileItem extends Item {

    public ProjectileItem(Plugin plugin) {
        super(plugin);
    }

    /**
     * The action to execute when the projectile hurts a block.
     *
     * @param block The hurt block.
     */
    public abstract void hurt(Block block);

    @Override
    public boolean isUsed() {
        return true; // Prevents the interact event from being cancelled
    }

    @Override
    public void click(Player player, Inventory inventory, int slot) {
        UUID playerId = player.getUniqueId();

        if (this.currentUsers.containsKey(playerId)) {
            // The cooldown for this player is not over yet
            return;
        }

        new BukkitRunnable() {

            @Override
            public void run() {
                ProjectileItem.super.currentUsers.remove(playerId);
                inventory.setItem(slot, ProjectileItem.super.asItemStack());
            }
        }.runTaskLater(this.plugin, TickUtils.getTicks(this.getCooldown()));

        this.action(player);
    }

    @Override
    protected void action(Player player) {
        // No action on right click by default
    }
}
