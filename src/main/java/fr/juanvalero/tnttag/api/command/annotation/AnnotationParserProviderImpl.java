/*
 * Copyright (c) 2022 - Juan Valero
 */

package fr.juanvalero.tnttag.api.command.annotation;

import cloud.commandframework.annotations.AnnotationParser;
import cloud.commandframework.meta.SimpleCommandMeta;
import fr.juanvalero.tnttag.api.command.manager.CommandManagerProvider;
import org.bukkit.command.CommandSender;

import javax.inject.Inject;

/**
 * Default {@link AnnotationParserProvider} implementation.
 */
public class AnnotationParserProviderImpl implements AnnotationParserProvider {

    private final CommandManagerProvider commandManagerProvider;

    @Inject
    public AnnotationParserProviderImpl(CommandManagerProvider commandManagerProvider) {
        this.commandManagerProvider = commandManagerProvider;
    }

    @Override
    public AnnotationParser<CommandSender> get() throws Exception {
        return new AnnotationParser<>(
                this.commandManagerProvider.get(),
                CommandSender.class,
                parameters -> SimpleCommandMeta.empty()
        );
    }
}
