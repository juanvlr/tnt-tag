package fr.juanvalero.tnttag.api.configuration;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import javax.inject.Inject;

public class ConfigurationImpl implements Configuration {

    private final FileConfiguration configuration;

    @Inject
    public ConfigurationImpl(FileConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public Location getLobbyLocation() {
        return this.configuration.getLocation("lobby-location");
    }

    @Override
    public Location getStartLocation() {
        return this.configuration.getLocation("start-location");
    }
}
