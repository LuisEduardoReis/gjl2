package com.gjl2;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class NavigationRoom extends Entity implements Interactable, PointableEntity {
    private static final float FIX_DELAY = 4;
    float fixState = 0;

    NavigationRoom() {
        radius = 0.75f;
    }

    @Override
    void update(float delta) {
        if (level.shipState.lost) {
            if (fixState == 1){
                level.shipState.lost = false;
                this.level.gameScreen.hud.addMessage("Ship back on course");
            }
        } else {
            fixState = 0;
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
        return level.shipState.lost ? "Press " + GameKeys.INTERACT_KEY_NAME + " to fix course" : null;
    }
}
