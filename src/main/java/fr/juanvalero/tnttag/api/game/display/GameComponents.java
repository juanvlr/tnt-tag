/*
 * Copyright (c) 2022 - Juan Valero
 */

package fr.juanvalero.tnttag.api.game.display;

import fr.juanvalero.tnttag.api.game.GameConstants;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.title.Title;
import org.bukkit.entity.Player;

/**
 * Game {@link Component}s list.
 */
public class GameComponents {

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

    public static Title getStartMessage() {
        return Title.title(Component.text("GO !", NamedTextColor.GREEN), Component.empty());
    }

    public static Component getDisconnectionDeathMessage(Player leaver) {
        return Component.text(leaver.getName(), NamedTextColor.GREEN)
                .append(Component.text(" s'est déconnecté !"));
    }

    public static Title getTagMessage() {
        return Title.title(
                Component.text("Tu es tagé !", NamedTextColor.RED),
                Component.empty()
        );
    }

    public static Component getExplosionMessage(Player victim) {
        return Component.text(victim.getName(), NamedTextColor.RED).append(Component.text(" a explosé !"));
    }

    public static Component getAlivePlayerAmountMessage(int amount) {
        return Component.text(amount, NamedTextColor.GREEN)
                .append(Component.text(" joueurs en vie"));
    }

    public static Component getWinMessage(Player winner) {
        return Component.text(winner.getName(), NamedTextColor.GOLD, TextDecoration.BOLD)
                .append(Component.text(" a gagné !"));
    }
}
