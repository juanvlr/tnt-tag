package fr.juanvalero.tnttag.core.command;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;

public class CommandModule extends AbstractModule {

    @Override
    protected void configure() {
        Multibinder<Object> binder = Multibinder.newSetBinder(binder(), Object.class);
        binder.addBinding().to(EndCommand.class);
        binder.addBinding().to(TntTagCommand.class);
    }
}
