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
	private View view;
	private Stage stage; //Scene2d stage

    @Override
	public void create() {
		controller = Controller.getInstance();
		view = new View();
		stage = new Stage(new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
		Hud hud = Hud.getInstance();

		stage.addActor(hud);
	}

	@Override
	public void render() {
		controller.update();
		view.update();
		stage.draw();
	}

	public void dispose(){
		stage.dispose();
	}
}
