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
     * Erases the specified lines.
     *
     * @param lines The lines number to be erased.
     */
    void eraseLines(int... lines);
}
