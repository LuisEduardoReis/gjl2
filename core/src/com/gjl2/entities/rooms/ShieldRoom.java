package com.gjl2.entities.rooms;

import com.gjl2.*;
import com.gjl2.entities.Entity;
import com.gjl2.entities.interactables.Interactable;
import com.gjl2.entities.Player;

public class ShieldRoom extends Entity implements Interactable {

    public ShieldRoom() {
        this.radius = .75f;
    }

    @Override
    public void interactHold(Player player, float delta) {
        if (level.shipState.shieldState < 100) {
            level.shipState.shieldState = Util.stepTo(level.shipState.shieldState, 100, delta * 100 / ShipState.TIME_TO_REGENERATE_SHIELD);
            if (level.shipState.shieldState == 100) {
                this.level.gameScreen.playSound(Assets.shieldsUpSound);
                this.level.gameScreen.hud.addMessage("Shield replenished");
            }
        }

    }

    @Override
    public String getHoverMessage() {
        return level.shipState.shieldState < 100 ? "Press " + GameKeys.INTERACT_KEY_NAME + " to replenish shields" : null;
    }
}

