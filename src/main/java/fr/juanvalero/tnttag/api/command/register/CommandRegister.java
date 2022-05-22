/*
 * Copyright (c) 2022 - Juan Valero
 */

package fr.juanvalero.tnttag.api.command.register;

/**
 * A register of the plugin commands.
 */
public interface CommandRegister {

    /**
     * Registers the commands of this register inside the plugin.
     */
    void registerCommands();
}
