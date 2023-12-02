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
}
