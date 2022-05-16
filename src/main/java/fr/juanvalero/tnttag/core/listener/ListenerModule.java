package fr.juanvalero.tnttag.core.listener;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import fr.juanvalero.tnttag.core.listener.entity.EntityDamageListener;
import fr.juanvalero.tnttag.core.listener.entity.EntityExplodeListener;
import fr.juanvalero.tnttag.core.listener.environment.BlockExplodeListener;
import fr.juanvalero.tnttag.core.listener.environment.TimeSkipListener;
import fr.juanvalero.tnttag.core.listener.environment.WeatherListener;
import fr.juanvalero.tnttag.core.listener.player.*;
import fr.juanvalero.tnttag.core.listener.player.login.PlayerJoinListener;
import fr.juanvalero.tnttag.core.listener.player.login.PlayerLoginListener;
import fr.juanvalero.tnttag.core.listener.player.logout.PlayerQuitListener;
import fr.juanvalero.tnttag.core.listener.projectile.PlayerLaunchProjectileListener;
import fr.juanvalero.tnttag.core.listener.projectile.ProjectileHitListener;
import org.bukkit.event.Listener;

public class ListenerModule extends AbstractModule {

    @Override
    protected void configure() {
        Multibinder<Listener> binder = Multibinder.newSetBinder(binder(), Listener.class);

        binder.addBinding().to(EntityDamageListener.class);
        binder.addBinding().to(EntityExplodeListener.class);

        binder.addBinding().to(BlockExplodeListener.class);
        binder.addBinding().to(TimeSkipListener.class);
        binder.addBinding().to(WeatherListener.class);

        binder.addBinding().to(PlayerJoinListener.class);
        binder.addBinding().to(PlayerLoginListener.class);
        binder.addBinding().to(PlayerQuitListener.class);
        binder.addBinding().to(FoodLevelChangeListener.class);
        binder.addBinding().to(InventoryClickListener.class);
        binder.addBinding().to(PlayerAttemptPickupItemListener.class);
        binder.addBinding().to(PlayerDropItemListener.class);
        binder.addBinding().to(PlayerInteractListener.class);
        binder.addBinding().to(PlayerMoveListener.class);

        binder.addBinding().to(ProjectileHitListener.class);
        binder.addBinding().to(PlayerLaunchProjectileListener.class);
    }
}
