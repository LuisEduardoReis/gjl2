package com.gjl2;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Door extends Entity implements Interactable {

    boolean open = false;
    float openState = 0;

    public Door() {
        this.z = 1;
        this.radius = 0.75f;
    }

    @Override
    void update(float delta) {
        float distToPlayer = Util.pointDistance(this.x, this.y, this.level.player.x, this.level.player.y);
        if (distToPlayer > 3) this.open = false;

        this.openState = Util.stepTo(this.openState, this.open ? 1 : 0, 2 * delta);
    }

    @Override
    public void renderSprites(SpriteBatch spriteBatch) {
        spriteBatch.draw(Assets.doorSprite, (int) Math.floor(this.x), (int) Math.floor(this.y) + this.openState, 1,1);
    }

    @Override
    public void interact(Player player) {
        if (Math.floor(player.x) != Math.floor(this.x) || Math.floor(player.y) != Math.floor(this.y)) {
            this.open = !this.open;
        }
    }

    @Override
    public String getHoverMessage() {
        return "Press space to open";
    }
}
