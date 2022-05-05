package fr.juanvalero.tnttag.api.world;

import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.World;

public class WorldServiceImpl implements WorldService {

    private static final long DAY_TIME = 1000L;
    private static final long NIGHT_TIME = 13000L;

    private final World world;

    public WorldServiceImpl() {
        this.world = Bukkit.getWorld("world");
    }

    @Override
    public void init() {
        if (this.world == null) {
            throw new RuntimeException("The default world must be named world");
        }

        this.world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
        this.world.setGameRule(GameRule.DO_WEATHER_CYCLE, false);

        this.setSun();
        this.setDay();
    }

    @Override
    public void setDay() {
        this.world.setTime(DAY_TIME);
    }

    @Override
    public void setNight() {
        this.world.setTime(NIGHT_TIME);
    }

    @Override
    public void setRain() {
        this.world.setStorm(true);
    }

    @Override
    public void setSun() {
        this.world.setStorm(false);
    }
}
