/*
 * Copyright (c) 2022 - Juan Valero
 */

package fr.juanvalero.tnttag.gui.utils;

import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.function.Consumer;

public final class TitleToggleButtonItem {

    private final String name;
    private final Consumer<InventoryClickEvent> action;

    public TitleToggleButtonItem(String name, Consumer<InventoryClickEvent> action) {
        this.name = name;
        this.action = action;
    }

    public String getName() {
        return this.name;
    }

    public void accept(InventoryClickEvent event) {
        this.action.accept(event);
    }
}
