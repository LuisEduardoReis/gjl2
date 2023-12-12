package com.gjl2.entities.interactables;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gjl2.*;
import com.gjl2.entities.Entity;
import com.gjl2.entities.Player;
import com.gjl2.entities.interactables.Interactable;

public class Door extends Entity implements Interactable {

    public boolean open = false;
    public float openState = 0;

    public Door() {
        this.z = 1;
        this.radius = 0.75f;
    }

    @Override
    public void update(float delta) {
        float distToPlayer = Util.pointDistance(this.x, this.y, this.level.player.x, this.level.player.y);
        if (distToPlayer > 3 && this.open) {
            level.gameScreen.playSound(Assets.doorCloseSound, 0.5f);
            this.open = false;
        }

        this.openState = Util.stepTo(this.openState, this.open ? 1 : 0, delta / 0.35f);
    }

    @Override
    public void renderSprites(SpriteBatch spriteBatch) {
        spriteBatch.draw(Assets.doorSprite, (int) Math.floor(this.x), (int) Math.floor(this.y) + this.openState, 1,1);
    }

    @Override
    public void interact(Player player) {
        if (Math.floor(player.x) != Math.floor(this.x) || Math.floor(player.y) != Math.floor(this.y)) {
            if (!this.open) {
                this.open = true;
                level.gameScreen.playSound(Assets.doorOpenSound, 0.5f);
            } else {
                this.open = false;
                level.gameScreen.playSound(Assets.doorCloseSound, 0.5f);
            }
        }
    }

    @Override
    public String getHoverMessage() {
        return "Press " + GameKeys.INTERACT_KEY_NAME + " to open";
    }
}
