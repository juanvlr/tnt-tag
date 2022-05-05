package fr.juanvalero.tnttag.api.utils.item;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ItemStackBuilder {

    private final Material material;
    private final int amount;
    private Component name;
    private List<Component> lore;

    public ItemStackBuilder(Material material) {
        this(material, 1);
    }

    public ItemStackBuilder(Material material, int amount) {
        this.material = material;
        this.amount = amount;
    }

    public ItemStackBuilder withName(Component name) {
        this.name = name;

        return this;
    }

    public ItemStackBuilder withLore(Component... lore) {
        this.lore = List.of(lore);

        return this;
    }

    public ItemStack build() {
        ItemStack item = new ItemStack(this.material, this.amount);

        ItemMeta meta = item.getItemMeta();
        Component name = Component.empty()
                .style(Style.style().decoration(TextDecoration.ITALIC, false));

        if (this.name != null) {
            name = name.append(this.name);
        }

        meta.displayName(name);
        meta.lore(this.lore);

        item.setItemMeta(meta);

        return item;
    }
}
