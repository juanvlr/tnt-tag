package fr.juanvalero.tnttag.api.command.manager;

import cloud.commandframework.CommandManager;
import cloud.commandframework.execution.CommandExecutionCoordinator;
import cloud.commandframework.paper.PaperCommandManager;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import javax.inject.Inject;

/**
 * Default {@link CommandManagerProvider} implementation.
 */
public class CommandManagerProviderImpl implements CommandManagerProvider {

    private final Plugin plugin;

    @Inject
    public CommandManagerProviderImpl(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public CommandManager<CommandSender> get() throws Exception {
        return PaperCommandManager.createNative(this.plugin, CommandExecutionCoordinator.simpleCoordinator());
    }
}
