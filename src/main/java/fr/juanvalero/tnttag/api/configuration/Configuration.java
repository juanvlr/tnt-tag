/*
 * Copyright (c) 2022 - Juan Valero
 */

package fr.juanvalero.tnttag.api.configuration;

import fr.juanvalero.tnttag.api.world.GameTime;
import org.bukkit.Location;
import org.bukkit.WeatherType;

import java.util.Optional;

/**
 * The configuration of the game.
 * Values can be set using the config.yml file or the configuration gui.
 */
public interface Configuration {

    /**
     * Returns the world.
     *
     * @return The world
     */
    String getWorld();

    /**
     * Returns the location of the lobby.
     *
     * @return The location of the lobby
     */
    Optional<Location> getLobbyLocation();

    /**
     * Returns the location of the start.
     *
     * @return The location of the start, if present
     */
    Optional<Location> getStartLocation();

    /**
     * Enables the destruction of tnts.
     */
    void enableTntDestruction();

    /**
     * Disables the destruction of tnts.
     */
    void disableTntDestruction();

    /**
     * Checks if tnts are destructives.
     *
     * @return {@code true} if tnts are destructives, {@code false} otherwise.
     */
    boolean isTntDestructive();

    /**
     * Enables events.
     */
    void enableEvents();

    /**
     * Disables events.
     */
    void disableEvents();

    /**
     * Checks if events are enabled.
     *
     * @return {@code true} if events are enabled, {@code false} otherwise.
     */
    boolean isEventEnabled();

    /**
     * Enables items.
     */
    void enableItems();

    /**
     * Disable items.
     */
    void disableItems();

    /**
     * Checks if items are enabled.
     *
     * @return {@code true} if items are enabled, {@code false} otherwise.
     */
    boolean allowItems();

    /**
     * Increments the amount of items received by the player.
     */
    void incrementItemAmount();

    /**
     * Decrements the amount of items received by the player.
     */
    void decrementItemAmount();

    /**
     * Returns the amount of items received by the player.
     *
     * @return The amount of items that the player will receive
     */
    int getItemAmount();

    /**
     * Changes the weather type.
     *
     * @param weatherType The weather type
     */
    void setWeatherType(WeatherType weatherType);

    /**
     * Returns the weather type.
     *
     * @return The weather type
     */
    WeatherType isWeatherClear();

    /**
     * Returns the game time.
     *
     * @return The game time
     */
    GameTime getTime();

    /**
     * Changes the current time.
     *
     * @param time The new time
     */
    void setTime(GameTime time);
}
