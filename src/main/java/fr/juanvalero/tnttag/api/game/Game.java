package fr.juanvalero.tnttag.api.game;

import fr.juanvalero.tnttag.api.game.player.PlayerCollection;
import org.bukkit.entity.Player;

public interface Game {

    /**
     * Starts the game.
     */
    void start();

    /**
     * Stops the game.
     */
    void stop();

    /**
     * Checks if the game is closed.
     * The game is closed if it has started or if it is full.
     *
     * @return {@code true} if the game is closed, {@code false} otherwise
     */
    boolean isClosed();

    /**
     * Adds a player to the game.
     * The game must not have started.
     *
     * @param player The player to be added
     */
    void addPlayer(Player player);

    /**
     * Removes a player from the game.
     * If the game has already started, the player will be killed.
     *
     * @param player The player to be removed
     */
    void removePlayer(Player player);

    /**
     * Returns a collection of the players.
     *
     * @return A collection of the players
     */
    PlayerCollection getPlayers();
}
