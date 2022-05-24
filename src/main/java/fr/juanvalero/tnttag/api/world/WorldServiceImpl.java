/*
 * Copyright (c) 2022 - Juan Valero
 */

package fr.juanvalero.tnttag.api.world;

import fr.juanvalero.tnttag.api.configuration.Configuration;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Firework;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Default {@link WorldService} implementation.
 */
public class WorldServiceImpl implements WorldService {

    @Inject
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
        this.getWorld().setTime(GameTime.DAY.getTicks());
    }

    @Override
    public void setNight() {
        this.getWorld().setTime(GameTime.NIGHT.getTicks());
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
    public List<Block> getNearbyBlocks(Location center, int radius) {
        List<Block> blocks = new ArrayList<>();
        for (int x = center.getBlockX() - radius; x <= center.getBlockX() + radius; x++) {
            for (int y = center.getBlockY(); y <= center.getBlockY() + radius; y++) {
                for (int z = center.getBlockZ() - radius; z <= center.getBlockZ() + radius; z++) {
                    blocks.add(center.getWorld().getBlockAt(x, y, z));
                }
            }
        }

        return blocks;
    }

    @Override
    public void spawnFirework(Location location) {
        this.getWorld().spawn(location, Firework.class);
    }

    /**
     * Returns the {@link World} in which the game is played.
     *
     * @return The game {@link World}
     */
    private World getWorld() {
        return Bukkit.getWorld(this.configuration.getWorld());
    }
}
