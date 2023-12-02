package com.gjl2;

public class ShieldRoom extends Entity implements Interactable {

    public ShieldRoom() {
        this.radius = .75f;
        this.timer = new Timer(3);
    }

    private final Timer timer;

    @Override
    public void interactHold(Player player, float delta) {
        timer.update(delta);
        if (timer.timer == 0) {
            this.level.gameScreen.hud.addMessage("Shield replenished");
            this.level.shipState.shieldHits = (int) Util.clamp(this.level.shipState.shieldHits + 1, 0, ShipState.MAX_SHIELD_HITS); //could change this to have its own timer so that shield repair does not happen immediately
            timer.reset();
        }
    }

    @Override
    public String getHoverMessage() {
        return "Press space to replenish shields";
    }
}

