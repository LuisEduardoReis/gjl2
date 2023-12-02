package com.gjl2;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class NavigationRoom extends Entity implements Interactable, PointableEntity {

    private static final float MIN_LOST_DELAY = 45;
    private static final float MAX_LOST_DELAY = 60;
    private static final float FIX_DELAY = 4;
    float fixState = 0;
    float lostTimer = 60;

    NavigationRoom() {
        radius = 0.75f;
    }

    @Override
    void update(float delta) {
        if (!level.shipState.lost) {
            lostTimer = Util.stepTo(lostTimer, 0, delta);
            if (lostTimer == 0) {
                this.level.gameScreen.hud.addWarning("Navigation failed!");
                level.shipState.lost = true;
                fixState = 0;
                lostTimer = Util.randomRange(MIN_LOST_DELAY, MAX_LOST_DELAY);
            }
        } else if (fixState == 1){
            level.shipState.lost = false;
            this.level.gameScreen.hud.addMessage("Ship back on course");
        }
    }

    @Override
    public void renderShapes(ShapeRenderer shapeRenderer) {
        if (level.shipState.lost) {
            shapeRenderer.setColor(Color.GREEN);
            shapeRenderer.rect(this.x - 0.25f, this.y + 0.35f, 0.5f * this.fixState, 0.1f);
        }
    }

    @Override
    public void interactHold(Player player, float delta) {
        if (level.shipState.lost) {
            fixState = Util.stepTo(fixState, 1, delta / FIX_DELAY);
        }
    }

    @Override
    public TextureRegion getIcon() {
        return Assets.navigationSprite;
    }

    @Override
    public boolean isActive() {
        return level.shipState.lost;
    }

    @Override
    public String getHoverMessage() {
        return level.shipState.lost ? "Press space to fix course" : null;
    }
}
