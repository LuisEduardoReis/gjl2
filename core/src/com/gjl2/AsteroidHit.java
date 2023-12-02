package com.gjl2;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class AsteroidHit extends Entity implements Interactable, PointableEntity {

    public float fixState = 0;
    public float fixDelay = 4;

    public AsteroidHit() {
        this.z = -1;
    }

    @Override
    void update(float delta) {
        super.update(delta);

        if (this.fixState >= 1) {
            this.remove = true;
            this.level.gameScreen.hud.addMessage("Asteroid hole patched.");
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
        return "Press space to fix hole";
    }
}
