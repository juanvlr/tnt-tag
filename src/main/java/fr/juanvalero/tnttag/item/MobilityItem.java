/*
 * Copyright (c) 2022 - Juan Valero
 */

package fr.juanvalero.tnttag.item;

import fr.juanvalero.tnttag.api.game.item.Item;
import fr.juanvalero.tnttag.api.utils.item.ItemStackBuilder;
import fr.juanvalero.tnttag.api.utils.scheduler.TickUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import javax.inject.Inject;

public class MobilityItem extends Item {

    @Inject
    public MobilityItem(Plugin plugin) {
        super(plugin);
    }

    @Override
    public int getId() {
        return 5;
    }

    @Override
    protected int getCooldown() {
        return 60;
    }

    @Override
    protected ItemStack getItemStack() {
        return new ItemStackBuilder(Material.RABBIT_FOOT)
                .withName(
                        Component
                                .text("[", NamedTextColor.WHITE)
                                .append(Component.text("Mobilité accrue", NamedTextColor.RED))
                                .append(Component.text("]", NamedTextColor.WHITE))
                )
                .withLore(
                        Component
                                .text("[", NamedTextColor.WHITE)
                                .append(Component.keybind("key.use", NamedTextColor.RED))
                                .append(Component.text("]", NamedTextColor.WHITE))
                                .append(Component.text(" pour augmenter sa vitesse et la hauteur de ses sauts"))
                )
                .build();
    }

    @Override
    protected void action(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, TickUtils.getTicks(5), 2));
        player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, TickUtils.getTicks(5), 2));
    }
}
