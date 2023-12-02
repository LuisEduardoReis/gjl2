package com.gjl2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Affine2;

public class Alien extends Entity{

    private float radius;
    private int direction;
    private float rotation = 0;
    private float jumpTimer = 0;
    private float jumpDelay = 2;

    Alien() {
        this.radius = 0.4f;
        this.hasGravity = true;
        this.collidesWithLevel = true;
        this.pushesOthers = true;

        //randomize initial direction
        this.direction = (int) Util.randomRange(1, 2);
    }

    @Override
    void collide(Entity other, float delta) {
        super.collide(other, delta);

        if (other instanceof Alien){
            if (direction == 1) { direction = 2;} else direction = 1;
        }
    }

    @Override
    public void handleLevelCollision(float x, float y) {
        super.handleLevelCollision(x, y);
        if (y != 0) return;
        if (direction == 1) { direction = 2;} else direction = 1;
    }

    void update(float delta) {
        float velocity = 1;
        float jumpVelocity = 5;

        if (direction == 1){
            this.x -= velocity * delta;
            rotation += delta * 180;
        }else if (direction == 2){
            this.x += velocity * delta;
            rotation -= delta * 180;
        }

        jumpTimer = Util.stepTo(jumpTimer, 0, delta);
        if (jumpTimer == 0 && vy == 0) {
            jumpTimer = jumpDelay;
            vy += jumpVelocity;
        }

        super.update(delta);
    }

    Affine2 affine2 = new Affine2();
    @Override
    public void renderSprites(SpriteBatch spriteBatch) {
        affine2.idt();
        affine2.translate(x, y);
        affine2.rotate(rotation);
        affine2.translate(-radius,-radius);
        spriteBatch.draw(Assets.alienSprite, 2*radius, 2*radius, affine2);
    }
}
