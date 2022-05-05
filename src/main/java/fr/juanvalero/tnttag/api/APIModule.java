package fr.juanvalero.tnttag.api;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import fr.juanvalero.tnttag.api.bootstrap.BootstrapModule;
import fr.juanvalero.tnttag.api.command.CommandModule;
import fr.juanvalero.tnttag.api.configuration.ConfigurationModule;
import fr.juanvalero.tnttag.api.game.GameModule;
import fr.juanvalero.tnttag.api.listener.ListenerModule;
import fr.juanvalero.tnttag.api.logging.LoggingModule;
import fr.juanvalero.tnttag.api.scoreboard.ScoreboardModule;
import fr.juanvalero.tnttag.api.utils.UtilsModule;
import fr.juanvalero.tnttag.api.world.WorldModule;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.slf4j.Logger;

public class APIModule extends AbstractModule {

    private final Plugin plugin;
    private final Logger logger;
    private final FileConfiguration configuration;

    public APIModule(Plugin plugin, Logger logger, FileConfiguration configuration) {
        this.plugin = plugin;
        this.logger = logger;
        this.configuration = configuration;
    }

    @Override
    protected void configure() {
        install(new BootstrapModule());
        install(new CommandModule());
        install(new ConfigurationModule(this.configuration));
        install(new GameModule());
        install(new ListenerModule());
        install(new LoggingModule(this.logger));
        install(new ScoreboardModule());
        install(new UtilsModule());
        install(new WorldModule());
    }

    @Provides
    public Plugin providePlugin() {
        return this.plugin;
    }

    @Provides
    public PluginManager providePluginManager() {
        return Bukkit.getPluginManager();
    }
}
