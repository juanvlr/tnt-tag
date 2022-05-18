/*
 * Copyright (c) 2022 - Juan Valero
 */

package fr.juanvalero.tnttag.gui.utils;

import org.bukkit.Material;

public final class TitleToggleButton {

    private final int x;
    private final int y;
    private final boolean defaultValue;
    private final String name;
    private final Material material;

    public TitleToggleButton(int x, int y, boolean defaultValue, String name, Material material) {
        this.x = x;
        this.y = y;
        this.defaultValue = defaultValue;
        this.name = name;
        this.material = material;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public boolean getDefaultValue() {
        return this.defaultValue;
    }

    public String getName() {
        return this.name;
    }

    public Material getMaterial() {
        return this.material;
    }
}
