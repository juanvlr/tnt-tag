/*
 * Copyright (c) 2022 - Juan Valero
 */

package fr.juanvalero.tnttag.api.game.event;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Default {@link EventService} implementation.
 */
public class EventServiceImpl implements EventService {

    private final List<Event> events;

    @Inject
    public EventServiceImpl(Set<Event> events) {
        this.events = new ArrayList<>(events);
    }

    @Override
    public Event getRandomEvent() {
        return this.events.get(new Random().nextInt(this.events.size()));
    }
}
