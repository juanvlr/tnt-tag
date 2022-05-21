/*
 * Copyright (c) 2022 - Juan Valero
 */

package fr.juanvalero.tnttag.item;

import fr.juanvalero.tnttag.api.game.Game;
import fr.juanvalero.tnttag.api.game.item.Item;
import fr.juanvalero.tnttag.api.utils.scheduler.TickUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import javax.inject.Inject;

public class FlyItem extends Item {

    private final Plugin plugin;

    @Inject
    public FlyItem(Game game, Plugin plugin) {
        super(game, plugin);

        this.plugin = plugin;
    }

    @Override
    public int getId() {
        return 2;
    }

    @Override
    protected Material getMaterial() {
        return Material.GHAST_TEAR;
    }

    @Override
    protected String getName() {
        return "Vol";
    }

    @Override
    protected Component[] getLore() {
        return new TextComponent[]{Component
                .text("[", NamedTextColor.WHITE)
                .append(Component.keybind("key.use", NamedTextColor.RED))
                .append(Component.text("]", NamedTextColor.WHITE))
                .append(Component.text(" pour passer en mode fly"))};
    }

    @Override
    protected int getCooldown() {
        return 60;
    }

    @Override
    protected void action(Player player) {
        player.setAllowFlight(true);
        player.setFlying(true);

        player.setVelocity(new Vector(0, 2, 0));
        new BukkitRunnable() {

            @Override
            public void run() {
                player.setFlying(false);
                player.setAllowFlight(false);
            }
        }.runTaskLater(this.plugin, TickUtils.getTicks(3));
    }
}
