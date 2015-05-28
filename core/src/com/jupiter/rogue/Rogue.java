package com.jupiter.rogue;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.jupiter.rogue.Controller.Controller;
import com.jupiter.rogue.View.Hud;
import com.jupiter.rogue.View.View;


public class Rogue extends ApplicationAdapter {
	private Controller controller;

    @Override
	public void create() {
		controller = Controller.getInstance();
	}

	@Override
	public void render() {
		controller.update();
	}

	public void dispose(){
	}
}
