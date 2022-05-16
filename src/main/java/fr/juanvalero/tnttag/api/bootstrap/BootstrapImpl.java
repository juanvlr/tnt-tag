package fr.juanvalero.tnttag.api.bootstrap;

import fr.juanvalero.tnttag.api.command.register.CommandRegister;
import fr.juanvalero.tnttag.api.listener.ListenerRegister;
import fr.juanvalero.tnttag.api.world.WorldService;
import org.bukkit.plugin.Plugin;

import javax.inject.Inject;

/**
 * Default {@link Boostrap} implementation.
 */
public class BootstrapImpl implements Boostrap {

    private final Plugin plugin;
    private final CommandRegister commandRegister;
    private final ListenerRegister listenerRegister;
    private final WorldService worldService;

    @Inject
    public BootstrapImpl(Plugin plugin,
                         CommandRegister commandRegister,
                         ListenerRegister listenerRegister,
                         WorldService worldService) {
        this.plugin = plugin;
        this.commandRegister = commandRegister;
        this.listenerRegister = listenerRegister;
        this.worldService = worldService;
    }

    @Override
    public void bootstrap() {
        this.plugin.saveConfig(); // TODO Check if it results in a bug when no configuration is provided

        this.commandRegister.registerCommands();
        this.listenerRegister.registerListeners();

        this.worldService.init();
    }
}
