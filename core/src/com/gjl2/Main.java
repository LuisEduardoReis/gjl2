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
	public static boolean MUTE = false;
	public static int VOLUME = 1;
	public static int LAST_VOLUME = 1;

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
		if (Gdx.input.isKeyJustPressed(Input.Keys.F2)) {
			MUTE = !MUTE;
		}
		if (MUTE){
			if (VOLUME != 0)
				LAST_VOLUME = VOLUME;
			VOLUME = 0;

		}else {
			VOLUME = LAST_VOLUME;
		}
		Assets.menuMusic.setVolume(VOLUME);
	}
}
