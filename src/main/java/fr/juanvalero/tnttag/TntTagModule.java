package fr.juanvalero.tnttag;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import fr.juanvalero.tnttag.api.APIModule;
import fr.juanvalero.tnttag.command.CommandModule;
import fr.juanvalero.tnttag.event.EventModule;
import fr.juanvalero.tnttag.item.ItemModule;
import fr.juanvalero.tnttag.listener.ListenerModule;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.slf4j.Logger;

public class TntTagModule extends AbstractModule {

    private final Plugin plugin;

    public TntTagModule(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    protected void configure() {
        install(new APIModule());
        install(new CommandModule());
        install(new EventModule());
        install(new ItemModule());
        install(new ListenerModule());
    }

    @Provides
    public Plugin providePlugin() {
        return this.plugin;
    }

    @Provides
    public Logger provideLogger() {
        return this.plugin.getSLF4JLogger();
    }

    @Provides
    public FileConfiguration provideConfiguration() {
        return this.plugin.getConfig();
    }

    @Provides
    public PluginManager providePluginManager() {
        return Bukkit.getPluginManager();
    }
}
