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
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import javax.inject.Inject;

public class InvisibilityItem extends Item {

    @Inject
    public InvisibilityItem(Plugin plugin) {
        super(plugin);
    }

    @Override
    public int getId() {
        return 4;
    }

    @Override
    protected int getCooldown() {
        return 30;
    }

    @Override
    protected ItemStack getItemStack() {
        return new ItemStackBuilder(Material.GRAY_DYE)
                .withName(
                        Component
                                .text("[", NamedTextColor.WHITE)
                                .append(Component.text("Invisibilité", NamedTextColor.RED))
                                .append(Component.text("]", NamedTextColor.WHITE))
                )
                .withLore(
                        Component
                                .text("[", NamedTextColor.WHITE)
                                .append(Component.keybind("key.use", NamedTextColor.RED))
                                .append(Component.text("]", NamedTextColor.WHITE))
                                .append(Component.text(" pour devenir invisible"))
                )
                .build();
    }

    @Override
    protected void action(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, TickUtils.getTicks(5), 1));
    }
}
