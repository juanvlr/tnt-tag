package fr.juanvalero.tnttag.api.game;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import fr.juanvalero.tnttag.api.game.start.AutoStartRunnable;
import fr.juanvalero.tnttag.api.game.start.AutoStartRunnableFactory;
import org.bukkit.scheduler.BukkitRunnable;

public class GameModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new FactoryModuleBuilder()
                .implement(BukkitRunnable.class, AutoStartRunnable.class)
                .build(AutoStartRunnableFactory.class));

        bind(Game.class).to(GameImpl.class).asEagerSingleton();
    }
}
