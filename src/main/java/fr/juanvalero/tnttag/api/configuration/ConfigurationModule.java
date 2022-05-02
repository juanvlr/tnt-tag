package fr.juanvalero.tnttag.api.configuration;

import com.google.inject.AbstractModule;
import com.google.inject.matcher.Matchers;
import fr.juanvalero.tnttag.api.configuration.inject.ConfigurationTypeListener;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigurationModule extends AbstractModule {

    private final Configuration configuration;

    public ConfigurationModule(FileConfiguration configuration) {
        this.configuration = new ConfigurationImpl(configuration);
    }

    @Override
    protected void configure() {
        bindListener(Matchers.any(), new ConfigurationTypeListener(this.configuration));
    }
}
