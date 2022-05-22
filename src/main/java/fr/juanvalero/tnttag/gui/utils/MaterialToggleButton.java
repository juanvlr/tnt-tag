package fr.juanvalero.tnttag.gui.utils;

public final class MaterialToggleButton {

    private final int x;
    private final int y;
    private final boolean defaultValue;

    public MaterialToggleButton(int x, int y, boolean defaultValue) {
        this.x = x;
        this.y = y;
        this.defaultValue = defaultValue;
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
}
