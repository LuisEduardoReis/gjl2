package com.gjl2;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class EngineRoom extends Entity implements Interactable, PointableEntity {

    private static final float MIN_LOST_DELAY = 45;
    private static final float MAX_LOST_DELAY = 60;
    private static final float FIX_DELAY = 4;
    float particleTimer = 0;
    float particleDelay = 0.2f;

    EngineRoom() {
        radius = 2f;
    }

    @Override
    void update(float delta) {
        super.update(delta);
        if (level.shipState.engineOverloaded) {
            particleTimer = Util.stepTo(particleTimer, 0, delta);
            if (particleTimer == 0) {
                particleTimer = particleDelay;
                for (int i = 0; i < 4; i++) {
                    level.addEntity(new EngineFireParticle(), x,y);
                }
            }
        }
    }

    @Override
    public void interact(Player player) {
        if (level.shipState.engineOverloaded) {
            level.shipState.engineOverloaded = false;
            this.level.gameScreen.hud.addMessage("Engine reset");
        }
    }

    @Override
    public TextureRegion getIcon() {
        return Assets.engineSprite;
    }

    @Override
    public boolean isActive() {
        return level.shipState.engineOverloaded;
    }

    @Override
    public String getHoverMessage() {
        return level.shipState.engineOverloaded ? "Press space to reset engine" : null;
    }
}
