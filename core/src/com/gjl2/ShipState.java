package com.gjl2;

public class ShipState {

    public static final int MAX_SHIELD_HITS = 3;
    public static final int TIME_TO_DEPLETE_OXYGEN = 30;
    public final Level level;
    public float oxygenLevel = 100;
    public int shieldHits = 0;
    public int hullStatus = 100;

    ShipState(Level level) {
        this.level = level;
    }

    public void update(float delta) {
        if (hasAsteroidHits()) {
            oxygenLevel = Util.stepTo(oxygenLevel, 0, delta * 100 / TIME_TO_DEPLETE_OXYGEN);
        }
    }

    public boolean hasAsteroidHits() {
        return this.level.entities.stream().anyMatch(e -> e instanceof AsteroidHit);
    }

    public boolean isOxygenCritical() {
        return this.oxygenLevel < 50;
    }

    public boolean isAlarmOn() {
        return this.hasAsteroidHits() || isOxygenCritical();
    }
}
