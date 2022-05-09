package fr.juanvalero.tnttag.api.world;

import org.bukkit.Location;
import org.bukkit.block.Block;

import java.util.List;

public interface WorldService {

    void init();

    void setDay();

    void setNight();

    void setRain();

    void setSun();

    List<Block> getNearbyBlocks(Location location, int radius);
}
