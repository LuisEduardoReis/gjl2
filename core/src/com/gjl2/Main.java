package com.gjl2;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class Main extends Game {
	GameScreen gameScreen;

	public static final int WIDTH = 1920;
	public static final int HEIGHT = 1080;
	public static final int TILE_SIZE = 16;

	public static boolean DEBUG = false;

	public static final int FPS = 60;

	@Override
	public void create () {
		Assets.createAssets();

		this.gameScreen = new GameScreen();

		this.setScreen(this.gameScreen);
	}

	@Override
	public void render() {
		super.render();
		if (Gdx.input.isKeyJustPressed(Input.Keys.F1)) {
			DEBUG = !DEBUG;
		}
	}
}
