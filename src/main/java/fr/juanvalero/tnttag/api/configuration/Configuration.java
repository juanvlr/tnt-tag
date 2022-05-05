package fr.juanvalero.tnttag.api.configuration;

import fr.juanvalero.tnttag.api.game.GameTime;
import org.bukkit.Location;
import org.bukkit.WeatherType;

public interface Configuration {

    /**
     * Returns the location of the lobby.
     *
     * @return The location of the lobby
     */
    Location getLobbyLocation();

    /**
     * Returns the location of the start.
     *
     * @return The location of the start
     */
    Location getStartLocation();

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
    boolean isItemEnabled();

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
     * Returns the time.
     *
     * @return The time
     */
    GameTime getTime();

    /**
     * Sets the time.
     *
     * @param time The time
     */
    void setTime(GameTime time);
}
