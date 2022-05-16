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

public class DashItem extends Item {

    @Inject
    public DashItem(Plugin plugin) {
        super(plugin);
    }

    @Override
    public int getId() {
        return 1;
    }

    @Override
    protected int getCooldown() {
        return 20;
    }

    @Override
    protected ItemStack getItemStack() {
        return new ItemStackBuilder(Material.FEATHER)
                .withName(
                        Component
                                .text("[", NamedTextColor.WHITE)
                                .append(Component.text("Dash", NamedTextColor.RED))
                                .append(Component.text("]", NamedTextColor.WHITE))
                )
                .withLore(
                        Component
                                .text("[", NamedTextColor.WHITE)
                                .append(Component.keybind("key.use", NamedTextColor.RED))
                                .append(Component.text("]", NamedTextColor.WHITE))
                                .append(Component.text(" pour se propulser en avant"))
                )
                .build();
    }

    @Override
    protected void action(Player player) {
        Vector playerDirection = player.getLocation().getDirection();
        player.setVelocity(playerDirection.multiply(1.5).setY(1));
    }
}
