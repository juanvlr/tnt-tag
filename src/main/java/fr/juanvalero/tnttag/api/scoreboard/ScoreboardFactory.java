/*
 * Copyright (c) 2022 - Juan Valero
 */

package fr.juanvalero.tnttag.api.scoreboard;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

public interface ScoreboardFactory {

    /**
     * Creates a scoreboard.
     *
     * @param player The player associated to the scoreboard
     * @param title  The scoreboard title
     * @return The created scoreboard instance
     */
    Scoreboard createScoreboard(Player player, Component title);
}
