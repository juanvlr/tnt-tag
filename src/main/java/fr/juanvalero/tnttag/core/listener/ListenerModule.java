package fr.juanvalero.tnttag.core.listener;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import fr.juanvalero.tnttag.core.listener.environment.TimeSkipListener;
import fr.juanvalero.tnttag.core.listener.environment.WeatherListener;
import fr.juanvalero.tnttag.core.listener.login.PlayerJoinListener;
import fr.juanvalero.tnttag.core.listener.login.PlayerLoginListener;
import fr.juanvalero.tnttag.core.listener.logout.PlayerQuitListener;
import org.bukkit.event.Listener;

public class ListenerModule extends AbstractModule {

    @Override
    protected void configure() {
        Multibinder<Listener> binder = Multibinder.newSetBinder(binder(), Listener.class);
        binder.addBinding().to(TimeSkipListener.class);
        binder.addBinding().to(WeatherListener.class);

        binder.addBinding().to(PlayerJoinListener.class);
        binder.addBinding().to(PlayerLoginListener.class);

        binder.addBinding().to(PlayerQuitListener.class);

        binder.addBinding().to(EntityDamageListener.class);
        binder.addBinding().to(FoodLevelChangeListener.class);
    }
}
