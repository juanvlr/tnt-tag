/*
 * Copyright (c) 2022 - Juan Valero
 */

package fr.juanvalero.tnttag.gui.utils;

import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.function.Consumer;

public record MaterialToggleButtonItem(String name, Material material, Consumer<InventoryClickEvent> action) {

    public void accept(InventoryClickEvent event) {
        this.action.accept(event);
    }
}
