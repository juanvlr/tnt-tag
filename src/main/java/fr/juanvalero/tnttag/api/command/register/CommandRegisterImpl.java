package fr.juanvalero.tnttag.api.command.register;

import cloud.commandframework.annotations.AnnotationParser;
import fr.juanvalero.tnttag.api.command.annotation.AnnotationParserProvider;
import fr.juanvalero.tnttag.api.logging.inject.InjectLogger;
import org.bukkit.command.CommandSender;
import org.slf4j.Logger;

import javax.inject.Inject;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.Set;

/**
 * Default {@link CommandRegister} implementation.
 */
public class CommandRegisterImpl implements CommandRegister {

    private final AnnotationParserProvider annotationParserProvider;
    private final Collection<Object> commands;
    @InjectLogger
    private Logger logger;

    @Inject
    public CommandRegisterImpl(AnnotationParserProvider annotationParserProvider, Set<Object> commands) {
        this.annotationParserProvider = annotationParserProvider;
        this.commands = commands;
    }

    @Override
    public void registerCommands() {
        try {
            AnnotationParser<CommandSender> annotationParser = annotationParserProvider.get();

            this.commands.forEach(annotationParser::parse);

            this.logger.info(
                    MessageFormat.format(
                            "{0,choice,0#no command|1#1 command|1<{0,number,integer} commands} registered",
                            this.commands.size()
                    )
            );
        } catch (Exception e) {
            this.logger.error("An error occurred while registering the commands. Please refer to the following exception for further details.");

            e.printStackTrace();
        }
    }
}
