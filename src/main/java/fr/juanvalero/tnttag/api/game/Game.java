/*
 * Copyright (c) 2022 - Juan Valero
 */

package fr.juanvalero.tnttag.api.game;

import fr.juanvalero.tnttag.api.game.player.PlayerCollection;
import org.bukkit.entity.Player;

/**
 * At the start of each round, random players are designated to be tagged.
 * A timer begins.
 * During this time, tagged players can tag other players by hitting them to lose their own tnt.
 * When the time is up, tagged players blow up and a new round begins.
 * <p>
 * The game can be started using {@link #start()} or stopped using {@link #stop()}.
 * Players can be added or removed from the game using {@link #join(Player)} and {@link #leave(Player)}
 * respectively.
 */
public interface Game {

    /**
     * Starts the game instantly.
     * The game will automatically be started when there is enough players.
     * The game is considered not to have started yet.
     */
    void start();

    /**
     * Checks if the game has started.
     *
     * @return {@code true} if the game has started, {@code false} otherwise
     */
    boolean hasStarted();

    /**
     * Forces the game to start after a short delay, even if there is not enough players yet.
     * This can be triggered by a player using the configuration gui.
     * The game is considered not to have started yet, and it is considered to have more than 1 player in the game.
     */
    void startDelayed();

    /**
     * Checks if the game is fast starting.
     *
     * @return {@code true} if the game is fast starting, {@code false} otherwise.
     * @see #startDelayed()
     */
    boolean isFastStarting();

    /**
     * Stops the game.
     * The game is considered not to have stopped yet.
     */
    void stop();

    /**
     * Checks if the game is closed.
     * If the game is closed, no more player can join it.
     * The game is closed if it has already started, if it is stopping or if it is full.
     *
     * @return {@code true} if the game is closed, {@code false} otherwise
     */
    boolean isClosed();

    /**
     * Tags a player.
     * All tagged players will blow up at the end of each round.
     * If the damager is not tagged or if the defender is already tagged, this method will have no effect.
     *
     * @param damager  The player who tags
     * @param defender The player whose tagged
     * @see #blowUp()
     */
    void tag(Player damager, Player defender);

    /**
     * Blows up the tnts and kills the tagged players.
     */
    void blowUp();

    /**
     * Adds a player to the game.
     * The game is considered not to have started yet.
     *
     * @param player The player to add
     */
    void join(Player player);

    /**
     * Removes a player from the game.
     * If the game has already started, the player will be killed.
     *
     * @param player The player to remove
     */
    void leave(Player player);

    /**
     * Returns a collection of the tagged players.
     *
     * @return A collection of the tagged players
     */
    PlayerCollection getTaggedPlayers();

    /**
     * Returns a collection of the players alive.
     *
     * @return A collection of the players alive
     */
    PlayerCollection getAlivePlayers();

    /**
     * Checks if a player is tagged.
     *
     * @param player The player to check
     * @return {@code true} if the player is tagged, {@code false} otherwise.
     */
    boolean isTagged(Player player);

    /**
     * Checks if a player is a spectator.
     *
     * @param player The player to check
     * @return {@code true} if the player is a spectator, {@code false} otherwise.
     */
    boolean isSpectator(Player player);
}
