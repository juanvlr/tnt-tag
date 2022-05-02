package fr.juanvalero.tnttag.core.listener.login;

import fr.juanvalero.tnttag.api.game.Game;
import org.bukkit.entity.Player;
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
        Player player = event.getPlayer();

        this.game.addPlayer(player);

        event.joinMessage(null);
    }
}
