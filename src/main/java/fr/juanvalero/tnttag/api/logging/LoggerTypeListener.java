package fr.juanvalero.tnttag.api.logging;

import com.google.inject.TypeLiteral;
import com.google.inject.spi.TypeEncounter;
import com.google.inject.spi.TypeListener;
import org.slf4j.Logger;

import javax.inject.Inject;
import java.lang.reflect.Field;

public class LoggerTypeListener implements TypeListener {

    private final Logger logger;

    @Inject
    public LoggerTypeListener(Logger logger) {
        this.logger = logger;
    }

    @Override
    public <I> void hear(TypeLiteral<I> type, TypeEncounter<I> encounter) {
        Class<?> clazz = type.getRawType();

        while (clazz != null) {
            for (Field field : clazz.getDeclaredFields()) {
                if (field.getType() == Logger.class && field.isAnnotationPresent(InjectLogger.class)) {
                    encounter.register(new LoggerMembersInjector<>(field, this.logger));
                }
            }

            clazz = clazz.getSuperclass();
        }
    }
}
