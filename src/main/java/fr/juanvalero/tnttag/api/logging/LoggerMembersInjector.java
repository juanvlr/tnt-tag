package fr.juanvalero.tnttag.api.logging;

import com.google.inject.MembersInjector;
import org.slf4j.Logger;

import java.lang.reflect.Field;

public class LoggerMembersInjector<T> implements MembersInjector<T> {

    private final Field field;
    private final Logger logger;

    public LoggerMembersInjector(Field field, Logger logger) {
        this.field = field;
        this.field.setAccessible(true);
        this.logger = logger;
    }

    @Override
    public void injectMembers(T instance) {
        try {
            this.field.set(instance, this.logger);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
