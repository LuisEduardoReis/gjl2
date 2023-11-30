package com.gjl2;

import com.badlogic.gdx.Game;

public class Main extends Game {
	GameScreen gameScreen;

	public static final int WIDTH = 1920;
	public static final int HEIGHT = 1080;

	@Override
	public void create () {
		Assets.createAssets();

		this.gameScreen = new GameScreen();

		this.setScreen(this.gameScreen);
	}
}
