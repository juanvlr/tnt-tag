/*
 * Copyright (c) 2022 - Juan Valero
 */

package fr.juanvalero.tnttag.api.world;

import fr.juanvalero.tnttag.api.configuration.Configuration;
import org.bukkit.Location;
import org.bukkit.block.Block;

import java.util.List;

/**
 * World utilities.
 * The world in which the game is played can be configured inside the config.yml.
 *
 * @see Configuration#getWorld()
 */
public interface WorldService {

    /**
     * Inits the world in which the game is played.
     */
    void init();

    /**
     * Changes the time to day.
     */
    void setDay();

    /**
     * Changes the time to night.
     */
    void setNight();

    /**
     * Summons the rain.
     */
    void setRain();

    /**
     * Clears the weather.
     */
    void setSun();

    /**
     * Returns the nearby blocks inside a circle.
     *
     * @param center The {@link Location} of the center of the circle
     * @param radius The radius of the circle
     * @return A list of the nearby blocks inside the specified circle.
     */
    List<Block> getNearbyBlocks(Location center, int radius);

    /**
     * Spawns a firework at a location.
     *
     * @param location The location where to spawn the firework
     */
    void spawnFirework(Location location);
}
