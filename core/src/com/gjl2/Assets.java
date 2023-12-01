package com.gjl2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import static com.gjl2.Main.TILE_SIZE;

public class Assets {

    static Texture testTexture;
    static Texture tilesheet;
    static Texture spritesheet;

    static TextureRegion[][] tileTextures;
    static TextureRegion[][] spriteTextures;

    public static TextureRegion doorSprite;
    public static TextureRegion playerSprite;
    public static TextureRegion asteroidHitSprite;
    public static TextureRegion toolboxSprite;

    public static BitmapFont font;

    public static void createAssets() {
        font = new BitmapFont(Gdx.files.internal("font.fnt"));
        testTexture = new Texture("badlogic.jpg");
        tilesheet = new Texture("tilesheet.png");
        spritesheet = new Texture("spritesheet.png");

        tileTextures = TextureRegion.split(tilesheet, TILE_SIZE, TILE_SIZE);
        spriteTextures = TextureRegion.split(spritesheet, TILE_SIZE, TILE_SIZE);

        doorSprite = spriteTextures[0][1];
        playerSprite = spriteTextures[0][2];
        asteroidHitSprite = spriteTextures[1][0];
        toolboxSprite = spriteTextures[1][1];
    }

    static TextureRegion getTileTextureById(int id) {
       return tileTextures[id / tileTextures.length][id % tileTextures.length];
    }
}
