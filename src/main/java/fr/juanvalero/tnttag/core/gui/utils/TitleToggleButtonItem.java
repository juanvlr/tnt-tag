package fr.juanvalero.tnttag.core.gui.utils;

import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.function.Consumer;

public record TitleToggleButtonItem(String name, Consumer<InventoryClickEvent> action) {

    public void accept(InventoryClickEvent event) {
        this.action.accept(event);
    }
}
