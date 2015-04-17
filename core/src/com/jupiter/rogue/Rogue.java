package com.jupiter.rogue;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.jupiter.rogue.Model.Creatures.Hero;

public class Rogue extends ApplicationAdapter {

	@Override
	public void create() {
		Hero hero = Hero.getInstance();
		walkSheet = new Texture(Gdx.files.internal("sprite-animation4.png")); // #9
		TextureRegion[][] tmp = TextureRegion.split(walkSheet, walkSheet.getWidth()/FRAME_COLS, walkSheet.getHeight()/FRAME_ROWS);              // #10
		walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
		int index = 0;
		for (int i = 0; i < FRAME_ROWS; i++) {
			for (int j = 0; j < FRAME_COLS; j++) {
				walkFrames[index++] = tmp[i][j];
			}
		}
	}

	@Override
	public void render() {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);                        // #14
		stateTime += Gdx.graphics.getDeltaTime();           // #15
		currentFrame = walkAnimation.getKeyFrame(stateTime, true);  // #16
		spriteBatch.begin();
		spriteBatch.draw(currentFrame, 50, 50);             // #17
		spriteBatch.end();
	}
}
