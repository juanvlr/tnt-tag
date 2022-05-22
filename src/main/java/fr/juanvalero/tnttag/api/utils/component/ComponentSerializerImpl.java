/*
 * Copyright (c) 2022 - Juan Valero
 */

package fr.juanvalero.tnttag.api.utils.component;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

public class ComponentSerializerImpl implements ComponentSerializer {

    private static final LegacyComponentSerializer SERIALIZER_INSTANCE = LegacyComponentSerializer.legacySection();

    @Override
    public String serialize(Component component) {
        return SERIALIZER_INSTANCE.serialize(component);
    }
}
