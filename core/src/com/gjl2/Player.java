package com.gjl2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Affine2;

public class Player extends Entity {

    public static float IDLE_DELAY = 0.2f;

    boolean goingRight = true;
    float idleTimer = 0;

    Player() {
        this.radius = 0.4f;
        this.hasGravity = true;
        this.collidesWithLevel = true;
    }

    @Override
    void update(float delta) {
        float velocity = 5;
        float jumpVelocity = 5;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            this.x -= velocity * delta;
            this.goingRight = false;
            this.idleTimer = IDLE_DELAY;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            this.x += velocity * delta;
            this.goingRight = true;
            this.idleTimer = IDLE_DELAY;
        }

        Tile currentTile = this.level.getTile(this.x, this.y);
        if (currentTile.type.ladder) {
            this.vy = 0;
            this.hasGravity = false;
            if (Gdx.input.isKeyPressed(Input.Keys.UP)) this.y += velocity * delta;
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) this.y -= velocity * delta;
        } else {
            this.hasGravity = true;
            if (Gdx.input.isKeyJustPressed(Input.Keys.UP) && this.vy == 0) {
                this.vy = jumpVelocity;
            }
        }

        this.idleTimer = Util.stepTo(this.idleTimer, 0, delta);

        super.update(delta);
    }

    @Override
    void collide(Entity other, float delta) {
        if (other instanceof Interactable) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                ((Interactable) other).interact(this);
            } else if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
                ((Interactable) other).interactHold(this, delta);
            }

        }
    }

    Affine2 affine2 = new Affine2();
    @Override
    public void renderSprites(SpriteBatch spriteBatch) {
        affine2.idt();
        float r = this.radius;
        affine2.translate(this.x - r, this.y - r);
        affine2.translate(r, r);
        affine2.scale(this.goingRight ? -1 : 1, 1);
        affine2.translate(-r, -r);

        spriteBatch.draw(Assets.playerSprite, r * 2, r * 2, affine2);
    }

    @Override
    public void renderShapes(ShapeRenderer shapeRenderer) {
        //shapeRenderer.setColor(Color.RED);
        //shapeRenderer.circle(this.x, this.y, this.radius, 24);
    }

    @Override
    public boolean isTileSolid(Tile tile) {
        if (tile.type.ladder) {
            return this.y > tile.y + 1 && !Gdx.input.isKeyPressed(Input.Keys.DOWN);
        } else {
            return super.isTileSolid(tile);
        }
    }
}
