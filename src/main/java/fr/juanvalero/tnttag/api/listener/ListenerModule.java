package fr.juanvalero.tnttag.api.listener;

import com.google.inject.AbstractModule;

public class ListenerModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(ListenerRegister.class).to(ListenerRegisterImpl.class);
    }
}
