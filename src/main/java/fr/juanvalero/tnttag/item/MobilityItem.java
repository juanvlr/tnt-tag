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
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import javax.inject.Inject;

public class MobilityItem extends Item {

    @Inject
    public MobilityItem(Game game, Plugin plugin) {
        super(game, plugin);
    }

    @Override
    public int getId() {
        return 5;
    }

    @Override
    protected Material getMaterial() {
        return Material.RABBIT_FOOT;
    }

    @Override
    protected String getName() {
        return "Mobilité accrue";
    }

    @Override
    protected Component[] getLore() {
        return new TextComponent[]{Component
                .text("[", NamedTextColor.WHITE)
                .append(Component.keybind("key.use", NamedTextColor.RED))
                .append(Component.text("]", NamedTextColor.WHITE))
                .append(Component.text(" pour augmenter sa vitesse et la hauteur de ses sauts"))};
    }

    @Override
    protected int getCooldown() {
        return 60;
    }

    @Override
    protected void action(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, TickUtils.getTicks(5), 2));
        player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, TickUtils.getTicks(5), 2));
    }
}
