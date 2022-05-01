package fr.juanvalero.tnttag.api.bootstrap;

import fr.juanvalero.tnttag.api.logging.InjectLogger;
import org.slf4j.Logger;

public class Bootstrap {

    @InjectLogger
    private Logger logger;

    public void bootstrap() {
        this.logger.info("TntTag enabled successfully");
    }
}
