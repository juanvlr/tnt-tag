package fr.juanvalero.tnttag.core.listener.logout;

import fr.juanvalero.tnttag.api.game.Game;
import org.bukkit.entity.Player;
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
        Player player = event.getPlayer();

        this.game.removePlayer(player);

        event.quitMessage(null);
    }


}
