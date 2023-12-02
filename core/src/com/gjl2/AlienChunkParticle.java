package com.gjl2;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class AlienChunkParticle extends Particle {

    float rotation = Util.randomRange(0, 360);
    boolean rotating = true;

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
    void update(float delta) {
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
