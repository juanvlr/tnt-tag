/*
 * Copyright (c) 2022 - Juan Valero
 */

package fr.juanvalero.tnttag.listener.player.logout;

import fr.juanvalero.tnttag.api.game.Game;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import javax.inject.Inject;

public class PlayerQuitListener implements Listener {

    private final Game game;

    @Inject
    public PlayerQuitListener(Game game) {
        this.game = game;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        this.game.leave(event.getPlayer());

        event.quitMessage(null);
    }
}
