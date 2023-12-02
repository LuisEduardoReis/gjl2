package com.gjl2;

public class ShipState {

    public static final int MAX_SHIELD_HITS = 1;
    public static final int TIME_TO_DEPLETE_OXYGEN = 30;
    public static final float SHIP_SPEED = 10f / 300;

    public final Level level;
    public float oxygenLevel = 100;
    public int shieldHits = 0;
    public int hullStatus = 100;
    public boolean lost = false;

    public float distanceToEarth = 10;

    ShipState(Level level) {
        this.level = level;
    }

    public void update(float delta) {
        if (hasAsteroidHits()) {
            boolean wasOxygenCritical = isOxygenCritical();
            oxygenLevel = Util.stepTo(oxygenLevel, 0, delta * 100 / TIME_TO_DEPLETE_OXYGEN);
            if (!wasOxygenCritical && isOxygenCritical()) {
                level.gameScreen.hud.addWarning("Oxygen level critical!");
            }
        }
        if (lost) {
            distanceToEarth += SHIP_SPEED * delta;
        } else {
            distanceToEarth = Util.stepTo( distanceToEarth, 0, SHIP_SPEED * delta);
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
