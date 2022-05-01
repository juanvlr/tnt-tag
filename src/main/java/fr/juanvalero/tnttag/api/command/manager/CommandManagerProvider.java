package fr.juanvalero.tnttag.api.command.manager;

import cloud.commandframework.CommandManager;
import com.google.inject.throwingproviders.CheckedProvider;
import org.bukkit.command.CommandSender;

public interface CommandManagerProvider extends CheckedProvider<CommandManager<CommandSender>> {

}
