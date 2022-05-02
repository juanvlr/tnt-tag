package fr.juanvalero.tnttag.api.configuration.inject;

import com.google.inject.TypeLiteral;
import com.google.inject.spi.TypeEncounter;
import com.google.inject.spi.TypeListener;
import fr.juanvalero.tnttag.api.configuration.Configuration;

import javax.inject.Inject;
import java.lang.reflect.Field;

public class ConfigurationTypeListener implements TypeListener {

    private final Configuration configuration;

    @Inject
    public ConfigurationTypeListener(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public <I> void hear(TypeLiteral<I> type, TypeEncounter<I> encounter) {
        Class<?> clazz = type.getRawType();

        while (clazz != null) {
            for (Field field : clazz.getDeclaredFields()) {
                if (field.getType() == Configuration.class && field.isAnnotationPresent(InjectConfiguration.class)) {
                    encounter.register(new ConfigurationMembersInjector<>(field, this.configuration));
                }
            }

            clazz = clazz.getSuperclass();
        }
    }
}
