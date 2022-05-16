package fr.juanvalero.tnttag.bootstrap;

import fr.juanvalero.tnttag.api.command.register.CommandRegister;
import fr.juanvalero.tnttag.api.listener.ListenerRegister;
import fr.juanvalero.tnttag.api.world.WorldService;

import javax.inject.Inject;

/**
 * Default {@link Boostrap} implementation.
 */
public class BootstrapImpl implements Boostrap {

    private final CommandRegister commandRegister;
    private final ListenerRegister listenerRegister;
    private final WorldService worldService;

    @Inject
    public BootstrapImpl(CommandRegister commandRegister,
                         ListenerRegister listenerRegister,
                         WorldService worldService) {
        this.commandRegister = commandRegister;
        this.listenerRegister = listenerRegister;
        this.worldService = worldService;
    }

    @Override
    public void bootstrap() {
        this.commandRegister.registerCommands();
        this.listenerRegister.registerListeners();

        this.worldService.init();
    }
}
