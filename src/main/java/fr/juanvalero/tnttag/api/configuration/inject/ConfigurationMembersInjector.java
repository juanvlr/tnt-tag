package fr.juanvalero.tnttag.api.configuration.inject;

import com.google.inject.MembersInjector;
import fr.juanvalero.tnttag.api.configuration.Configuration;

import java.lang.reflect.Field;

public class ConfigurationMembersInjector<T> implements MembersInjector<T> {

    private final Field field;
    private final Configuration configuration;

    public ConfigurationMembersInjector(Field field, Configuration configuration) {
        this.field = field;
        this.field.setAccessible(true);
        this.configuration = configuration;
    }

    @Override
    public void injectMembers(T instance) {
        try {
            this.field.set(instance, this.configuration);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
