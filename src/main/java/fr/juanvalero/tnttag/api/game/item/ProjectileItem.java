/*
 * Copyright (c) 2022 - Juan Valero
 */

package fr.juanvalero.tnttag.api.game.item;

import fr.juanvalero.tnttag.api.game.Game;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

/**
 * An {@link Item} that can be thrown by the player.
 * The action to execute when the projectile hurts the block can be configured using {@link #hurt(Block)}.
 */
public abstract class ProjectileItem extends Item {

    public ProjectileItem(Game game, Plugin plugin) {
        super(game, plugin);
    }

    /**
     * The action to execute when the projectile hurts a block.
     *
     * @param block The hurt block.
     */
    public abstract void hurt(Block block);

    @Override
    public boolean consume(Player player) {
        return !this.currentUsers.containsKey(player.getUniqueId()); // Prevents the interact event from being cancelled (only if the cooldown is finished)
    }

    @Override
    public void click(Player player, boolean isSoundEnabled) {
        super.click(player, false);
    }

    @Override
    protected void action(Player player) {
        // No action on right click by default
    }
}
