package com.jupiter.rogue;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jupiter.rogue.Controller.Controller;
import com.jupiter.rogue.Model.Creatures.Hero;
import com.jupiter.rogue.Model.Map.WorldConstants;
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
