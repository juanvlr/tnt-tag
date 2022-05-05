package fr.juanvalero.tnttag.api.game.display;

import fr.juanvalero.tnttag.api.game.GameConstants;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.title.Title;
import org.bukkit.entity.Player;

public class GameMessages {

    private static final Component[] CREDIT_MESSAGES = new Component[]{
            Component.text("➤ Imaginé par Spik’ & Loomi’", NamedTextColor.GREEN),
            Component.text("➤ Développé par Choukas", NamedTextColor.LIGHT_PURPLE),
            Component.text("➤ Build par ???", NamedTextColor.BLUE)
    };

    public static Component getConnectionMessage(Player player, int playerCount) {
        return Component
                .empty()
                .color(NamedTextColor.GRAY)
                .append(Component.text("[TNT-TAG]", NamedTextColor.RED))
                .append(Component.text(" ["))
                .append(Component.text("✔", NamedTextColor.GREEN))
                .append(Component.text("] "))
                .append(Component.text(player.getName(), NamedTextColor.GREEN))
                .append(Component.text(" a rejoint la partie ! "))
                .append(Component.text("("))
                .append(Component.text(playerCount))
                .append(Component.text("/"))
                .append(Component.text(GameConstants.MAXIMUM_PLAYER_COUNT))
                .append(Component.text(")"));
    }

    public static Component getDisconnectionMessage(Player player, int playerCount) {
        return Component
                .empty()
                .color(NamedTextColor.GRAY)
                .append(Component.text("[TNT-TAG]", NamedTextColor.RED))
                .append(Component.text(" ["))
                .append(Component.text("✖").color(NamedTextColor.RED))
                .append(Component.text("] "))
                .append(Component.text(player.getName()).color(NamedTextColor.GREEN))
                .append(Component.text(" a quitté la partie ! "))
                .append(Component.text("("))
                .append(Component.text(playerCount))
                .append(Component.text("/"))
                .append(Component.text(GameConstants.MAXIMUM_PLAYER_COUNT))
                .append(Component.text(")"));
    }

    public static Component getGameTitle() {
        return Component.text("TNT-TAG", NamedTextColor.DARK_PURPLE);
    }

    public static Component getCreditMessage(int n) {
        return CREDIT_MESSAGES[n];
    }

    public static Title getRemainingTimeTitle(int remainingTime) {
        return Title.title(Component.text(remainingTime), Component.empty());
    }

    public static Component getRemainingTimeMessage(int remainingTime) {
        return Component
                .text("Lancement dans ")
                .append(Component.text(remainingTime).color(NamedTextColor.GREEN))
                .append(Component.text(" seconde(s)"));
    }

    public static Component getMissingPlayerCountMessage(int playerCount) {
        return Component
                .empty()
                .color(NamedTextColor.GRAY)
                .append(Component.text("En attente"))
                .append(Component.text(" "))
                .append(Component.text("("))
                .append(Component.text(playerCount))
                .append(Component.text("/"))
                .append(Component.text(GameConstants.MINIMUM_PLAYER_COUNT))
                .append(Component.text(")"));
    }
}
