package com.gjl2.entities.rooms;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.gjl2.*;
import com.gjl2.entities.*;
import com.gjl2.entities.interactables.Interactable;
import com.gjl2.entities.particles.EngineFireParticle;

public class EngineRoom extends Entity implements Interactable, PointableEntity {

    public float particleTimer = 0;
    public float particleDelay = 0.2f;

    public EngineRoom() {
        radius = 2f;
    }

    @Override
    public void update(float delta) {
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
        return level.shipState.engineOverloaded ? "Press " + GameKeys.INTERACT_KEY_NAME + " to reset engine" : null;
    }
}
