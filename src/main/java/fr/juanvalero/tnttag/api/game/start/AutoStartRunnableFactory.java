/*
 * Copyright (c) 2022 - Juan Valero
 */

package fr.juanvalero.tnttag.api.game.start;

public interface AutoStartRunnableFactory {

    AutoStartRunnable createAutoStartRunnable(int time);
}
