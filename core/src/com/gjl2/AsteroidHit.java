package com.gjl2;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class AsteroidHit extends Entity {
    @Override
    public void renderShapes(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.circle(this.x, this.y, this.radius, 24);
    }
}
