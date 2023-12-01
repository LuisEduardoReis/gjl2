package com.gjl2;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Affine2;

import java.util.LinkedList;
import java.util.List;

public class Hud {

    public static final float MESSAGE_DURATION = 5f;
    public static final float WARNING_DURATION = 2f;

    class HudMessage {
        String message;
        float time;

        public HudMessage(String message) {
            this.message = message;
        }
    }

    private float time = 0;
    private final GameScreen gameScreen;
    private HudMessage warningMessage = null;
    private final List<HudMessage> messages = new LinkedList<>();

    Hud(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    public void update(float delta) {
        this.time += delta;

        for (HudMessage message : this.messages) {
            message.time += delta;
        }
        if (this.warningMessage != null) this.warningMessage.time += delta;
    };

    public void addWarning(String message) {
        this.warningMessage = new HudMessage(message);
    }
    public void addMessage(String message) {
        this.messages.add(new HudMessage(message));
    }

    public void renderSprites(SpriteBatch spriteBatch) {

    }

    public void renderShapes(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(0,0,0, 0.5f);
        shapeRenderer.rect(0,0, 500, 250);

        if (this.gameScreen.level.shipState.isAlarmOn()) {
            shapeRenderer.setColor(0.5f, 0, 0, 0.5f * (float) Math.abs(Math.sin(4 * this.time)));
            shapeRenderer.rect(0,0,Main.WIDTH,Main.HEIGHT);
        }
    }

    public void renderText(SpriteBatch spriteBatch) {
        BitmapFont font = Assets.font;
        Level level = this.gameScreen.level;
        font.setColor(Color.GREEN);
        font.getData().setScale(1.5f);
        font.draw(spriteBatch, "Hull status 100%", 25, 210);
        font.draw(spriteBatch, String.format("Oxygen level %d%%", (int) level.shipState.oxygenLevel), 25, 140);
        font.draw(spriteBatch, String.format("Shield level %d", level.shipState.shieldHits), 25, 70);

        if (level.gameEvents.timeToNextAsteroid > 0) {
            font.draw(spriteBatch, String.format(String.format("Asteroid hit in %.1f", level.gameEvents.timeToNextAsteroid)), 0, Main.HEIGHT - 50);
        }

        font.setColor(Color.WHITE);
        Assets.font.getData().setScale(1.5f);
        for (int i = 0; i < messages.size() && i < 10; i++) {
            HudMessage hudMessage = messages.get(messages.size() - 1 - i);
            if (hudMessage.time > MESSAGE_DURATION) break;
            Util.drawTextAlignRight(spriteBatch, font, hudMessage.message, Main.WIDTH - 20, 50 + 50 * i);
        }

        if (this.warningMessage != null && this.warningMessage.time < MESSAGE_DURATION) {
            font.getData().setScale(4f);
            float fadeDelay = 0.5f;
            font.setColor(1,1,1, warningMessage.time < WARNING_DURATION - fadeDelay ? 1f : (MESSAGE_DURATION - warningMessage.time) / fadeDelay);
            Util.drawTextCentered(spriteBatch, font, warningMessage.message, Main.WIDTH / 2f, Main.HEIGHT * 3f/4);
        }
    }
}
