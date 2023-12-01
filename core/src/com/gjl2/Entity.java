package com.gjl2;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Entity {

    public static final float GRAVITY = 10f;

    public Level level;
    public float x = 0;
    public float y = 0;
    public float vx = 0;
    public float vy = 0;
    public float px = 0;
    public float py = 0;
    public boolean hasGravity = true;
    public float radius = 0.25f;
    public boolean collidesWithLevel = false;

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
        this.x += this.vx * delta;
        this.y += this.vy * delta;
    }

    public void renderSprites(SpriteBatch spriteBatch) {

    }

    public void renderShapes(ShapeRenderer shapeRenderer) {

    }

    public boolean isTileSolid(TileType type, int x, int y) {
        return type.solid;
    }
}
