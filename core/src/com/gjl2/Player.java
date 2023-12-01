package com.gjl2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Player extends Entity {

    Player() {
        this.collidesWithLevel = true;
    }

    @Override
    void update(float delta) {
        float velocity = 5;
        float jumpVelocity = 5;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) this.x -= velocity * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) this.x += velocity * delta;

        Tile currentTile = this.level.getTile(this.x, this.y);
        if (currentTile.type.ladder) {
            this.vy = 0;
            this.hasGravity = false;
            if (Gdx.input.isKeyPressed(Input.Keys.UP)) this.y += velocity * delta;
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) this.y -= velocity * delta;
        } else {
            this.hasGravity = true;
            if (Gdx.input.isKeyJustPressed(Input.Keys.UP) && this.vy == 0) this.vy = jumpVelocity;
        }

        super.update(delta);
    }

    @Override
    public void renderSprites(SpriteBatch spriteBatch) {
        //spriteBatch.draw(Assets.testTexture, this.x, this.y);
    }

    @Override
    public void renderShapes(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.circle(this.x, this.y, this.radius, 24);
    }

    @Override
    public boolean isTileSolid(TileType type, int x, int y) {
        if (type.ladder) {
            return this.y > y && !Gdx.input.isKeyPressed(Input.Keys.DOWN);
        } else {
            return type.solid;
        }
    }
}
