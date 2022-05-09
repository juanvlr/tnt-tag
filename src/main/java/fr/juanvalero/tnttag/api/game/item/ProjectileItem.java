package fr.juanvalero.tnttag.api.game.item;

import fr.juanvalero.tnttag.api.utils.scheduler.TickUtils;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public abstract class ProjectileItem extends Item {

    public ProjectileItem(Plugin plugin) {
        super(plugin);
    }

    public abstract void hurt(Player player, Block block);

    @Override
    public boolean isUsed() {
        return true;
    }

    @Override
    public void click(Player player, Inventory inventory, int slot) {
        if (!this.isUsable) {
            return;
        }

        this.isUsable = false;

        new BukkitRunnable() {

            @Override
            public void run() {
                ProjectileItem.super.isUsable = true;
                inventory.setItem(slot, ProjectileItem.super.asItemStack());
            }
        }.runTaskLater(this.plugin, TickUtils.getTicks(this.getCooldown()));

        this.action(player);
    }

    @Override
    protected void action(Player player) {

    }
}
