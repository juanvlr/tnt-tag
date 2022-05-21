/*
 * Copyright (c) 2022 - Juan Valero
 */

package fr.juanvalero.tnttag.command;

import cloud.commandframework.annotations.CommandDescription;
import cloud.commandframework.annotations.CommandMethod;
import cloud.commandframework.annotations.CommandPermission;
import fr.juanvalero.tnttag.api.game.Game;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import javax.inject.Inject;

public class EndGameCommand {

    private final Game game;

    @Inject
    public EndGameCommand(Game game) {
        this.game = game;
    }

    @CommandMethod("endgame")
    @CommandPermission("tnttag.command.endgame")
    @CommandDescription("Stopper la partie")
    public void endgame(CommandSender sender) {
        if (!this.game.hasStarted()) {
            return;
        }

        Bukkit.broadcast(Component.text("La partie a était terminée par un Admin"));

        this.game.stop();
    }
}
