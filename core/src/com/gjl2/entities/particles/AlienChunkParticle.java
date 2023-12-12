package com.gjl2.entities.particles;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.gjl2.Assets;
import com.gjl2.Util;

public class AlienChunkParticle extends Particle {

    public float rotation = Util.randomRange(0, 360);
    public boolean rotating = true;

    public AlienChunkParticle() {
        super();
        color = new Color(0x297520ff);
        size = 0.4f;
        hasGravity = true;
        collidesWithLevel = true;

        vx = Util.randomRange(-2, 2);
        vy = Util.randomRange(2, 5);
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        if (rotating) rotation += delta * 360;
    }

    @Override
    public void handleLevelCollision(float x, float y) {
        super.handleLevelCollision(x, y);
        rotating = false;
    }

    @Override
    public void renderShapes(ShapeRenderer shapeRenderer) {
    }

    @Override
    public void renderSprites(SpriteBatch spriteBatch) {
        Util.affine2.idt();
        Util.affine2.translate(x, y);
        Util.affine2.rotate(rotation);
        Util.affine2.translate(-size, -size);
        spriteBatch.draw(Assets.alienChunkSprite, 2*size, 2*size, Util.affine2);
    }
}
