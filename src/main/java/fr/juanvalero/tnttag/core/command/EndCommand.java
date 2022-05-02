package fr.juanvalero.tnttag.core.command;

import cloud.commandframework.annotations.CommandDescription;
import cloud.commandframework.annotations.CommandMethod;
import fr.juanvalero.tnttag.api.game.Game;
import org.bukkit.command.CommandSender;

import javax.inject.Inject;

// TODO Check permission
public class EndCommand {

    private final Game game;

    @Inject
    public EndCommand(Game game) {
        this.game = game;
    }

    @CommandMethod("end")
    @CommandDescription("Stoppe la partie")
    public void end(CommandSender sender) {
        this.game.stop();
    }
}
