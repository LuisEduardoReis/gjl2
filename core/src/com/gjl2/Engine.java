package com.gjl2;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Engine extends Entity {
    private float flameState=3;
    private int animationIndex=3;
    Engine(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    void update(float delta) {
        flameState = Util.stepTo(flameState, 0, delta*15);
        //flameState-= .25F;
        if (flameState == 0){
            flameState = 3;
            animationIndex = (animationIndex + 1) % 3;
        }

    }

    @Override
    public void renderSprites(SpriteBatch spriteBatch) {
        //int state = (int)Math.floor(flameState);
        TextureRegion sprite;
        switch (animationIndex) {
            case 0:
                sprite = Assets.engineFireSprite1;
                break;
            case 1:
                sprite = Assets.engineFireSprite2;
                break;
            case 2:
                sprite = Assets.engineFireSprite3;
                break;
            default:
                sprite = Assets.engineFireSprite1;
        }
        spriteBatch.draw(sprite, (int) Math.floor(this.x), (int) Math.floor(this.y), 1,1);
    }
}