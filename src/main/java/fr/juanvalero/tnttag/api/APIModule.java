package fr.juanvalero.tnttag.api;

import com.google.inject.AbstractModule;
import fr.juanvalero.tnttag.api.logging.LoggingModule;
import org.slf4j.Logger;

public class APIModule extends AbstractModule {

    private final Logger logger;

    public APIModule(Logger logger) {
        this.logger = logger;
    }

    @Override
    protected void configure() {
        install(new LoggingModule(this.logger));
    }
}
