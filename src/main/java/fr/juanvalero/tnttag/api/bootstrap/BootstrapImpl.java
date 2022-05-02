package fr.juanvalero.tnttag.api.bootstrap;

import fr.juanvalero.tnttag.api.command.register.CommandRegister;
import fr.juanvalero.tnttag.api.listener.ListenerRegister;
import fr.juanvalero.tnttag.api.logging.inject.InjectLogger;
import org.bukkit.plugin.Plugin;
import org.slf4j.Logger;

import javax.inject.Inject;

public class BootstrapImpl implements Boostrap {

    @InjectLogger
    private Logger logger;

    private final Plugin plugin;
    private final CommandRegister commandRegister;
    private final ListenerRegister listenerRegister;

    @Inject
    public BootstrapImpl(Plugin plugin, CommandRegister commandRegister, ListenerRegister listenerRegister) {
        this.plugin = plugin;
        this.commandRegister = commandRegister;
        this.listenerRegister = listenerRegister;
    }

    @Override
    public void bootstrap() {
        this.plugin.saveConfig();

        this.commandRegister.registerCommands();
        this.listenerRegister.registerListeners();

        this.logger.info("TntTag enabled successfully");
    }
}
