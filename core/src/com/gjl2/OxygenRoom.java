package com.gjl2;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class OxygenRoom extends Entity {

    public OxygenRoom() {
        this.radius = .75f;
    }


    @Override
    void collide(Entity other, float delta) {
        this.level.O2Level = Util.stepTo(level.O2Level, 100, 20*delta);
    }
}

