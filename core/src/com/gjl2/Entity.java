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
    public boolean hasGravity = false;
    public float radius = 0.25f;
    public boolean collidesWithLevel = false;
    public boolean collidesWithOthers = false;
    public boolean remove = false;

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
}
