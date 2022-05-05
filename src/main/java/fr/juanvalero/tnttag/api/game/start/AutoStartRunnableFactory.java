package fr.juanvalero.tnttag.api.game.start;

import org.bukkit.scheduler.BukkitRunnable;

public interface AutoStartRunnableFactory {

    BukkitRunnable createAutoStartRunnable(int time);
}
