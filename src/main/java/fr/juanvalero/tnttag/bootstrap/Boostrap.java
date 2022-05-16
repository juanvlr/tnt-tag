package fr.juanvalero.tnttag.bootstrap;

/**
 * Plugin entry point.
 */
public interface Boostrap {

    /**
     * Bootstraps the plugin by :
     * <ul>
     *     <li>Registering listeners</li>
     *     <li>Registering commands</li>
     *     <li>Initializing the world</li>
     * </ul>
     */
    void bootstrap();
}
