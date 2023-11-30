package com.gjl2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Player extends Entity {

    @Override
    void update(float delta) {
        float velocity = 5;
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) this.y += velocity * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) this.y -= velocity * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) this.x -= velocity * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) this.x += velocity * delta;
    }

    @Override
    public void renderSprites(SpriteBatch spriteBatch) {
        //spriteBatch.draw(Assets.testTexture, this.x, this.y);
    }

    @Override
    public void renderShapes(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.circle(this.x, this.y, 0.3f, 24);
    }
}
