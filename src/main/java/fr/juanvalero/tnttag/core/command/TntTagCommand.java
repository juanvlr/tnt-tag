package fr.juanvalero.tnttag.core.command;

import cloud.commandframework.annotations.CommandDescription;
import cloud.commandframework.annotations.CommandMethod;
import cloud.commandframework.annotations.CommandPermission;
import fr.juanvalero.tnttag.api.game.Game;
import fr.juanvalero.tnttag.core.gui.GameConfigurationGui;
import org.bukkit.entity.Player;

import javax.inject.Inject;

public class TntTagCommand {

    private final Game game;
    private final GameConfigurationGui gameConfigurationGui;

    @Inject
    public TntTagCommand(Game game, GameConfigurationGui gameConfigurationGui) {
        this.game = game;
        this.gameConfigurationGui = gameConfigurationGui;
    }

    @CommandMethod("tnt-tag")
    @CommandPermission("tnttag.command.tnt-tag")
    @CommandDescription("Configurer la partie")
    public void tntTag(Player sender) {
        if (!this.game.isFastStarting()) {
            this.gameConfigurationGui.build().show(sender);
        }
    }
}
