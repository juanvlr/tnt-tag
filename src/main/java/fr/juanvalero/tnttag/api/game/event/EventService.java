package fr.juanvalero.tnttag.api.game.event;

/**
 * Event utilities.
 *
 * @see Event
 */
public interface EventService {

    /**
     * Returns a random event among the registered events.
     *
     * @return A random event among the registered events.
     */
    Event getRandomEvent();
}
