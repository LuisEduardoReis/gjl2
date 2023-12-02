package com.gjl2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Alien extends Entity{

    private float radius;
    private int direction;

    Alien() {
        this.radius = 0.2f;
        this.hasGravity = true;
        this.collidesWithLevel = true;
        this.collidesWithOthers = true;

        //randomize initial direction
        this.direction = (int) Util.randomRange(1, 2);
    }

    @Override
    public void renderShapes(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.circle(this.x, this.y, this.radius, 24);
    }

    @Override
    void collide(Entity other, float delta) {
        super.collide(other, delta);

        if (other instanceof Player ){
            return;
        }

        if (direction == 1) { direction = 2;} else direction = 1;
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
        }else if (direction == 2){
            this.x += velocity * delta;
        }


        //if (r == 3 && this.vy == 0) {
        //    this.vy = jumpVelocity;
        //}

        super.update(delta);
    }
}
