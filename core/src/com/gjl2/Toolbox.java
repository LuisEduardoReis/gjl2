package com.gjl2;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Toolbox extends Entity {

    Toolbox() {
        this.radius = 0.4f;
    }

    @Override
    public void renderSprites(SpriteBatch spriteBatch) {
        spriteBatch.draw(Assets.toolboxSprite, this.x - this.radius, this.y - this.radius, this.radius * 2, this.radius * 2);
    }
}
