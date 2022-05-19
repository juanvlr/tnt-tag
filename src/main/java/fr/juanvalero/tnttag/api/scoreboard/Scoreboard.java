/*
 * Copyright (c) 2022 - Juan Valero
 */

package fr.juanvalero.tnttag.api.scoreboard;

import net.kyori.adventure.text.Component;

public interface Scoreboard {

    /**
     * Updates the content of the specified line or creates it if it doesn't exist.
     *
     * @param line    The line number to be updated
     * @param content The new line content
     */
    void updateLine(int line, Component content);

    /**
     * Empty the scoreboard.
     */
    void empty();
}
