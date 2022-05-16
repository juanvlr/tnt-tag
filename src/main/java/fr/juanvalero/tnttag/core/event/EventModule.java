package fr.juanvalero.tnttag.core.event;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import fr.juanvalero.tnttag.api.game.event.Event;

public class EventModule extends AbstractModule {

    @Override
    protected void configure() {
        Multibinder<Event> binder = Multibinder.newSetBinder(binder(), Event.class);

        binder.addBinding().to(DrunkennessEvent.class);
        binder.addBinding().to(GatheringEvent.class);
        binder.addBinding().to(GiantPartyEvent.class);
        binder.addBinding().to(LightsOutEvent.class);
        binder.addBinding().to(MoonEvent.class);
    }
}
