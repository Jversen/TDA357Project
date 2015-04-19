package com.jupiter.rogue;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.jupiter.rogue.Controller.Controller;
import com.jupiter.rogue.Controller.HeroController;
import com.jupiter.rogue.Model.Creatures.Hero;
import com.jupiter.rogue.View.View;


public class Rogue extends ApplicationAdapter {
	private Controller controller;
	private View view;
	private SpriteBatch batch;
	private Hero hero;
	@Override
	public void create() {
	batch = new SpriteBatch();
		controller = new Controller();
		view = new View();
		hero = hero.getInstance();
	}

	@Override
	public void render() {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		float deltaTime = Gdx.graphics.getDeltaTime();

		controller.update();
		view.update();
		batch.begin();

		batch.draw(hero.updateAnimation(deltaTime), 0, 0);
		batch.end();

	}
}
