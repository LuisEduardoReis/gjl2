package com.gjl2;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class AirParticle extends Particle {

    AsteroidHit hole;

    AirParticle(AsteroidHit hole) {
        this.hole = hole;
    };

    @Override
    void update(float delta) {
        super.update(delta);

        if (hole.remove) remove = true;

        float distToHole = Util.pointDistance(x,y, hole.x, hole.y);
        float dx = (hole.x - x) / distToHole;
        float dy = (hole.y - y) / distToHole;

        x += 2 * delta * (dx + dy * 1.5f);
        y += 2 * delta * (dy - dx * 1.5f);
    }

    @Override
    public void renderShapes(ShapeRenderer shapeRenderer) {
        float s = 0.075f;
        shapeRenderer.setColor(Color.DARK_GRAY);
        shapeRenderer.rect(x - s/2,y - s/2, s,s);
    }
}
