/*
 * Copyright (c) 2022 - Juan Valero
 */

package fr.juanvalero.tnttag.api.listener;

import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.slf4j.Logger;

import javax.inject.Inject;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.Set;

public class ListenerRegisterImpl implements ListenerRegister {

    @Inject
    private Logger logger;

    private final Plugin plugin;
    private final PluginManager pluginManager;
    private final Collection<Listener> listeners;

    @Inject
    public ListenerRegisterImpl(Plugin plugin, PluginManager pluginManager, Set<Listener> listeners) {
        this.plugin = plugin;
        this.pluginManager = pluginManager;
        this.listeners = listeners;
    }

    @Override
    public void registerListeners() {
        this.listeners.forEach(listener -> this.pluginManager.registerEvents(listener, this.plugin));

        this.logger.info(
                MessageFormat.format(
                        "{0,choice,0#no listener|1#1 listener|1<{0,number,integer} listeners} registered",
                        this.listeners.size()
                )
        );
    }
}
