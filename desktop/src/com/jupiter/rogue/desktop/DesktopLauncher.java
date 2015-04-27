package com.jupiter.rogue.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.jupiter.rogue.Rogue;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 384;
        config.height = 216;
		new LwjglApplication(new Rogue(), config);
	}
}
