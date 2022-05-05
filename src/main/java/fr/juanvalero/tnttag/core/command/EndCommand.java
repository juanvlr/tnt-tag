package fr.juanvalero.tnttag.core.command;

import cloud.commandframework.annotations.CommandDescription;
import cloud.commandframework.annotations.CommandMethod;
import cloud.commandframework.annotations.CommandPermission;
import fr.juanvalero.tnttag.api.game.Game;
import org.bukkit.command.CommandSender;

import javax.inject.Inject;

public class EndCommand {

    private final Game game;

    @Inject
    public EndCommand(Game game) {
        this.game = game;
    }

    @CommandMethod("end")
    @CommandPermission("tnttag.command.end")
    @CommandDescription("Stoppe la partie")
    public void end(CommandSender sender) {
        this.game.stop();
    }
}
