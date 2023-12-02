package com.gjl2;

public class ShipState {

    public static final int TIME_TO_DEPLETE_OXYGEN = 30;
    public static final float SHIP_SPEED = 10f / 120;
    public static final float TIME_TO_REGENERATE_SHIELD = 5f;
    private static final float ENGINE_BLOWUP_DELAY = 30;
    public static final float INITIAL_DISTANCE_TO_EARTH = 10f;

    public final Level level;
    public float oxygenLevel = 100;
    public float shieldState = 100;
    public int hullStatus = 100;
    public boolean lost = false;
    public boolean engineOverloaded = false;
    public float engineBlowupTimer = ENGINE_BLOWUP_DELAY;
    public float distanceToEarth = INITIAL_DISTANCE_TO_EARTH;

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
        if (!lost) {
            distanceToEarth = Util.stepTo( distanceToEarth, 0, SHIP_SPEED * delta);
            if (distanceToEarth == 0) {
                level.endGame("You reached earth safely", true);
            }
        }
        if (engineOverloaded) {
            engineBlowupTimer = Util.stepTo(engineBlowupTimer, 0, delta);
            if (engineBlowupTimer == 0) {
                engineOverloaded = false;
                Assets.explosionSound.play((float) Main.VOLUME);
                level.endGame("The engine blew up");
            }
        } else {
            engineBlowupTimer = ENGINE_BLOWUP_DELAY;
        }


        if (hullStatus == 0) {
            level.endGame("The ship desintegrated");
        }
        if (oxygenLevel == 0) {
            level.endGame("You suffocated");
        }
    }

    public boolean hasAsteroidHits() {
        return this.level.entities.stream().anyMatch(e -> e instanceof AsteroidHit);
    }

    public boolean isOxygenCritical() {
        return this.oxygenLevel < 50;
    }

    public boolean isAlarmOn() {
        return this.hasAsteroidHits() || isOxygenCritical() || engineOverloaded;
    }
}
