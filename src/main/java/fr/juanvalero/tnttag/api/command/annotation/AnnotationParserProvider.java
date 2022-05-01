package fr.juanvalero.tnttag.api.command.annotation;

import cloud.commandframework.annotations.AnnotationParser;
import com.google.inject.throwingproviders.CheckedProvider;
import org.bukkit.command.CommandSender;

public interface AnnotationParserProvider extends CheckedProvider<AnnotationParser<CommandSender>> {

}
