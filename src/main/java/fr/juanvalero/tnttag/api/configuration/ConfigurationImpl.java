package fr.juanvalero.tnttag.api.configuration;

import fr.juanvalero.tnttag.api.game.GameConstants;
import fr.juanvalero.tnttag.api.game.GameTime;
import org.bukkit.Location;
import org.bukkit.WeatherType;
import org.bukkit.configuration.file.FileConfiguration;

import javax.inject.Inject;

public class ConfigurationImpl implements Configuration {

    private final FileConfiguration configuration;

    private boolean isTntDestructive = false;
    private boolean isEventEnabled = true;
    private boolean isItemEnabled = true;
    private int itemAmount = 1;
    private WeatherType weatherType = WeatherType.CLEAR;
    private GameTime time = GameTime.DAY;

    @Inject
    public ConfigurationImpl(FileConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public String getWorld() {
        return this.configuration.getString("world");
    }

    @Override
    public Location getLobbyLocation() {
        return this.configuration.getLocation("lobby-location");
    }

    @Override
    public Location getStartLocation() {
        return this.configuration.getLocation("start-location");
    }

    @Override
    public void enableTntDestruction() {
        this.isTntDestructive = true;
    }

    @Override
    public void disableTntDestruction() {
        this.isTntDestructive = false;
    }

    @Override
    public boolean isTntDestructive() {
        return this.isTntDestructive;
    }

    @Override
    public void enableEvents() {
        this.isEventEnabled = true;
    }

    @Override
    public void disableEvents() {
        this.isEventEnabled = false;
    }

    @Override
    public boolean isEventEnabled() {
        return this.isEventEnabled;
    }

    @Override
    public void enableItems() {
        this.isItemEnabled = true;
    }

    @Override
    public void disableItems() {
        this.isItemEnabled = false;
    }

    @Override
    public boolean isItemEnabled() {
        return this.isItemEnabled;
    }

    @Override
    public void incrementItemAmount() {
        if (this.itemAmount == GameConstants.MAXIMUM_ITEM_COUNT) {
            throw new IllegalStateException(String.format("Item amount cannot be more than %d", GameConstants.MAXIMUM_ITEM_COUNT));
        }

        this.itemAmount++;
    }

    @Override
    public void decrementItemAmount() {
        if (this.itemAmount == 1) {
            throw new IllegalStateException("Item amount cannot be less than 1");
        }

        this.itemAmount--;
    }

    @Override
    public int getItemAmount() {
        return this.itemAmount;
    }

    @Override
    public void setWeatherType(WeatherType weatherType) {
        this.weatherType = weatherType;
    }

    @Override
    public WeatherType isWeatherClear() {
        return this.weatherType;
    }

    @Override
    public GameTime getTime() {
        return this.time;
    }

    @Override
    public void setTime(GameTime time) {
        this.time = time;
    }
}
