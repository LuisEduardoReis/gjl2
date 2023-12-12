package com.gjl2.entities.interactables;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.gjl2.*;
import com.gjl2.entities.Entity;
import com.gjl2.entities.Player;
import com.gjl2.entities.PointableEntity;
import com.gjl2.entities.particles.AirParticle;

public class AsteroidHit extends Entity implements Interactable, PointableEntity {

    public float fixState = 0;
    public float fixDelay = 4;
    public float particleTimer = 0;
    public float particleDelay = 0.05f;

    public AsteroidHit() {
        this.z = -1;
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        if (this.fixState >= 1) {
            this.remove = true;
            this.level.gameScreen.hud.addMessage("Asteroid hole patched.");
        }

        particleTimer = Util.stepTo(particleTimer, 0, delta);
        if (particleTimer == 0) {
            particleTimer = particleDelay;
            float dist = 2;
            float randomDirection = Util.randomRange(0, (float) (2 * Math.PI));
            level.addEntity(new AirParticle(this), (float) (x + dist * Math.cos(randomDirection)), (float) (y + dist * Math.sin(randomDirection)));
        }
    }

    @Override
    public void renderSprites(SpriteBatch spriteBatch) {
        spriteBatch.draw(Assets.asteroidHitSprite, this.x - 0.5f, this.y - 0.5f, 1,1);
    }

    @Override
    public void renderShapes(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.rect(this.x - 0.25f, this.y + 0.35f, 0.5f * this.fixState, 0.1f);
    }

    @Override
    public void interactHold(Player player, float delta) {
        this.fixState = Util.stepTo(fixState, 1, delta / fixDelay);
    }

    @Override
    public TextureRegion getIcon() {
        return Assets.asteroidHitSprite;
    }

    @Override
    public String getHoverMessage() {
        return "Press " + GameKeys.INTERACT_KEY_NAME + " to fix hole";
    }
}
