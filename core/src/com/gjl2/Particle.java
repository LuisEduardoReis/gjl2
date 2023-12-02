package com.gjl2;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Particle extends Entity {

    public Color color;
    float ttl = 1;
    public
    float size;

    public Particle() {
        radius = 0.05f;
        size = 0.075f;
        color = Color.DARK_GRAY;
    }

    @Override
    void update(float delta) {
        super.update(delta);
        ttl = Util.stepTo(ttl, 0, delta);
        if (ttl == 0) {
            remove = true;
        }
    }

    @Override
    public void handleLevelCollision(float x, float y) {
        vx = 0;
    }

    @Override
    public void renderShapes(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(color);
        shapeRenderer.rect(x - size/2,y - size/2, size,size);
    }
}
