package fr.juanvalero.tnttag.api.world;

import fr.juanvalero.tnttag.api.configuration.Configuration;
import fr.juanvalero.tnttag.api.configuration.inject.InjectConfiguration;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.List;

public class WorldServiceImpl implements WorldService {

    private static final long DAY_TIME = 1000L;
    private static final long NIGHT_TIME = 13000L;

    @InjectConfiguration
    private Configuration configuration;

    @Override
    public void init() {
        this.getWorld().setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
        this.getWorld().setGameRule(GameRule.DO_WEATHER_CYCLE, false);

        this.setSun();
        this.setDay();
    }

    @Override
    public void setDay() {
        this.getWorld().setTime(DAY_TIME);
    }

    @Override
    public void setNight() {
        this.getWorld().setTime(NIGHT_TIME);
    }

    @Override
    public void setRain() {
        this.getWorld().setStorm(true);
    }

    @Override
    public void setSun() {
        this.getWorld().setStorm(false);
    }

    @Override
    public List<Block> getNearbyBlocks(Location location, int radius) {
        List<Block> blocks = new ArrayList<>();
        for (int x = location.getBlockX() - radius; x <= location.getBlockX() + radius; x++) {
            for (int z = location.getBlockZ() - radius; z <= location.getBlockZ() + radius; z++) {
                blocks.add(location.getWorld().getBlockAt(x, location.getBlockY(), z));
            }
        }

        return blocks;
    }

    private World getWorld() {
        return Bukkit.getWorld(this.configuration.getWorld());
    }
}
