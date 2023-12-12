package com.gjl2.entities.rooms;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.gjl2.*;
import com.gjl2.entities.Entity;
import com.gjl2.entities.interactables.Interactable;
import com.gjl2.entities.Player;
import com.gjl2.entities.PointableEntity;

public class OxygenRoom extends Entity implements PointableEntity, Interactable {

    private static final float TIME_TO_REPLENISH = 5;

    public OxygenRoom() {
        this.radius = .75f;
    }


    @Override
    public void update(float delta) {
    }

    @Override
    public void interactHold(Player player, float delta) {
        this.level.shipState.oxygenLevel = Util.stepTo(this.level.shipState.oxygenLevel, 100, delta * 100 / TIME_TO_REPLENISH);
    }

    @Override
    public TextureRegion getIcon() {
        return Assets.oxygenSprite;
    }

    @Override
    public boolean isActive() {
        return this.level.shipState.oxygenLevel < 90;
    }

    @Override
    public String getHoverMessage() {
        return this.level.shipState.oxygenLevel < 100 ? "Press " + GameKeys.INTERACT_KEY_NAME + " to replenish oxygen" : null;
    }
}

