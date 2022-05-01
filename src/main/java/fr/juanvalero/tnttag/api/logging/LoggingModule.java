package fr.juanvalero.tnttag.api.logging;

import com.google.inject.AbstractModule;
import com.google.inject.matcher.Matchers;
import fr.juanvalero.tnttag.api.logging.inject.LoggerTypeListener;
import org.slf4j.Logger;

public class LoggingModule extends AbstractModule {

    private final Logger logger;

    public LoggingModule(Logger logger) {
        this.logger = logger;
    }

    @Override
    protected void configure() {
        bindListener(Matchers.any(), new LoggerTypeListener(this.logger));
    }
}
