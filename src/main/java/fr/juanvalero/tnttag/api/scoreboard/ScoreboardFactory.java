/*
 * Copyright (c) 2022 - Juan Valero
 */

package fr.juanvalero.tnttag.api.scoreboard;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

public interface ScoreboardFactory {

    Scoreboard createScoreboard(Player player, Component title);
}
