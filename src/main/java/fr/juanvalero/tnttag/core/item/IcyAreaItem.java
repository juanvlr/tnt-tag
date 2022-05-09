package fr.juanvalero.tnttag.core.item;

import fr.juanvalero.tnttag.api.game.item.ProjectileItem;
import fr.juanvalero.tnttag.api.utils.item.ItemStackBuilder;
import fr.juanvalero.tnttag.api.utils.scheduler.TickUtils;
import fr.juanvalero.tnttag.api.world.WorldService;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.IntStream;

public class IcyAreaItem extends ProjectileItem {

    private static final int RADIUS = 2;
    private final Plugin plugin;
    private final WorldService worldService;

    @Inject
    public IcyAreaItem(Plugin plugin, WorldService worldService) {
        super(plugin);

        this.plugin = plugin;
        this.worldService = worldService;
    }

    @Override
    public int getId() {
        return 3;
    }

    @Override
    protected int getCooldown() {
        return 20;
    }

    @Override
    protected ItemStack getItemStack() {
        return new ItemStackBuilder(Material.SNOWBALL)
                .withName(
                        Component
                                .text("[", NamedTextColor.WHITE)
                                .append(Component.text("Zone glacée", NamedTextColor.RED))
                                .append(Component.text("]", NamedTextColor.WHITE))
                )
                .withLore(
                        Component
                                .text("[", NamedTextColor.WHITE)
                                .append(Component.keybind("key.use", NamedTextColor.RED))
                                .append(Component.text("]", NamedTextColor.WHITE))
                                .append(Component.text(" pour crée une zone de glace"))
                )
                .build();
    }

    @Override
    public void hurt(Player player, Block block) {
        Location location = block.getLocation();

        List<Block> nearbyBlocks = this.worldService.getNearbyBlocks(location, RADIUS);
        List<Material> nearbyBlockTypes = nearbyBlocks.stream()
                .map(Block::getType)
                .toList();

        nearbyBlocks.forEach(nearbyBlock -> nearbyBlock.setType(Material.PACKED_ICE));

        new BukkitRunnable() {

            @Override
            public void run() {
                IntStream.range(0, nearbyBlocks.size())
                        .forEach(i -> nearbyBlocks.get(i).setType(nearbyBlockTypes.get(i)));
            }
        }.runTaskLater(this.plugin, TickUtils.getTicks(5));
    }
}
