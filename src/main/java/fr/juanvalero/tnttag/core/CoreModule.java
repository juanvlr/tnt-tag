package fr.juanvalero.tnttag.core;

import com.google.inject.AbstractModule;
import fr.juanvalero.tnttag.core.command.CommandModule;
import fr.juanvalero.tnttag.core.listener.ListenerModule;

public class CoreModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new CommandModule());
        install(new ListenerModule());
    }
}
