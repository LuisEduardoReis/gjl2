package com.gjl2.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Affine2;
import com.badlogic.gdx.math.Vector2;
import com.gjl2.*;
import com.gjl2.entities.interactables.Interactable;
import com.gjl2.level.Tile;

public class Player extends Entity {

    public static float IDLE_DELAY = 0.2f;
    public static float ANIMATION_DELAY = 0.1f;

    public Vector2 teleport = null;
    public boolean goingRight = true;
    public boolean isClimbing = false;
    public float idleTimer = 0;
    public float animationTimer = 0;
    public int animationFrame = 0;
    public int ammo = 100;
    public boolean isPlayerBeingDamaged = false;

    public Interactable currentInteractable = null;

    public Player() {
        this.radius = 0.4f;
        this.hasGravity = true;
        this.collidesWithLevel = true;
        this.pushesOthers = true;
    }

    @Override
    public void update(float delta) {
        isClimbing = false;
        float velocity = 5;
        float jumpVelocity = 5;
        if (Gdx.input.isKeyPressed(GameKeys.LEFT)) {
            this.x -= velocity * delta;
            this.goingRight = false;
            this.idleTimer = IDLE_DELAY;
        }
        if (Gdx.input.isKeyPressed(GameKeys.RIGHT)) {
            this.x += velocity * delta;
            this.goingRight = true;
            this.idleTimer = IDLE_DELAY;
        }

        Tile currentTile = this.level.getTile(this.x, this.y);
        if (currentTile.type.ladder) {
            this.vy = 0;
            this.hasGravity = false;
            if (Gdx.input.isKeyPressed(GameKeys.UP)) {
                this.y += velocity * delta;
                isClimbing = true;
            }
            if (Gdx.input.isKeyPressed(GameKeys.DOWN)) {
                this.y -= velocity * delta;
                isClimbing = true;
            }
        } else {
            this.hasGravity = true;
            if (Gdx.input.isKeyJustPressed(GameKeys.UP) && this.vy == 0) {
                this.vy = jumpVelocity;
            }
        }

        if (ammo > 0 && Gdx.input.isKeyJustPressed(GameKeys.FIRE) && !currentTile.type.ladder) {
            ammo--;
            Bullet bullet = new Bullet();
            bullet.vx = 10 * (goingRight ? 1 : -1);
            level.addEntity(bullet, x,y - radius / 2);
            level.gameScreen.playSound(Assets.laserSound);
        }

        this.idleTimer = Util.stepTo(this.idleTimer, 0, delta);
        this.animationTimer = Util.stepTo(this.animationTimer, 0, delta);

        if (this.animationTimer == 0) {
            this.animationTimer = ANIMATION_DELAY;
            this.animationFrame = (this.animationFrame + 1) % Assets.playerMovement.size();
        }

        currentInteractable = null;
        super.update(delta);
    }

    @Override
    public void postupdate(float delta) {
        if (teleport != null) {
            x = teleport.x;
            y = teleport.y;
            teleport = null;
        }
    }

    @Override
    public void collide(Entity other, float delta) {
        if (other instanceof Interactable) {
            currentInteractable = (Interactable) other;
            if (Gdx.input.isKeyJustPressed(GameKeys.INTERACT)) {
                ((Interactable) other).interact(this);
            } else if (Gdx.input.isKeyPressed(GameKeys.INTERACT)) {
                ((Interactable) other).interactHold(this, delta);
            }
        } else if (other instanceof Alien) {
            this.damage(1);
            if (other.x < this.x) this.ex = 5;
            if (other.x >= this.x) this.ex = -5;
            this.ey = 3;
            this.isPlayerBeingDamaged = true;
        }
    }

    Affine2 affine2 = new Affine2();

    @Override
    public void damage(int value) {
        super.damage(value);
        level.gameScreen.screenShakeTimer = 0.1f;
        if (!level.gameOver) {
            level.gameScreen.playSound(Assets.hitSound);
        }
    }

    @Override
    public void renderSprites(SpriteBatch spriteBatch) {
        if(isPlayerBeingDamaged) spriteBatch.setColor(Color.RED);

        Tile currentTile = this.level.getTile(this.x, this.y);
        Tile tileAtFeet = this.level.getTile(this.x, this.y-this.radius-0.01f);
        affine2.idt();
        float r = this.radius;
        affine2.translate(this.x - r, this.y - r);
        affine2.translate(r, r);
        affine2.scale(this.goingRight ? -1 : 1, 1);
        affine2.translate(-r, -r);
        boolean onLadder = currentTile.type.ladder && !tileAtFeet.type.solid;

        if (onLadder) {
            if (this.isClimbing) {
                spriteBatch.draw(Assets.playerClimbing.get(this.animationFrame % 2), r * 2, r * 2, affine2);
            } else {
                spriteBatch.draw(Assets.playerClimbing.get(0), r * 2, r * 2, affine2);
            }
        } else if (isMoving()) {
            spriteBatch.draw(Assets.playerMovement.get(this.animationFrame), r * 2, r * 2, affine2);
        } else {
            spriteBatch.draw(Assets.playerMovement.get(0), r * 2, r * 2, affine2);
        }


        spriteBatch.setColor(Color.WHITE);
        isPlayerBeingDamaged = false;

        if (ammo > 0 && !onLadder) {
            spriteBatch.draw(Assets.gunSprite, r * 2,r * 2, affine2);
        }
    }

    @Override
    public void renderShapes(ShapeRenderer shapeRenderer) {
        // shapeRenderer.setColor(Color.RED);
        // shapeRenderer.circle(this.x, this.y, this.radius, 24);
    }

    @Override
    public boolean isTileSolid(Tile tile) {
        if (tile.type.ladder) {
            Tile tileAt = this.level.getTile(this.x, this.y);
            return !tileAt.type.ladder && this.y > tile.y + 1 && !Gdx.input.isKeyPressed(GameKeys.DOWN);
        } else {
            return super.isTileSolid(tile);
        }
    }

    public boolean isMoving() {
        return idleTimer != 0;
    }

    public void teleport(float x, float y) {
        teleport = new Vector2(x,y);
    }
}
