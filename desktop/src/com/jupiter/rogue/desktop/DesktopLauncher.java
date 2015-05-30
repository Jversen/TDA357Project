package com.jupiter.rogue.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.jupiter.rogue.Rogue;

@lombok.Data
public class DesktopLauncher {
	public static void main (String[] arg) {

	 	//TexturePacker.process("Data//HeroAnimations//HeroRanged", "Data//HeroAnimations//HeroRanged", "ranged");  //G�ra Atlasfil

		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 320;
        config.height = 180;
		new LwjglApplication(new Rogue(), config);
	}
}
