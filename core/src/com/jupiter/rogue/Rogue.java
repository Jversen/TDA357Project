package com.jupiter.rogue;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.jupiter.rogue.Model.Creatures.Hero;

public class Rogue extends ApplicationAdapter {

	private SpriteBatch batch;
	private TextureAtlas textureAtlas;
	private Animation animation;
	private float elapsedTime = 0;

	@Override
	public void create() {
		Hero hero = Hero.getInstance();

		batch = new SpriteBatch();
		textureAtlas = new TextureAtlas(Gdx.files.internal("data/spritesheet.atlas"));
		animation = new Animation(1/15f, textureAtlas.getRegions());

	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);                        // #14
		batch.begin();
		//sprite.draw(batch);
		elapsedTime += Gdx.graphics.getDeltaTime();
		batch.draw(animation.getKeyFrame(elapsedTime, true), 0, 0);
		batch.end();
	}
}
