/*
 * Copyright (c) 2022 - Juan Valero
 */

package fr.juanvalero.tnttag.api.utils.item;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class SkullItemStackBuilder extends ItemStackBuilder {

    private final String icon;

    public SkullItemStackBuilder(String icon) {
        this(icon, 1);
    }

    public SkullItemStackBuilder(String icon, int amount) {
        super(Material.PLAYER_HEAD, amount);

        this.icon = icon;
    }

    @SuppressWarnings("deprecation")
    @Override
    public ItemStack build() {
        ItemStack item = super.build();

        SkullMeta meta = (SkullMeta) item.getItemMeta();
        meta.setOwner(this.icon); // Sorry, don't have any other idea

        item.setItemMeta(meta);

        return item;
    }
}
