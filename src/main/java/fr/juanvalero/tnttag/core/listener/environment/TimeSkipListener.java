package fr.juanvalero.tnttag.core.listener.environment;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.TimeSkipEvent;

public class TimeSkipListener implements Listener {

    @EventHandler
    public void onTimeSkip(TimeSkipEvent event) {
        if (event.getSkipReason() != TimeSkipEvent.SkipReason.CUSTOM) {
            event.setCancelled(true);
        }
    }
}
