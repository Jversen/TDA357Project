package com.jupiter.rogue.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.jupiter.rogue.Rogue;

@lombok.Data
public class DesktopLauncher {
	public static void main (String[] arg) {

	// 	TexturePacker.process("Data//EnemyAnimations//Boss//BossIdle", "Data//EnemyAnimations//Boss//BossIdle", "BossIdle");  //G�ra Atlasfil
	//	TexturePacker.process("Data//EnemyAnimations//Boss//BossAttack1", "Data//EnemyAnimations//Boss//BossAttack1", "BossAttack1");
	//	TexturePacker.process("Data//EnemyAnimations//Boss//BossAttack2", "Data//EnemyAnimations//Boss//BossAttack2", "BossAttack2");

		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 320;
        config.height = 180;
		new LwjglApplication(new Rogue(), config);
	}
}
