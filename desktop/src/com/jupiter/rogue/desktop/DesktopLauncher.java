package com.jupiter.rogue.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.jupiter.rogue.Rogue;

public class DesktopLauncher {
	public static void main (String[] arg) {

		//TexturePacker.process("Data//HeroRunning", "Data//HeroRunning", "HeroRunningRight");  //Göra Atlasfil

		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 384;
        config.height = 216;
		new LwjglApplication(new Rogue(), config);
	}
}
