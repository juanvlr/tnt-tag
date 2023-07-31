/*
 * Copyright (c) 2022 - Juan Valero
 */

package fr.juanvalero.tnttag.item;

import fr.juanvalero.tnttag.api.game.Game;
import fr.juanvalero.tnttag.api.game.item.ProjectileItem;
import fr.juanvalero.tnttag.api.utils.scheduler.TickUtils;
import fr.juanvalero.tnttag.api.world.WorldService;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

public class IcyAreaItem extends ProjectileItem {

    private static final int RADIUS = 1;

    private final Plugin plugin;
    private final WorldService worldService;

    @Inject
    public IcyAreaItem(Game game, Plugin plugin, WorldService worldService) {
        super(game, plugin);

        this.plugin = plugin;
        this.worldService = worldService;
    }

    @Override
    public int getId() {
        return 3;
    }

    @Override
    protected Material getMaterial() {
        return Material.SNOWBALL;
    }

    @Override
    protected String getName() {
        return "Zone glacée";
    }

    @Override
    protected Component[] getLore() {
        return new TextComponent[]{Component
                .text("[", NamedTextColor.WHITE)
                .append(Component.keybind("key.use", NamedTextColor.RED))
                .append(Component.text("]", NamedTextColor.WHITE))
                .append(Component.text(" pour crée une zone de glace"))};
    }

    @Override
    protected int getCooldown() {
        return 20;
    }

    @Override
    public void hurt(Block block) {
        Location location = block.getLocation();

        List<Block> nearbyBlocks = this.worldService.getNearbyBlocks(location, RADIUS);
        List<Material> nearbyBlockTypes = nearbyBlocks.stream()
                .map(Block::getType)
                .collect(Collectors.toList());

        nearbyBlocks.forEach(nearbyBlock -> nearbyBlock.setType(Material.PACKED_ICE));

        new BukkitRunnable() {

            @Override
            public void run() {
                int nearbyBlocksAmount = nearbyBlocks.size();
                for (int i = 0; i < nearbyBlocksAmount; i++) {
                    Material nearbyBlockType = nearbyBlockTypes.get(i);
                    if (nearbyBlockType != Material.PACKED_ICE) {
                        nearbyBlocks.get(i).setType(nearbyBlockTypes.get(i));
                    }
                }
            }
        }.runTaskLater(this.plugin, TickUtils.getTicks(5));
    }
}
