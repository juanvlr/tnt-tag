/*
 * Copyright (c) 2022 - Juan Valero
 */

package fr.juanvalero.tnttag.api.game.player;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.function.Consumer;
import java.util.function.Predicate;

public interface PlayerCollection extends Cloneable {

    /**
     * Adds a player in this collection.
     *
     * @param player The player to add. He must not be already in the collection.
     */
    void add(Player player);

    /**
     * Removes a player from this collection.
     *
     * @param player The player to remove. He must be in the collection.
     */
    void remove(Player player);

    /**
     * Checks if this collection contains a player.
     *
     * @param player The player to check
     * @return {@code true} if this collection contains the specified player, {@code false} otherwise.
     */
    boolean contains(Player player);

    /**
     * Checks if no player is present in this collection.
     *
     * @return {@code true} if no player is present if this collection, {@code false} otherwise.
     */
    boolean isEmpty();

    /**
     * Returns the amount of players in this collection.
     *
     * @return The amount of players in this collection
     */
    int count();

    /**
     * Returns the first player of this collection.
     * The collection must not be empty.
     *
     * @return The first player of this collection.
     */
    Player getFirst();

    /**
     * Returns a random player excepts one.
     * The collection must contain at least 2 players.
     *
     * @param excludedPlayer The player to exclude from research. He must be in the collection.
     * @return A random player excepts whose specified
     */
    Player getRandom(Player excludedPlayer);

    /**
     * Returns a sub collection of random players.
     *
     * @param amount The number of players to return.
     *               It must be less than the total player amount in this collection.
     * @return A collection of {@code amount} random players
     */
    PlayerCollection getRandoms(int amount);

    /**
     * Performs an action for each player in this collection.
     *
     * @param action The action to perform
     */
    void forEach(Consumer<? super Player> action);

    PlayerCollection filter(Predicate<? super Player> filter);

    Location getClosestLocation(Player player);

    Location getClosestLocation(Player player, Location excludedPlayerLocation);
}
