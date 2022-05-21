/*
 * Copyright (c) 2022 - Juan Valero
 */

package fr.juanvalero.tnttag.item;

import fr.juanvalero.tnttag.api.game.Game;
import fr.juanvalero.tnttag.api.game.item.Item;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import javax.inject.Inject;

public class DashItem extends Item {

    @Inject
    public DashItem(Game game, Plugin plugin) {
        super(game, plugin);
    }

    @Override
    public int getId() {
        return 1;
    }

    @Override
    protected Material getMaterial() {
        return Material.FEATHER;
    }

    @Override
    protected String getName() {
        return "Dash";
    }

    @Override
    protected Component[] getLore() {
        return new TextComponent[]{Component
                .text("[", NamedTextColor.WHITE)
                .append(Component.keybind("key.use", NamedTextColor.RED))
                .append(Component.text("]", NamedTextColor.WHITE))
                .append(Component.text(" pour se propulser en avant"))};
    }

    @Override
    protected int getCooldown() {
        return 20;
    }

    @Override
    protected void action(Player player) {
        Vector playerDirection = player.getLocation().getDirection();
        player.setVelocity(playerDirection.multiply(1.5).setY(1));
    }
}
