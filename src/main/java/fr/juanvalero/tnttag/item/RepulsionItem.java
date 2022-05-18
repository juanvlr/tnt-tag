/*
 * Copyright (c) 2022 - Juan Valero
 */

package fr.juanvalero.tnttag.item;

import fr.juanvalero.tnttag.api.game.item.Item;
import fr.juanvalero.tnttag.api.utils.item.ItemStackBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import javax.inject.Inject;

public class RepulsionItem extends Item {

    private static final double RADIUS = 10.;

    @Inject
    public RepulsionItem(Plugin plugin) {
        super(plugin);
    }

    @Override
    public int getId() {
        return 6;
    }

    @Override
    protected int getCooldown() {
        return 20;
    }

    @Override
    protected ItemStack getItemStack() {
        return new ItemStackBuilder(Material.BLAZE_POWDER)
                .withName(
                        Component
                                .text("[", NamedTextColor.WHITE)
                                .append(Component.text("Répulsion", NamedTextColor.RED))
                                .append(Component.text("]", NamedTextColor.WHITE))
                )
                .withLore(
                        Component
                                .text("[", NamedTextColor.WHITE)
                                .append(Component.keybind("key.use", NamedTextColor.RED))
                                .append(Component.text("]", NamedTextColor.WHITE))
                                .append(Component.text(" pour repousser ses adversaires"))
                )
                .build();
    }

    @Override
    protected void action(Player player) {
        // Nearby players except the player himself
        player.getLocation().getNearbyPlayers(RADIUS, nearbyPlayer -> !nearbyPlayer.getUniqueId().equals(player.getUniqueId()))
                .forEach(nearbyPlayer -> {
                    Vector playerDirection = nearbyPlayer.getLocation().getDirection();
                    nearbyPlayer.setVelocity(playerDirection.multiply(-1.5).setY(1));
                });
    }
}
