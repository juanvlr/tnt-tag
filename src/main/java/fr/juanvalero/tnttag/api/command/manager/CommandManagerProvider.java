/*
 * Copyright (c) 2022 - Juan Valero
 */

package fr.juanvalero.tnttag.api.command.manager;

import cloud.commandframework.CommandManager;
import com.google.inject.throwingproviders.CheckedProvider;
import org.bukkit.command.CommandSender;

/**
 * Provides the {@link CommandManager}.
 */
public interface CommandManagerProvider extends CheckedProvider<CommandManager<CommandSender>> {

}
