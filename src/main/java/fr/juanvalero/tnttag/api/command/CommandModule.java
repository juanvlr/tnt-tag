package fr.juanvalero.tnttag.api.command;

import cloud.commandframework.CommandManager;
import cloud.commandframework.annotations.AnnotationParser;
import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.throwingproviders.ThrowingProviderBinder;
import fr.juanvalero.tnttag.api.command.annotation.AnnotationParserProvider;
import fr.juanvalero.tnttag.api.command.annotation.AnnotationParserProviderImpl;
import fr.juanvalero.tnttag.api.command.manager.CommandManagerProvider;
import fr.juanvalero.tnttag.api.command.manager.CommandManagerProviderImpl;
import fr.juanvalero.tnttag.api.command.register.CommandRegister;
import fr.juanvalero.tnttag.api.command.register.CommandRegisterImpl;
import org.bukkit.command.CommandSender;

public class CommandModule extends AbstractModule {

    @Override
    protected void configure() {
        ThrowingProviderBinder.create(binder())
                .bind(CommandManagerProvider.class, new TypeLiteral<CommandManager<CommandSender>>() {
                })
                .to(CommandManagerProviderImpl.class);

        ThrowingProviderBinder.create(binder())
                .bind(AnnotationParserProvider.class, new TypeLiteral<AnnotationParser<CommandSender>>() {
                })
                .to(AnnotationParserProviderImpl.class);

        bind(CommandRegister.class).to(CommandRegisterImpl.class);
    }
}
