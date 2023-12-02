package com.gjl2;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class OxygenRoom extends Entity implements PointableEntity {

    public OxygenRoom() {
        this.radius = .75f;
    }


    @Override
    void update(float delta) {
    }

    @Override
    void collide(Entity other, float delta) {
        if (other instanceof Player) {
            this.level.shipState.oxygenLevel = Util.stepTo(this.level.shipState.oxygenLevel, 100, 20*delta);
        }
    }

    @Override
    public TextureRegion getIcon() {
        return Assets.oxygenSprite;
    }

    @Override
    public boolean isActive() {
        return this.level.shipState.oxygenLevel < 90;
    }
}

