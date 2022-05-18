/*
 * Copyright (c) 2022 - Juan Valero
 */

package fr.juanvalero.tnttag.listener;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import fr.juanvalero.tnttag.listener.entity.EntityDamageListener;
import fr.juanvalero.tnttag.listener.entity.EntityExplodeListener;
import fr.juanvalero.tnttag.listener.environment.BlockExplodeListener;
import fr.juanvalero.tnttag.listener.environment.TimeSkipListener;
import fr.juanvalero.tnttag.listener.environment.WeatherListener;
import fr.juanvalero.tnttag.listener.player.*;
import fr.juanvalero.tnttag.listener.player.login.PlayerJoinListener;
import fr.juanvalero.tnttag.listener.player.login.PlayerLoginListener;
import fr.juanvalero.tnttag.listener.player.logout.PlayerQuitListener;
import fr.juanvalero.tnttag.listener.projectile.PlayerLaunchProjectileListener;
import fr.juanvalero.tnttag.listener.projectile.ProjectileHitListener;
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
