package fr.juanvalero.tnttag.core.event;

import fr.juanvalero.tnttag.api.configuration.Configuration;
import fr.juanvalero.tnttag.api.configuration.inject.InjectConfiguration;
import fr.juanvalero.tnttag.api.game.Game;
import fr.juanvalero.tnttag.api.game.event.Event;

import javax.inject.Inject;

public class GatheringEvent extends Event {

    private final Game game;
    @InjectConfiguration
    private Configuration configuration;

    @Inject
    public GatheringEvent(Game game) {
        this.game = game;
    }

    @Override
    public void run() {
        this.game.getPlayers().forEach(player -> player.teleport(this.configuration.getStartLocation()));
    }
}
