package com.gjl2;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class AsteroidHit extends Entity implements Interactable {

    public float fixState = 0;
    public float fixDelay = 4;


    @Override
    void update(float delta) {
        super.update(delta);

        if (this.fixState >= 1) {
            this.remove = true;
            this.level.gameScreen.hud.addMessage("Asteroid hole patched.");
        }
    }

    @Override
    public void renderShapes(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.circle(this.x, this.y, this.radius, 24);

        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.rect(this.x - 0.25f, this.y + 0.35f, 0.5f * this.fixState, 0.1f);
    }

    @Override
    public void interactHold(Player player, float delta) {
        this.fixState = Util.stepTo(fixState, 1, delta / fixDelay);
    }
}
