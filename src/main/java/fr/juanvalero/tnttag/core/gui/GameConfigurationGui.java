package fr.juanvalero.tnttag.core.gui;

import com.github.stefvanschie.inventoryframework.font.util.Font;
import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.gui.type.util.Gui;
import com.github.stefvanschie.inventoryframework.pane.OutlinePane;
import com.github.stefvanschie.inventoryframework.pane.Pane;
import com.github.stefvanschie.inventoryframework.pane.PatternPane;
import com.github.stefvanschie.inventoryframework.pane.component.Label;
import com.github.stefvanschie.inventoryframework.pane.util.Pattern;
import fr.juanvalero.tnttag.api.configuration.Configuration;
import fr.juanvalero.tnttag.api.configuration.inject.InjectConfiguration;
import fr.juanvalero.tnttag.api.game.Game;
import fr.juanvalero.tnttag.api.game.GameConstants;
import fr.juanvalero.tnttag.api.game.GameTime;
import fr.juanvalero.tnttag.api.utils.gui.MaterialToggleButton;
import fr.juanvalero.tnttag.api.utils.gui.MaterialToggleButtonItem;
import fr.juanvalero.tnttag.api.utils.gui.TitleToggleButton;
import fr.juanvalero.tnttag.api.utils.gui.TitleToggleButtonItem;
import fr.juanvalero.tnttag.api.utils.item.ItemStackBuilder;
import fr.juanvalero.tnttag.api.world.WorldService;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.WeatherType;
import org.bukkit.inventory.ItemStack;

import javax.inject.Inject;

import static fr.juanvalero.tnttag.api.utils.gui.GuiUtils.*;

public class GameConfigurationGui {

    private final Game game;
    private final WorldService worldService;
    @InjectConfiguration
    private Configuration configuration;

    @Inject
    public GameConfigurationGui(Game game, WorldService worldService) {
        this.game = game;
        this.worldService = worldService;
    }

    public Gui build() {
        ChestGui mainGui = createGui(6, Component.text("Configuration de la partie"));

        // ITEM AMOUNT GUI
        ChestGui itemAmountGui = createGui(3, Component.text("Nombre d'items"));

        int itemAmount = this.configuration.getItemAmount();

        ItemStack itemAmountSelector = new ItemStackBuilder(Material.DIAMOND, itemAmount)
                .withName(Component.text("Items"))
                .build();

        OutlinePane itemAmountSelectorPane = new OutlinePane(4, 1, 1, 1);
        itemAmountSelectorPane.addItem(new GuiItem(itemAmountSelector));

        Label itemAmountSelectorDecrementButton = createLabel(2, 1, Font.OAK_PLANKS, "-", "-1");

        if (itemAmount == 1) {
            // We can't remove any item, so we can't use the decrement button
            itemAmountSelectorDecrementButton.setVisible(false);
        }

        Label itemAmountSelectorIncrementButton = createLabel(6, 1, Font.OAK_PLANKS, "+", "+1");

        if (itemAmount == GameConstants.MAXIMUM_ITEM_COUNT) {
            // We can't add any item, so we can't use the increment amount
            itemAmountSelectorIncrementButton.setVisible(false);
        }

        itemAmountSelectorDecrementButton.setOnClick(event -> {
            if (itemAmountSelector.getAmount() <= 1) {
                event.setCancelled(true);

                return;
            }

            itemAmountSelector.add(-1);
            this.configuration.decrementItemAmount();

            if (!itemAmountSelectorIncrementButton.isVisible()) {
                itemAmountSelectorIncrementButton.setVisible(true);
            }

            if (itemAmountSelector.getAmount() == 1) {
                itemAmountSelectorDecrementButton.setVisible(false);
            }

            itemAmountGui.update();
        });

        itemAmountSelectorIncrementButton.setOnClick(event -> {
            if (itemAmountSelector.getAmount() >= GameConstants.MAXIMUM_ITEM_COUNT) {
                event.setCancelled(true);

                return;
            }

            itemAmountSelector.add();
            this.configuration.incrementItemAmount();

            if (!itemAmountSelectorDecrementButton.isVisible()) {
                itemAmountSelectorDecrementButton.setVisible(true);
            }

            if (itemAmountSelector.getAmount() == GameConstants.MAXIMUM_ITEM_COUNT) {
                itemAmountSelectorIncrementButton.setVisible(false);
            }

            itemAmountGui.update();
        });

        itemAmountGui.addPane(itemAmountSelectorPane);
        itemAmountGui.addPane(itemAmountSelectorDecrementButton);
        itemAmountGui.addPane(itemAmountSelectorIncrementButton);
        itemAmountGui.addPane(createLeaveButton(mainGui));

        // ITEM AMOUNT GUI

        // WEATHER GUI
        ChestGui weatherGui = createGui(3, Component.text("Météo / Heure"));

        Pane weatherButton = createMaterialToggleButton(
                new MaterialToggleButton(3, 1, this.configuration.isWeatherClear() == WeatherType.CLEAR),
                new MaterialToggleButtonItem(
                        "Soleil",
                        Material.LAVA_BUCKET,
                        event -> {
                            this.worldService.setSun();
                            this.configuration.setWeatherType(WeatherType.CLEAR);
                        }),
                new MaterialToggleButtonItem(
                        "Pluie",
                        Material.WATER_BUCKET,
                        event -> {
                            this.worldService.setRain();
                            this.configuration.setWeatherType(WeatherType.DOWNFALL);
                        }
                )
        );

        Pane timeButton = createMaterialToggleButton(
                new MaterialToggleButton(5, 1, this.configuration.getTime() == GameTime.DAY),
                new MaterialToggleButtonItem(
                        "Jour",
                        Material.TORCH,
                        event -> {
                            this.worldService.setDay();
                            this.configuration.setTime(GameTime.DAY);
                        }),
                new MaterialToggleButtonItem(
                        "Nuit",
                        Material.COAL,
                        event -> {
                            this.worldService.setNight();
                            this.configuration.setTime(GameTime.NIGHT);
                        }
                )
        );

        weatherGui.addPane(weatherButton);
        weatherGui.addPane(timeButton);
        weatherGui.addPane(createLeaveButton(mainGui));

        // WEATHER GUI

        // MAIN GUI
        Pattern pattern = new Pattern(
                "111222111",
                "112222211",
                "222023222",
                "222020222",
                "112222211",
                "411252111"
        );

        PatternPane patternPane = new PatternPane(9, 6, pattern);

        patternPane.bindItem('1', new GuiItem(new ItemStackBuilder(Material.RED_STAINED_GLASS_PANE).build()));
        patternPane.bindItem('2', new GuiItem(new ItemStackBuilder(Material.WHITE_STAINED_GLASS_PANE).build()));
        patternPane.bindItem('3', new GuiItem(
                new ItemStackBuilder(Material.NAME_TAG)
                        .withName(Component.text("Nombre d'items"))
                        .build(),
                event -> itemAmountGui.show(event.getWhoClicked()))
        );
        patternPane.bindItem('4', new GuiItem(
                new ItemStackBuilder(Material.BLAZE_POWDER)
                        .withName(Component.text("Météo / Heure"))
                        .build(),
                event -> weatherGui.show(event.getWhoClicked()))
        );
        patternPane.bindItem('5', new GuiItem(
                new ItemStackBuilder(Material.EMERALD)
                        .withName(Component.text("Commencer la partie"))
                        .build(),
                event -> {
                    this.game.startDelayed();
                    //noinspection ConstantConditions
                    event.getClickedInventory().close(); // Not sure about this one
                })
        );

        mainGui.addPane(patternPane);

        Pane tntDestructionButton = createTitleToggleButton(
                new TitleToggleButton(3, 2, this.configuration.isTntDestructive(), "Tnt", Material.TNT),
                new TitleToggleButtonItem("Activée", event -> this.configuration.enableTntDestruction()),
                new TitleToggleButtonItem("Désactivée", event -> this.configuration.disableTntDestruction())
        );

        mainGui.addPane(tntDestructionButton);

        Pane eventActivationButton = createTitleToggleButton(
                new TitleToggleButton(3, 3, this.configuration.isEventEnabled(), "Événements", Material.BOOKSHELF),
                new TitleToggleButtonItem("Activés", event -> this.configuration.enableEvents()),
                new TitleToggleButtonItem("Désactivés", event -> this.configuration.disableEvents())
        );

        mainGui.addPane(eventActivationButton);

        Pane itemActivationButton = createTitleToggleButton(
                new TitleToggleButton(5, 3, this.configuration.isItemEnabled(), "Items", Material.FEATHER),
                new TitleToggleButtonItem("Activés", event -> this.configuration.enableItems()),
                new TitleToggleButtonItem("Désactivés", event -> this.configuration.disableItems())
        );

        mainGui.addPane(itemActivationButton);

        // MAIN GUI

        return mainGui;
    }
}
