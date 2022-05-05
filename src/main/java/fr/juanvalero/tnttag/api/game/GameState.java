package fr.juanvalero.tnttag.api.game;

public enum GameState {

    /**
     * Waiting for players or starting.
     */
    WAITING,
    /**
     * Game is starting, no matter the amount of players
     */
    FAST_STARTING,
    /**
     * Game is starting.
     */
    REGULAR_STARTING,
    /**
     * Game has started.
     */
    IN_GAME,
    /**
     * Game is stopping.
     */
    STOPPING
}
