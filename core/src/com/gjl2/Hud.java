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
    public static float AVATAR_ANIMATION_DELAY = 1f;

    static class HudMessage {
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

    float avatarAnimationTimer = 0;
    int avatarAnimationFrame = 0;

    Hud(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    public void update(float delta) {
        this.time += delta;

        for (HudMessage message : this.messages) {
            message.time += delta;
        }
        if (this.warningMessage != null) this.warningMessage.time += delta;

        this.avatarAnimationTimer = Util.stepTo(this.avatarAnimationTimer, 0, delta);

        if (this.avatarAnimationTimer == 0) {
            this.avatarAnimationTimer = AVATAR_ANIMATION_DELAY;
            this.avatarAnimationFrame = (avatarAnimationFrame + 1) % 2;
        }
    }

    public void addWarning(String message) {
        this.warningMessage = new HudMessage(message);
    }

    public void addMessage(String message) {
        this.messages.add(new HudMessage(message));
    }

    Affine2 affine2 = new Affine2();

    public void renderSprites(SpriteBatch spriteBatch) {
        Level level = this.gameScreen.level;
        level.entities.stream()
                .filter(e -> e instanceof PointableEntity)
                .filter(e -> ((PointableEntity) e).isActive())
                .forEach(e -> {
                    float x = (e.x - level.player.x) / this.gameScreen.cameraScale;
                    float y = (e.y - level.player.y) / this.gameScreen.cameraScale;
                    if (Util.isBetween(x, -Main.WIDTH / 2f, Main.WIDTH / 2f) && Util.isBetween(y, -Main.HEIGHT / 2f, Main.HEIGHT / 2f))
                        return;
                    float distToPlayer = Util.pointDistance(e.x, e.y, level.player.x, level.player.y);
                    float dx = (e.x - level.player.x) / distToPlayer;
                    float dy = (e.y - level.player.y) / distToPlayer;
                    float sx = Main.WIDTH / 2f + 250 * dx;
                    float sy = Main.HEIGHT / 2f + 250 * dy;
                    float s = 75;

                    affine2.idt();
                    affine2.translate(sx, sy);
                    affine2.rotate((float) (Math.atan2(dy, dx) * 180 / Math.PI));
                    affine2.translate(-s / 2, -s / 2);

                    if (time % 1f < 0.75) {
                        spriteBatch.draw(Assets.arrowSprite, s, s, affine2);
                        spriteBatch.draw(((PointableEntity) e).getIcon(), sx - s / 2, sy - s / 2, s, s);
                    }
                });
    }

    public void renderShapes(ShapeRenderer shapeRenderer) {
        Level level = this.gameScreen.level;
        shapeRenderer.setColor(0, 0, 0, 0.5f);
        shapeRenderer.rect(0, 0, 520, 370);

        if (level.shipState.isAlarmOn()) {
            shapeRenderer.setColor(0.5f, 0, 0, 0.5f * (float) Math.abs(Math.sin(4 * this.time)));
            shapeRenderer.rect(0, 0, Main.WIDTH, Main.HEIGHT);
        }

        if (this.gameScreen.level.gameOver) {
            if (!this.gameScreen.level.gameWon) {
                shapeRenderer.setColor(0.5f, 0, 0, 1);
            } else {
                shapeRenderer.setColor(0, 0.5f, 0, 1);
            }
            shapeRenderer.rect(0, 0, Main.WIDTH, Main.HEIGHT);
        }
    }

    public void renderOverlay(SpriteBatch spriteBatch) {
        BitmapFont font = Assets.font;
        Level level = this.gameScreen.level;
        font.getData().setScale(1.5f);

        if (!level.gameOver) {
            font.setColor(level.player.health < 25 ? Color.RED : Color.GREEN);
            if (level.player.health >= 25 || time % 0.5 < 0.25) {
                font.draw(spriteBatch, String.format("Crew health %d", (int) level.player.health), 25, 350);
            }

            font.setColor(level.shipState.lost ? Color.RED : Color.GREEN);
            if (!level.shipState.lost || time % 1 < 0.75) {
                font.draw(spriteBatch, String.format("Earth %.1f ly", level.shipState.distanceToEarth), 25, 280);
            }

            font.setColor(level.shipState.hullStatus < 30 ? Color.RED : Color.GREEN);
            font.draw(spriteBatch, String.format("Hull status %d%%", level.shipState.hullStatus), 25, 210);

            font.setColor(level.shipState.isOxygenCritical() ? Color.RED : Color.GREEN);
            if (!level.shipState.isOxygenCritical() || time % 1 < 0.75) {
                font.draw(spriteBatch, String.format("Oxygen level %d%%", (int) level.shipState.oxygenLevel), 25, 140);
            }

            font.setColor(level.shipState.shieldState < 100 ? Color.RED : Color.BLUE);
            font.draw(spriteBatch, String.format("Shield energy %d%%", (int) Math.floor(level.shipState.shieldState)), 25, 70);

            font.setColor(Color.RED);
            if (level.shipState.engineOverloaded) {
                font.draw(spriteBatch, String.format(String.format("Engine blows up in %.1f", level.shipState.engineBlowupTimer)), 10, Main.HEIGHT - 50);
            } else if (level.gameEvents.timeToNextAsteroid > 0) {
                font.draw(spriteBatch, String.format(String.format("Asteroid hit in %.1f", level.gameEvents.timeToNextAsteroid)), 10, Main.HEIGHT - 50);
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
                font.setColor(1, 1, 1, warningMessage.time < WARNING_DURATION - fadeDelay ? 1f : (MESSAGE_DURATION - warningMessage.time) / fadeDelay);
                Util.drawTextCentered(spriteBatch, font, warningMessage.message, Main.WIDTH / 2f, Main.HEIGHT * 0.8f);
            }

            if (level.player.currentInteractable != null && level.player.currentInteractable.getHoverMessage() != null) {
                font.setColor(Color.WHITE);
                font.getData().setScale(1.5f);
                Util.drawTextCentered(spriteBatch, font, level.player.currentInteractable.getHoverMessage(), Main.WIDTH / 2f, Main.HEIGHT * 0.7f);
            }
        }

        if (level.gameOver) {
            if (!level.gameWon) {
                font.setColor(Color.WHITE);
                font.getData().setScale(4f);
                Util.drawTextCentered(spriteBatch, font, "GAME OVER", Main.WIDTH / 2f, Main.HEIGHT * 0.8f);
                font.getData().setScale(2f);
                Util.drawTextCentered(spriteBatch, font, level.gameOverReason, Main.WIDTH / 2f, Main.HEIGHT * 0.2f);
                renderAvatar(spriteBatch);
            } else {
                font.setColor(Color.WHITE);
                font.getData().setScale(4f);
                Util.drawTextCentered(spriteBatch, font, "YOU WIN!", Main.WIDTH / 2f, Main.HEIGHT / 2f);
                font.getData().setScale(2f);
                Util.drawTextCentered(spriteBatch, font, level.gameOverReason, Main.WIDTH / 2f, Main.HEIGHT * 0.4f);
            }
        }
    }

    private void renderAvatar(SpriteBatch spriteBatch) {
        int scaleAvatar = 400;
        Util.affine2.idt();
        Util.affine2.translate((float) Main.WIDTH / 2, (float) Main.HEIGHT /2);
        Util.affine2.scale(this.avatarAnimationFrame == 0 ? -1 : 1, 1);
        Util.affine2.translate(- scaleAvatar / 2f, - scaleAvatar / 2f);

        spriteBatch.draw(Assets.playerGameOver, scaleAvatar, scaleAvatar, Util.affine2);
    }
}
