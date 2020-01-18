package com.mystargame.arzek.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mystargame.arzek.StarGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		System.setProperty("user.name","\\xD0\\x94\\xD0\\xBC\\xD0\\xB8\\xD1\\x82\\xD1\\x80\\xD0\\xB8\\xD0\\xB9");
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 400;
		config.height = 600;
		//config.resizable = false;
		new LwjglApplication(new StarGame(), config);
	}
}
