/*
 * Copyright (c) 2022 - Juan Valero
 */

package fr.juanvalero.tnttag.api.world;

import com.google.inject.AbstractModule;

public class WorldModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(WorldService.class).to(WorldServiceImpl.class);
    }
}
