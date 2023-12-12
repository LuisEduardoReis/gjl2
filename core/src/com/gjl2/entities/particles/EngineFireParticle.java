package com.gjl2.entities.particles;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.gjl2.Assets;
import com.gjl2.Util;
import com.gjl2.entities.particles.Particle;

public class EngineFireParticle extends Particle {

    public EngineFireParticle() {
        super();
        size = 0.4f;
        ttl = 1;

        float direction = Util.randomRange(0, (float) (2 * Math.PI));
        float speed = Util.randomRange(5,7);
        ex = (float) (speed * Math.cos(direction));
        ey = (float) (speed * Math.sin(direction));
    }

    @Override
    public void renderShapes(ShapeRenderer shapeRenderer) {
    }

    @Override
    public void renderSprites(SpriteBatch spriteBatch) {
        Util.affine2.idt();
        Util.affine2.translate(x, y);
        Util.affine2.scale(ttl, ttl);
        Util.affine2.translate(-size, -size);
        spriteBatch.draw(Assets.engineFireSprite, 2*size, 2*size, Util.affine2);
    }
}
