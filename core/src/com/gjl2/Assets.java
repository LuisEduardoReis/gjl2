package com.gjl2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.gjl2.Main.TILE_SIZE;

public class Assets {

    static Texture testTexture;
    static Texture tilesheet;
    static Texture spritesheet;

    static TextureRegion[][] tileTextures;
    static TextureRegion[][] spriteTextures;

    public static TextureRegion doorSprite;
    public static List<TextureRegion> playerMovement = new ArrayList<>(4);
    public static List<TextureRegion> playerClimbing = new ArrayList<>(2);
//    public static TextureRegion playerBack;
    public static TextureRegion asteroidHitSprite;
    public static TextureRegion arrowSprite;
    public static TextureRegion oxygenSprite;
    public static TextureRegion navigationSprite;
    public static TextureRegion starSprite;
    public static TextureRegion alienSprite;

    public static BitmapFont font;

    public static void createAssets() {
        font = new BitmapFont(Gdx.files.internal("font.fnt"));
        testTexture = new Texture("badlogic.jpg");
        tilesheet = new Texture("tilesheet.png");
        spritesheet = new Texture("spritesheet.png");

        tileTextures = TextureRegion.split(tilesheet, TILE_SIZE, TILE_SIZE);
        spriteTextures = TextureRegion.split(spritesheet, TILE_SIZE, TILE_SIZE);

        doorSprite = spriteTextures[0][1];
        playerMovement.add(spriteTextures[0][2]);
        playerMovement.add(spriteTextures[0][3]);
        playerMovement.add(spriteTextures[0][2]);
        playerMovement.add(spriteTextures[0][4]);
        playerClimbing.add(spriteTextures[0][5]);
        playerClimbing.add(spriteTextures[0][6]);
        asteroidHitSprite = spriteTextures[1][0];
        arrowSprite = spriteTextures[1][1];
        oxygenSprite = tileTextures[0][4];
        navigationSprite = tileTextures[0][7];
        starSprite = spriteTextures[1][2];
        alienSprite = spriteTextures[1][4];

        com.badlogic.gdx.audio.Music menuMusic = Gdx.audio.newMusic(Gdx.files.internal("iamnotok.mp3"));
        menuMusic.setLooping(true);
        menuMusic.play();
    }

    static TextureRegion getTileTextureById(int id) {
       return tileTextures[id / tileTextures.length][id % tileTextures.length];
    }
}
