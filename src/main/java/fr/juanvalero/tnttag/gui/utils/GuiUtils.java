package fr.juanvalero.tnttag.gui.utils;

import com.github.stefvanschie.inventoryframework.adventuresupport.ComponentHolder;
import com.github.stefvanschie.inventoryframework.font.util.Font;
import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.gui.type.util.Gui;
import com.github.stefvanschie.inventoryframework.pane.OutlinePane;
import com.github.stefvanschie.inventoryframework.pane.Pane;
import com.github.stefvanschie.inventoryframework.pane.component.Label;
import com.github.stefvanschie.inventoryframework.pane.component.ToggleButton;
import fr.juanvalero.tnttag.api.utils.item.ItemStackBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.bukkit.inventory.meta.ItemMeta;

public class GuiUtils {

    public static ChestGui createGui(int rows, Component title) {
        ChestGui gui = new ChestGui(rows, ComponentHolder.of(title));
        gui.setOnGlobalClick(event -> event.setCancelled(true));

        return gui;
    }

    public static Label createLabel(int x, int y, Font font, String text, String name) {
        Label label = new Label(x, y, 1, 1, font);
        label.setText(text, (character, item) -> {
            ItemMeta meta = item.getItemMeta();
            meta.displayName(Component.text(name));
            item.setItemMeta(meta);

            return new GuiItem(item);
        });

        return label;
    }

    public static Pane createLeaveButton(Gui gui) {
        OutlinePane leavePane = new OutlinePane(0, 2, 1, 1);
        leavePane.addItem(
                new GuiItem(
                        new ItemStackBuilder(Material.OAK_DOOR)
                                .withName(Component.text("Retour au menu"))
                                .build(),
                        event -> gui.show(event.getWhoClicked())
                )
        );

        return leavePane;
    }

    public static Pane createMaterialToggleButton(
            MaterialToggleButton button,
            MaterialToggleButtonItem enableItem,
            MaterialToggleButtonItem disableItem) {
        ToggleButton toggleButton = new ToggleButton(button.x(), button.y(), 1, 1, button.defaultValue());

        toggleButton.setOnClick(event -> {
            if (toggleButton.isEnabled()) {
                enableItem.accept(event);
            } else {
                disableItem.accept(event);
            }
        });

        toggleButton.setEnabledItem(createItemFromMaterialToggleButton(enableItem));
        toggleButton.setDisabledItem(createItemFromMaterialToggleButton(disableItem));

        return toggleButton;
    }

    private static GuiItem createItemFromMaterialToggleButton(MaterialToggleButtonItem buttonItem) {
        return new GuiItem(
                new ItemStackBuilder(buttonItem.material())
                        .withName(Component.text(buttonItem.name()))
                        .build()
        );
    }

    public static Pane createTitleToggleButton(
            TitleToggleButton button,
            TitleToggleButtonItem enableItem,
            TitleToggleButtonItem disableItem
    ) {
        ToggleButton toggleButton = new ToggleButton(button.x(), button.y(), 1, 1, button.defaultValue());

        toggleButton.setOnClick(event -> {
            if (toggleButton.isEnabled()) {
                enableItem.accept(event);
            } else {
                disableItem.accept(event);
            }
        });

        toggleButton.setEnabledItem(createItemFromTitleToggleButton(button, enableItem, NamedTextColor.GREEN));
        toggleButton.setDisabledItem(createItemFromTitleToggleButton(button, disableItem, NamedTextColor.RED));

        return toggleButton;
    }

    private static GuiItem createItemFromTitleToggleButton(TitleToggleButton button, TitleToggleButtonItem buttonItem, TextColor color) {
        return new GuiItem(
                new ItemStackBuilder(button.material())
                        .withName(Component.text(button.name())
                                .append(Component.text(" : "))
                                .append(Component.text(buttonItem.name(), color)))
                        .build()
        );
    }
}
