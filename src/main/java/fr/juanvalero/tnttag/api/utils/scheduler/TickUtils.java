/*
 * Copyright (c) 2022 - Juan Valero
 */

package fr.juanvalero.tnttag.api.utils.scheduler;

public class TickUtils {

    public static final int TICKS_PER_SECOND = 20;

    public static int getTicks(int seconds) {
        return seconds * TICKS_PER_SECOND;
    }
}
