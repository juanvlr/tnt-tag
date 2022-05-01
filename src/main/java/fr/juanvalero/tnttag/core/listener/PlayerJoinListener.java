package fr.juanvalero.tnttag.core.listener;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.sendMessage(
                Component
                        .text("Hello ")
                        .append(Component.text(player.getName()).color(NamedTextColor.RED))
                        .append(Component.text(" !"))
        );
    }
}
