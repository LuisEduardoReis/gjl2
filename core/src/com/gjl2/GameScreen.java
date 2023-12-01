package com.gjl2;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
public class GameScreen extends ScreenAdapter {

    SpriteBatch spriteBatch;
    ShapeRenderer shapeRenderer;
    Level level;

    OrthographicCamera camera;
    Viewport viewport;


    GameScreen() {
        this.spriteBatch = new SpriteBatch();
        this.shapeRenderer = new ShapeRenderer();

        this.level = new Level();
        this.camera = new OrthographicCamera(Main.WIDTH, Main.HEIGHT);
        this.viewport = new FitViewport(Main.WIDTH, Main.HEIGHT, camera);
    }

    @Override
    public void render(float delta) {
        delta = Math.min(2 * 1.0f / Main.FPS, delta); // que lixo
        this.level.update(delta);

        ScreenUtils.clear(0,0,0,1);

        this.viewport.apply();
        this.camera.position.set(level.player.x, level.player.y, 0);
        this.camera.zoom = (float) 12 / Main.WIDTH;
        this.camera.update();

        this.spriteBatch.setProjectionMatrix(camera.combined);
        this.shapeRenderer.setProjectionMatrix(camera.combined);

        this.spriteBatch.begin();
        this.level.renderSprites(this.spriteBatch);
        this.spriteBatch.end();

        this.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        this.level.renderShapes(this.shapeRenderer);
        this.shapeRenderer.end();


        this.camera.position.set(Main.WIDTH/2f,Main.HEIGHT/2f,0);
        this.camera.zoom = 1;
        this.camera.update();
        this.spriteBatch.setProjectionMatrix(camera.combined);
        BitmapFont font = Assets.font;
        font.setColor(Color.GREEN);
        font.getData().setScale(2f);
        this.spriteBatch.begin();
        Util.drawTextCentered(spriteBatch, font, "Hull status 100/100\nOxygen level 100%\nShield level 3", 365,100);
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
}
