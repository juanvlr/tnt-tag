package fr.juanvalero.tnttag.api.configuration;

import org.bukkit.Location;

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
}
