package fr.juanvalero.tnttag.event;

import fr.juanvalero.tnttag.api.configuration.Configuration;
import fr.juanvalero.tnttag.api.game.Game;
import fr.juanvalero.tnttag.api.game.event.Event;

import javax.inject.Inject;

public class GatheringEvent extends Event {

    @Inject
    private Configuration configuration;

    private final Game game;

    @Inject
    public GatheringEvent(Game game) {
        this.game = game;
    }

    @Override
    public void run() {
        this.configuration.getStartLocation().ifPresent(startLocation ->
                this.game.getPlayers().forEach(player -> player.teleport(startLocation))
        );
    }
}
