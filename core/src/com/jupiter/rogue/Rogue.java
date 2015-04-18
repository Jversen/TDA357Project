package com.jupiter.rogue;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.jupiter.rogue.Controller.Controller;
import com.jupiter.rogue.View.View;

public class Rogue extends ApplicationAdapter {
	private Controller controller;
	private View view;

	@Override
	public void create() {

		controller = new Controller();
		view = new View();
	}

	@Override
	public void render() {
		controller.update();
		view.update();
	}
}