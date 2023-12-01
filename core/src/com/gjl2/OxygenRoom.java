package com.gjl2;

public class OxygenRoom extends Entity {

    public OxygenRoom() {
        this.radius = .75f;
    }


    @Override
    void update(float delta) {
    }

    @Override
    void collide(Entity other, float delta) {
        this.level.shipState.oxygenLevel = Util.stepTo(this.level.shipState.oxygenLevel, 100, 20*delta);
    }
}

