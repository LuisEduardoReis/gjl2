package com.gjl2;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Entity {

    public static final float GRAVITY = 10f;

    public Level level;
    public float x = 0;
    public float y = 0;
    public float z = 0;
    public float vx = 0;
    public float vy = 0;
    public float px = 0;
    public float py = 0;
    public float ex = 0;
    public float ey = 0;
    public float eFriction = 0.95f;
    public boolean hasGravity = false;
    public float radius = 0.25f;
    public boolean collidesWithLevel = false;
    public boolean collidesWithOthers = true;
    public boolean pushesOthers = false;
    public boolean remove = false;
    public float health = 100;
    public boolean dead = false;

    Entity() {
    }

    void preupdate(float delta) {
        this.px = this.x;
        this.py = this.y;
    }

    void update(float delta) {
        if (this.hasGravity) {
            this.vy -= GRAVITY * delta;
        }

        this.ex = this.eFriction * this.ex;
        this.ey = this.eFriction * this.ey;

        this.x += (this.vx + this.ex) * delta;
        this.y += (this.vy + this.ey) * delta;

        if (health <= 0 && !dead) {
            dead = true;
            die();
        }
    }

    void collide(Entity other,float delta) {}

    public void renderSprites(SpriteBatch spriteBatch) {

    }

    public void renderShapes(ShapeRenderer shapeRenderer) {
    }

    public void renderDebug(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.circle(this.x, this.y, this.radius, 24);
    }

    public boolean isTileSolid(Tile tile) {
        if (tile.door != null) {
            return !tile.door.open;
        } else {
            return tile.type.solid;
        }
    }

    public void handleLevelCollision(float x, float y){
    }

    protected void damage(int value) {
        health = Util.stepTo(health, 0, value);
    }

    public void die() {
    }
}
