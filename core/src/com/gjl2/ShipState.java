package com.gjl2;

public class ShipState {

    public final Level level;
    public boolean isLeakingOxygen = false;
    public float oxygenLevel = 100;

    ShipState(Level level) {
        this.level = level;
    }

    public void update(float delta) {
        if (isLeakingOxygen) {
            oxygenLevel = Util.stepTo(oxygenLevel, 0, delta * 100 / 150);
        }
    }
}
