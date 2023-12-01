package com.gjl2;

public class ShipState {

    public final Level level;
    public float oxygenLevel = 100;

    ShipState(Level level) {
        this.level = level;
    }

    public void update(float delta) {
        boolean hasAsteroidHits = this.level.entities.stream().anyMatch(entity -> entity instanceof AsteroidHit);

        if (hasAsteroidHits) {
            oxygenLevel = Util.stepTo(oxygenLevel, 0, delta * 100 / 150);
        }
    }
}
