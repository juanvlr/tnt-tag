/*
 * Copyright (c) 2022 - Juan Valero
 */

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
                this.game.getAlivePlayers().forEach(player -> player.teleport(startLocation))
        );
    }

    @Override
    public String getName() {
        return "Rassemblement";
    }

    @Override
    public int getDuration() {
        return 0;
    }
}
