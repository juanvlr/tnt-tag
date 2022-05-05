package fr.juanvalero.tnttag.core.listener.environment;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

public class WeatherListener implements Listener {

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent event) {
        if (event.getCause() != WeatherChangeEvent.Cause.PLUGIN) {
            event.setCancelled(true);
        }
    }
}
