package fr.juanvalero.tnttag.listener.player.login;

import fr.juanvalero.tnttag.api.game.Game;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import javax.inject.Inject;

public class PlayerJoinListener implements Listener {

    private final Game game;

    @Inject
    public PlayerJoinListener(Game game) {
        this.game = game;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        this.game.join(event.getPlayer());

        event.joinMessage(null);
    }
}
