package fr.juanvalero.tnttag.api.world;

public enum GameTime {

    DAY(1000),
    NIGHT(13000);

    private final int ticks;

    GameTime(int ticks) {
        this.ticks = ticks;
    }

    public int getTicks() {
        return ticks;
    }
}
