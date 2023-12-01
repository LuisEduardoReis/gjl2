package com.gjl2;

public class ShipState {

    public static final int MAX_SHIELD_HITS = 3;
    public final Level level;
    public float oxygenLevel = 100;
    public int shieldHits = 0;

    ShipState(Level level) {
        this.level = level;
    }

    public void update(float delta) {
        if (hasAsteroidHits()) {
            oxygenLevel = Util.stepTo(oxygenLevel, 0, delta * 100 / 150);
        }
    }

    public boolean hasAsteroidHits() {
        return this.level.entities.stream().anyMatch(e -> e instanceof AsteroidHit);
    }

    public boolean isOxygenCritical() {
        return this.oxygenLevel < 0.3;
    }

    public boolean isAlarmOn() {
        return this.hasAsteroidHits() || isOxygenCritical();
    }
}
