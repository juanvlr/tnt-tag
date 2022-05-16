package fr.juanvalero.tnttag.bootstrap;

import com.google.inject.AbstractModule;

public class BootstrapModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(Boostrap.class).to(BootstrapImpl.class);
    }
}
