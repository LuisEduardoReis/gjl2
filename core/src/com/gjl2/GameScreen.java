package com.gjl2;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameScreen extends ScreenAdapter {

    SpriteBatch spriteBatch;
    ShapeRenderer shapeRenderer;
    Level level;
    Hud hud;

    OrthographicCamera camera;
    Viewport viewport;

    float cameraScale = 12f / Main.WIDTH;


    GameScreen() {
        this.spriteBatch = new SpriteBatch();
        this.shapeRenderer = new ShapeRenderer();

        this.level = new Level(this);
        this.hud = new Hud(this);
        this.camera = new OrthographicCamera(Main.WIDTH, Main.HEIGHT);
        this.viewport = new FitViewport(Main.WIDTH, Main.HEIGHT, camera);
    }

    @Override
    public void render(float delta) {
        delta = Math.min(2 * 1.0f / Main.FPS, delta); // que lixo
        this.hud.update(delta);
        this.level.update(delta);

        ScreenUtils.clear(0,0,0,1);
        this.viewport.apply();
        this.camera.position.set(level.player.x, level.player.y, 0);
        this.camera.zoom = cameraScale;
        this.camera.update();

        this.spriteBatch.setProjectionMatrix(camera.combined);
        this.shapeRenderer.setProjectionMatrix(camera.combined);

        this.spriteBatch.begin();
        this.level.renderSprites(this.spriteBatch);
        this.spriteBatch.end();

        this.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        this.level.renderShapes(this.shapeRenderer);
        this.shapeRenderer.end();

        if (Main.DEBUG) {
            this.shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            this.level.renderDebug(shapeRenderer);
            this.shapeRenderer.end();
        }

        this.camera.position.set(Main.WIDTH/2f,Main.HEIGHT/2f,0);
        this.camera.zoom = 1;
        this.camera.update();
        this.spriteBatch.setProjectionMatrix(camera.combined);
        this.shapeRenderer.setProjectionMatrix(camera.combined);

        this.spriteBatch.begin();
        this.hud.renderSprites(spriteBatch);
        this.spriteBatch.end();

        Util.enableBlending();
        this.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        this.hud.renderShapes(shapeRenderer);
        this.shapeRenderer.end();

        this.spriteBatch.begin();
        this.hud.renderOverlay(spriteBatch);
        this.spriteBatch.end();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);

        this.viewport.update(width, height);
    }

    @Override
    public void dispose() {
        super.dispose();

        this.spriteBatch.dispose();
        this.shapeRenderer.dispose();
    }

    public void playSound(Sound sound) {
        if (!level.gameOver) sound.play(Main.VOLUME);
    }
}
