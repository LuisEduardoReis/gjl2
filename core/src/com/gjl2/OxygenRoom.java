package com.gjl2;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class OxygenRoom extends Entity implements PointableEntity, Interactable {

    private static final float TIME_TO_REPLENISH = 5;

    public OxygenRoom() {
        this.radius = .75f;
    }


    @Override
    void update(float delta) {
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
        return this.level.shipState.oxygenLevel < 100 ? "Press space to replenish oxygen" : null;
    }
}

