/*
 * Copyright (c) 2022 - Juan Valero
 */

package fr.juanvalero.tnttag.item;

import fr.juanvalero.tnttag.api.game.Game;
import fr.juanvalero.tnttag.api.game.item.Item;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import javax.inject.Inject;

public class RepulsionItem extends Item {

    private static final double RADIUS = 10.;

    @Inject
    public RepulsionItem(Game game, Plugin plugin) {
        super(game, plugin);
    }

    @Override
    public int getId() {
        return 6;
    }

    @Override
    protected Material getMaterial() {
        return Material.BLAZE_POWDER;
    }

    @Override
    protected String getName() {
        return "Répulsion";
    }

    @Override
    protected Component[] getLore() {
        return new TextComponent[]{Component
                .text("[", NamedTextColor.WHITE)
                .append(Component.keybind("key.use", NamedTextColor.RED))
                .append(Component.text("]", NamedTextColor.WHITE))
                .append(Component.text(" pour repousser ses adversaires"))};
    }

    @Override
    protected int getCooldown() {
        return 20;
    }

    @Override
    public void click(Player player, boolean isSoundEnabled) {
        super.click(player, false);
    }

    @Override
    protected void action(Player player) {
        player.playSound(player.getLocation(), Sound.ENTITY_BLAZE_AMBIENT, 1f, 1f);

        Location location = player.getLocation();
        location.getWorld().spawnParticle(Particle.FLAME, location, 5);

        // Nearby players except the player himself
        location.getNearbyPlayers(RADIUS, nearbyPlayer -> !nearbyPlayer.getUniqueId().equals(player.getUniqueId()) && nearbyPlayer.getGameMode() != GameMode.SPECTATOR)
                .forEach(nearbyPlayer -> {
                    Vector playerDirection = nearbyPlayer.getLocation().getDirection();
                    nearbyPlayer.setVelocity(playerDirection.multiply(-1.5).setY(1));
                });
    }
}
