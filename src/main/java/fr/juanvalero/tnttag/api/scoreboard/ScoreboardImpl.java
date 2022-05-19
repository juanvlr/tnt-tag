/*
 * Copyright (c) 2022 - Juan Valero
 */

package fr.juanvalero.tnttag.api.scoreboard;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import fr.juanvalero.tnttag.api.utils.component.ComponentSerializer;
import net.kyori.adventure.text.Component;
import org.apache.commons.lang.StringUtils;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.ScoreboardManager;

import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.IntStream;

/**
 * Default {@link Scoreboard} implementation.
 */
public class ScoreboardImpl implements Scoreboard {

    private static final int SCOREBOARD_MINIMUM_WIDTH = 35;

    private static final String OBJECTIVE_NAME = "tnt-tag";

    private final ComponentSerializer componentSerializer;
    private final org.bukkit.scoreboard.Scoreboard scoreboard;
    private final Objective objective;
    private final SortedMap<Integer, String> lines;

    @AssistedInject
    public ScoreboardImpl(
            ComponentSerializer componentSerializer,
            ScoreboardManager scoreboardManager,
            @Assisted Player player,
            @Assisted Component title) {
        this.componentSerializer = componentSerializer;

        this.scoreboard = scoreboardManager.getNewScoreboard();
        this.objective = this.scoreboard.registerNewObjective(OBJECTIVE_NAME, StringUtils.EMPTY, title);
        this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        player.setScoreboard(this.scoreboard);

        this.lines = new TreeMap<>();

        String blank = StringUtils.repeat(" ", SCOREBOARD_MINIMUM_WIDTH); // Fix scoreboard width
        this.lines.put(1, blank);
        this.updateLine(1, blank);
    }

    @Override
    public void updateLine(int line, Component content) {
        String serializedContent = this.componentSerializer.serialize(content);

        if (this.lines.containsKey(line)) {
            // If the line already exists, empty it
            String entry = this.lines.get(line);
            this.scoreboard.resetScores(entry);

            this.lines.put(line, serializedContent);
            this.updateLine(line, serializedContent);
        } else {
            // Create empty lines between the last line and the new one
            IntStream.range(this.lines.lastKey() + 1, line)
                    .forEach(index -> {
                        String blank = StringUtils.repeat(" ", index); // Trick to store multiple blank lines
                        this.lines.put(index, blank);
                    });

            this.lines.put(line, serializedContent);

            this.refreshLinesNumber();
        }
    }

    @Override
    public void eraseLines(int... lines) {
        for (int line : lines) {
            String content = this.lines.remove(line);
            this.scoreboard.resetScores(content);
        }

        this.refreshLinesNumber();
    }

    @Override
    public void empty() {
        this.eraseLines(IntStream.range(2, this.lines.size() + 1).toArray());
    }

    private void updateLine(int line, String content) {
        this.objective.getScore(content).setScore(this.lines.size() - line);
    }

    private void refreshLinesNumber() {
        this.lines.forEach(this::updateLine);
    }
}
