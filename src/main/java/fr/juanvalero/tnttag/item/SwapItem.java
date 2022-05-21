/*
 * Copyright (c) 2022 - Juan Valero
 */

package fr.juanvalero.tnttag.item;

import fr.juanvalero.tnttag.api.game.Game;
import fr.juanvalero.tnttag.api.game.item.Item;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import javax.inject.Inject;

public class SwapItem extends Item {

    @Inject
    public SwapItem(Game game, Plugin plugin) {
        super(game, plugin);
    }

    @Override
    public int getId() {
        return 7;
    }

    @Override
    protected Material getMaterial() {
        return Material.ENDER_PEARL;
    }

    @Override
    protected String getName() {
        return "Swap";
    }

    @Override
    protected Component[] getLore() {
        return new TextComponent[]{Component
                .text("[", NamedTextColor.WHITE)
                .append(Component.keybind("key.use", NamedTextColor.RED))
                .append(Component.text("]", NamedTextColor.WHITE))
                .append(Component.text(" pour échanger sa position avec celle d'un joueur"))};
    }

    @Override
    protected int getCooldown() {
        return 60;
    }

    @Override
    protected void action(Player player) {
        Player targetPlayer = this.game.getAlivePlayers().getRandom(player);

        Location currentLocation = player.getLocation();
        Location targetLocation = targetPlayer.getLocation();

        targetPlayer.teleport(currentLocation);
        targetPlayer.playSound(currentLocation, Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 1f);
        targetPlayer.spawnParticle(Particle.PORTAL, currentLocation, 50);

        player.teleport(targetLocation);
        player.playSound(targetLocation, Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 1f);
        player.spawnParticle(Particle.PORTAL, targetLocation, 50);

        this.game.getTaggedPlayers().forEach(taggedPlayer ->
                taggedPlayer.setCompassTarget(
                        this.game.getAlivePlayers()
                                .filter(p -> !this.game.isTagged(p))
                                .getClosestLocation(taggedPlayer)
                )
        );
    }
}
