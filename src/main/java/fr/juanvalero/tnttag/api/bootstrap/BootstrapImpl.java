package fr.juanvalero.tnttag.api.bootstrap;

import fr.juanvalero.tnttag.api.command.register.CommandRegister;
import fr.juanvalero.tnttag.api.listener.ListenerRegister;
import fr.juanvalero.tnttag.api.logging.inject.InjectLogger;
import fr.juanvalero.tnttag.api.world.WorldService;
import org.bukkit.plugin.Plugin;
import org.slf4j.Logger;

import javax.inject.Inject;

public class BootstrapImpl implements Boostrap {

    @InjectLogger
    private Logger logger;

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

        this.logger.info("TntTag enabled successfully");
    }
}
