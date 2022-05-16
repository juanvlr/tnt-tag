package fr.juanvalero.tnttag.api;

import com.google.inject.AbstractModule;
import fr.juanvalero.tnttag.api.command.CommandModule;
import fr.juanvalero.tnttag.api.configuration.ConfigurationModule;
import fr.juanvalero.tnttag.api.game.GameModule;
import fr.juanvalero.tnttag.api.listener.ListenerModule;
import fr.juanvalero.tnttag.api.scoreboard.ScoreboardModule;
import fr.juanvalero.tnttag.api.utils.UtilsModule;
import fr.juanvalero.tnttag.api.world.WorldModule;
import fr.juanvalero.tnttag.bootstrap.BootstrapModule;

public class APIModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new BootstrapModule());
        install(new CommandModule());
        install(new ConfigurationModule());
        install(new GameModule());
        install(new ListenerModule());
        install(new ScoreboardModule());
        install(new UtilsModule());
        install(new WorldModule());
    }
}
