/*
 * Copyright (c) 2022 - Juan Valero
 */

package fr.juanvalero.tnttag.command;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;

public class CommandModule extends AbstractModule {

    @Override
    protected void configure() {
        Multibinder<Object> binder = Multibinder.newSetBinder(binder(), Object.class);

        binder.addBinding().to(EndGameCommand.class);
        binder.addBinding().to(TntTagCommand.class);
    }
}
