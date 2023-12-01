package com.gjl2;

import com.badlogic.gdx.Game;

public class Main extends Game {
	GameScreen gameScreen;

	public static final int WIDTH = 1920;
	public static final int HEIGHT = 1080;
	public static final int TILE_SIZE = 16;

	public static final int FPS = 60;

	@Override
	public void create () {
		Assets.createAssets();

		this.gameScreen = new GameScreen();

		this.setScreen(this.gameScreen);
	}
}
