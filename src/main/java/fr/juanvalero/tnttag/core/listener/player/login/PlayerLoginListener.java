package fr.juanvalero.tnttag.core.listener.player.login;

import fr.juanvalero.tnttag.api.game.Game;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import javax.inject.Inject;

public class PlayerLoginListener implements Listener {

    private final Game game;

    @Inject
    public PlayerLoginListener(Game game) {
        this.game = game;
    }

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) {
        if (this.game.isClosed()) {
            event.disallow(PlayerLoginEvent.Result.KICK_OTHER,
                    Component
                            .text("Vous ne pouvez pas rejoindre la partie car elle est pleine ou déjà en cours")
                            .color(NamedTextColor.RED)
            );
        }
    }
}
