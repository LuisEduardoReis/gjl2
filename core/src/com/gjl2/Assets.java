package com.gjl2;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import static com.gjl2.Main.TILE_SIZE;

public class Assets {

    static Texture testTexture;
    static Texture tilesheet;

    static TextureRegion[][] tileTextures;

    public static void createAssets() {
        testTexture = new Texture("badlogic.jpg");
        tilesheet = new Texture("tilesheet.png");

        tileTextures = TextureRegion.split(tilesheet, TILE_SIZE, TILE_SIZE);
    }

    static TextureRegion getTileTextureById(int id) {
       return tileTextures[id / tileTextures.length][id % tileTextures.length];
    }
}
