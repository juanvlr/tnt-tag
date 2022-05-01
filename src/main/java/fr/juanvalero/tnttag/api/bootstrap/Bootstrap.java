package fr.juanvalero.tnttag.api.bootstrap;

import fr.juanvalero.tnttag.api.listener.ListenerRegister;
import fr.juanvalero.tnttag.api.logging.InjectLogger;
import org.slf4j.Logger;

import javax.inject.Inject;

public class Bootstrap {

    @InjectLogger
    private Logger logger;

    private final ListenerRegister listenerRegister;

    @Inject
    public Bootstrap(ListenerRegister listenerRegister) {
        this.listenerRegister = listenerRegister;
    }

    public void bootstrap() {
        this.listenerRegister.registerListeners();

        this.logger.info("TntTag enabled successfully");
    }
}
