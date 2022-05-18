/*
 * Copyright (c) 2022 - Juan Valero
 */

package fr.juanvalero.tnttag.api.scoreboard;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import org.bukkit.Bukkit;
import org.bukkit.scoreboard.ScoreboardManager;

public class ScoreboardModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new FactoryModuleBuilder()
                .implement(Scoreboard.class, ScoreboardImpl.class)
                .build(ScoreboardFactory.class));

        bind(ScoreboardService.class).to(ScoreboardServiceImpl.class).asEagerSingleton();
    }

    @Provides
    public ScoreboardManager provideScoreboardManager() {
        return Bukkit.getScoreboardManager();
    }
}
