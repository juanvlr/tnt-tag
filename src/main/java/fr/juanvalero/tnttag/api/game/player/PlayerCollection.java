package fr.juanvalero.tnttag.api.game.player;

import org.bukkit.entity.Player;

import java.util.function.Consumer;

public interface PlayerCollection {

    /**
     * Returns the amount of players in this collection.
     *
     * @return The amount of players in this collection
     */
    int count();

    /**
     * Checks if no player is present in this collection.
     *
     * @return {@code true} if no player is present if this collection, {@code false} otherwise.
     */
    boolean isEmpty();

    /**
     * Adds the specified player in this collection.
     *
     * @param player The player to be added
     */
    void add(Player player);

    /**
     * Removes the specified from this collection.
     *
     * @param player The player to be removed
     */
    void remove(Player player);

    /**
     * Performs an action for each player in this collection.
     *
     * @param action The action to perform
     */
    void forEach(Consumer<? super Player> action);
}
