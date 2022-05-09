package fr.juanvalero.tnttag.core.item;

import fr.juanvalero.tnttag.api.game.item.Item;
import fr.juanvalero.tnttag.api.utils.item.ItemStackBuilder;
import fr.juanvalero.tnttag.api.utils.scheduler.TickUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import javax.inject.Inject;

public class FlyItem extends Item {

    private final Plugin plugin;

    @Inject
    public FlyItem(Plugin plugin) {
        super(plugin);

        this.plugin = plugin;
    }

    @Override
    public int getId() {
        return 2;
    }

    @Override
    protected int getCooldown() {
        return 60;
    }

    @Override
    protected ItemStack getItemStack() {
        return new ItemStackBuilder(Material.GHAST_TEAR)
                .withName(
                        Component
                                .text("[", NamedTextColor.WHITE)
                                .append(Component.text("Vol", NamedTextColor.RED))
                                .append(Component.text("]", NamedTextColor.WHITE))
                )
                .withLore(
                        Component
                                .text("[", NamedTextColor.WHITE)
                                .append(Component.keybind("key.use", NamedTextColor.RED))
                                .append(Component.text("]", NamedTextColor.WHITE))
                                .append(Component.text(" pour passer en mode fly"))
                )
                .build();
    }

    @Override
    protected void action(Player player) {
        player.setAllowFlight(true);

        new BukkitRunnable() {

            @Override
            public void run() {
                player.setFlying(false);
                player.setAllowFlight(false);
            }
        }.runTaskLater(this.plugin, TickUtils.getTicks(5));
    }
}
