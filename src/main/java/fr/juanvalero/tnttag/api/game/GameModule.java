package fr.juanvalero.tnttag.api.game;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import fr.juanvalero.tnttag.api.game.event.EventService;
import fr.juanvalero.tnttag.api.game.event.EventServiceImpl;
import fr.juanvalero.tnttag.api.game.explosion.ExplosionRunnableFactory;
import fr.juanvalero.tnttag.api.game.item.ItemService;
import fr.juanvalero.tnttag.api.game.item.ItemServiceImpl;
import fr.juanvalero.tnttag.api.game.start.AutoStartRunnableFactory;

public class GameModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new FactoryModuleBuilder()
                .build(ExplosionRunnableFactory.class));

        install(new FactoryModuleBuilder()
                .build(AutoStartRunnableFactory.class));

        bind(Game.class).to(GameImpl.class).asEagerSingleton();

        bind(EventService.class).to(EventServiceImpl.class);
        bind(ItemService.class).to(ItemServiceImpl.class);
    }
}
