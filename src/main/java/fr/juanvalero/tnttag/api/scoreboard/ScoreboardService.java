package fr.juanvalero.tnttag.api.scoreboard;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

public interface ScoreboardService {

    /**
     * Creates a scoreboard for the specified player and returns it.
     *
     * @param player The player to whom the scoreboard belongs
     * @param title  The title of the scoreboard
     * @return The created scoreboard
     */
    Scoreboard createScoreboard(Player player, Component title);

    /**
     * Deletes the scoreboard of the specified player.
     *
     * @param player The player to whom the scoreboard belongs
     */
    void deleteScoreboard(Player player);

    /**
     * Returns the scoreboard of the specified player.
     *
     * @param player The player to whom the scoreboard belongs
     * @return The scoreboard of the given player
     */
    Scoreboard getScoreboard(Player player);
}