/*
 * Copyright (c) 2022 - Juan Valero
 */

package fr.juanvalero.tnttag.api.configuration;

import fr.juanvalero.tnttag.api.game.GameConstants;
import fr.juanvalero.tnttag.api.world.GameTime;
import org.bukkit.Location;
import org.bukkit.WeatherType;
import org.bukkit.configuration.file.FileConfiguration;
import org.slf4j.Logger;

import javax.inject.Inject;
import java.util.Optional;

/**
 * Default {@link Configuration} implementation.
 */
public class ConfigurationImpl implements Configuration {

    @Inject
    private Logger logger;

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
        return this.configuration.getString("world", "world");
    }

    @Override
    public Optional<Location> getLobbyLocation() {
        if (!this.configuration.contains("lobby-location")) {
            this.logger.warn("No lobby location has been provided in the configuration");
        }

        return Optional.ofNullable(this.configuration.getLocation("lobby-location"));
    }

    @Override
    public Optional<Location> getStartLocation() {
        if (!this.configuration.contains("start-location")) {
            this.logger.warn("No start location has been provided in the configuration");
        }

        return Optional.ofNullable(this.configuration.getLocation("start-location"));
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
    public boolean allowItems() {
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
