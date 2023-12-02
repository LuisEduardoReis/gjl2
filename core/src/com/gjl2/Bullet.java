package com.gjl2;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Bullet extends Entity {

    public Bullet() {
        z = -1;
        radius = 0.1f;
        collidesWithLevel = true;
    }

    @Override
    void collide(Entity other, float delta) {
        if (other instanceof Alien) {
            remove = true;
            other.damage(50);
            other.ex = 2 * Math.signum(other.x - x);
        }
    }

    @Override
    public void handleLevelCollision(float x, float y) {
        remove = true;
    }

    @Override
    public void renderSprites(SpriteBatch spriteBatch) {
        spriteBatch.draw(Assets.starSprite, x - radius, y - radius, 2 * radius, 2 * radius);
    }
}
