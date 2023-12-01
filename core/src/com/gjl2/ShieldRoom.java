package com.gjl2;

public class ShieldRoom extends Entity {

    public ShieldRoom() {
        this.radius = .75f;
        this.timer = new Timer(3);
    }

    private final Timer timer;

    @Override
    void update(float delta) {
        timer.update(delta);
    }

    @Override
    void collide(Entity other, float delta) {
        if (other instanceof Player) {
            if (timer.timer != 0) return;
            this.level.gameScreen.hud.addMessage("Recharging shield");
            this.level.shipState.shieldHits = (int) Util.clamp(this.level.shipState.shieldHits + 1, 0, ShipState.MAX_SHIELD_HITS); //could change this to have its own timer so that shield repair does not happen immediately
            timer.reset();
        }
    }
}

