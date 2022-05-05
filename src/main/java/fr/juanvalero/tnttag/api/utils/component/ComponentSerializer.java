package fr.juanvalero.tnttag.api.utils.component;

import net.kyori.adventure.text.Component;

public interface ComponentSerializer {

    /**
     * Serializes the specified component.
     *
     * @param component The component to serialize.
     * @return The serialized component.
     */
    String serialize(Component component);
}
