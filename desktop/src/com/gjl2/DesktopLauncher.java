package com.gjl2;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.gjl2.Main;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(Main.FPS);
		config.setTitle("My GDX Game");
		config.setWindowedMode(Main.WIDTH / 2,Main.HEIGHT / 2);
		new Lwjgl3Application(new Main(), config);
	}
}
