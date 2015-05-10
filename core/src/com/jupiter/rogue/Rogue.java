package com.jupiter.rogue;

import com.badlogic.gdx.ApplicationAdapter;
import com.jupiter.rogue.Controller.Controller;
import com.jupiter.rogue.View.View;


public class Rogue extends ApplicationAdapter {
	private Controller controller;
	private View view;

    @Override
	public void create() {
		controller = Controller.getInstance();
		view = new View();
	}

	@Override
	public void render() {
		controller.update();
		view.update();
	}
}
