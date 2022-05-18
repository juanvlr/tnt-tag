/*
 * Copyright (c) 2022 - Juan Valero
 */

package fr.juanvalero.tnttag.gui.utils;

import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.function.Consumer;

public final class MaterialToggleButtonItem {

    private final String name;
    private final Material material;
    private final Consumer<InventoryClickEvent> action;

    public MaterialToggleButtonItem(String name, Material material, Consumer<InventoryClickEvent> action) {
        this.name = name;
        this.material = material;
        this.action = action;
    }

    public String getName() {
        return this.name;
    }

    public Material getMaterial() {
        return this.material;
    }

    public void accept(InventoryClickEvent event) {
        this.action.accept(event);
    }
}
