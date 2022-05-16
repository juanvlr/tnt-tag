package fr.juanvalero.tnttag.api.game.event;

/**
 * An event has a probability to occur at the end of each round.
 * Events can be enabled or disabled from the configuration gui.
 */
public abstract class Event {

    /**
     * Runs the event.
     */
    public abstract void run();
}