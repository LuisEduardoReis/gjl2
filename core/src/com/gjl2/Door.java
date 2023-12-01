package com.gjl2;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Door extends Entity implements Interactable {

    boolean open = false;

    public Door() {
        this.radius = 0.75f;
    }

    @Override
    public void renderSprites(SpriteBatch spriteBatch) {
        if (!this.open) {
            spriteBatch.draw(Assets.doorSprite, (int) Math.floor(this.x), (int) Math.floor(this.y), 1,1);
        }
    }

    @Override
    public void interact(Player player) {
        if (Math.floor(player.x) != Math.floor(this.x) || Math.floor(player.y) != Math.floor(this.y)) {
            this.open = !this.open;
        }
    }
}
