package com.gjl2;

public class OxygenRoom extends Entity {

    public OxygenRoom() {
        this.radius = .75f;
    }


    @Override
    void update(float delta) {
        this.level.O2Level = Util.stepTo(level.O2Level, 0, delta * 100 / 150);
    }

    @Override
    void collide(Entity other, float delta) {
        this.level.O2Level = Util.stepTo(level.O2Level, 100, 20*delta);
    }
}

