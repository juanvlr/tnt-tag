/*
 * Copyright (c) 2022 - Juan Valero
 */

package fr.juanvalero.tnttag.listener.player;

import fr.juanvalero.tnttag.api.game.Game;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import javax.inject.Inject;

public class PlayerMoveListener implements Listener {

    private final Game game;

    @Inject
    public PlayerMoveListener(Game game) {
        this.game = game;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (!this.game.hasStarted()) {
            return;
        }

        Player player = event.getPlayer();

        if (this.game.isSpectator(player) || this.game.isTagged(player)) {
            return;
        }

        Location location = event.getTo();

        this.game.getTaggedPlayers().forEach(taggedPlayer ->
                taggedPlayer.setCompassTarget(
                        this.game.getPlayers()
                                .filter(p -> !this.game.isTagged(p) && !p.equals(player))
                                .getClosestLocation(taggedPlayer, location)
                )
        );
    }
}
