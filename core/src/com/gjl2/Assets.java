package com.gjl2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;
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
    public static TextureRegion playerGameOver;
    public static TextureRegion asteroidHitSprite;
    public static TextureRegion arrowSprite;
    public static TextureRegion oxygenSprite;
    public static TextureRegion navigationSprite;
    public static TextureRegion engineSprite;
    public static TextureRegion starSprite;
    public static TextureRegion alienSprite;
    public static TextureRegion alienChunkSprite;
    public static TextureRegion gunSprite;
    public static TextureRegion engineFireSprite;

    public static TextureRegion engineFireSprite1;
    public static TextureRegion engineFireSprite2;
    public static TextureRegion engineFireSprite3;

    public static BitmapFont font;

    public static Sound laserSound;
    public static Sound hitSound;
    public static Sound explosionSound;
    public static Sound alarmSound;
    public static Sound shieldsUpSound;
    public static Sound doorOpenSound;
    public static Sound doorCloseSound;
    public static Sound teleportSound;
    public static com.badlogic.gdx.audio.Music menuMusic;

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
        playerGameOver = spriteTextures[0][7];
        asteroidHitSprite = spriteTextures[1][0];
        arrowSprite = spriteTextures[1][1];
        oxygenSprite = tileTextures[0][4];
        navigationSprite = tileTextures[0][7];
        starSprite = spriteTextures[1][2];
        alienSprite = spriteTextures[1][4];
        alienChunkSprite = spriteTextures[1][5];
        gunSprite = spriteTextures[1][3];
        engineSprite = spriteTextures[1][6];
        engineFireSprite = spriteTextures[1][7];
        engineFireSprite1 = spriteTextures[2][0];
        engineFireSprite2 = spriteTextures[3][0];
        engineFireSprite3 = spriteTextures[4][0];

        menuMusic = Gdx.audio.newMusic(Gdx.files.internal("iamnotok.mp3"));
        menuMusic.setLooping(true);
        menuMusic.play();

        alarmSound = Gdx.audio.newSound(Gdx.files.internal("alarm.wav"));
        laserSound = Gdx.audio.newSound(Gdx.files.internal("laserShoot.wav"));
        hitSound = Gdx.audio.newSound(Gdx.files.internal("hitHurt.wav"));
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("explosion.wav"));
        shieldsUpSound = Gdx.audio.newSound(Gdx.files.internal("shieldsUp.wav"));
        doorOpenSound = Gdx.audio.newSound(Gdx.files.internal("doorOpen.wav"));
        doorCloseSound = Gdx.audio.newSound(Gdx.files.internal("doorClose.wav"));
        teleportSound = Gdx.audio.newSound(Gdx.files.internal("teleport.wav"));
    }

    public static TextureRegion getTileTextureById(int id) {
       return tileTextures[id / tileTextures.length][id % tileTextures.length];
    }
}
