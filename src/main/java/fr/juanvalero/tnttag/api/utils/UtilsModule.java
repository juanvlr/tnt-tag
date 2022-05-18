/*
 * Copyright (c) 2022 - Juan Valero
 */

package fr.juanvalero.tnttag.api.utils;

import com.google.inject.AbstractModule;
import fr.juanvalero.tnttag.api.utils.component.ComponentSerializer;
import fr.juanvalero.tnttag.api.utils.component.ComponentSerializerImpl;

public class UtilsModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(ComponentSerializer.class).to(ComponentSerializerImpl.class);
    }
}
