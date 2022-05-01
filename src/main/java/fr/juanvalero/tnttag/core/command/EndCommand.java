package fr.juanvalero.tnttag.core.command;

import cloud.commandframework.annotations.CommandMethod;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;

public class EndCommand {

    @CommandMethod("end")
    public void end(CommandSender sender) {
        sender.sendMessage(Component.text("TODO"));
    }
}
