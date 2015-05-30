package com.jupiter.rogue;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.jupiter.rogue.Controller.Controller;
import com.jupiter.rogue.View.MainMenuScreen;
import com.jupiter.rogue.View.View;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;


public class Rogue extends Game implements PropertyChangeListener{
	private Controller controller;
    private boolean gameStarted;

    @Override
    public void create() {
        MainMenuScreen mainMenuScreen = new MainMenuScreen();
        mainMenuScreen.addObserver(this);
        setScreen(mainMenuScreen);
        gameStarted = false;
    }

	@Override
	public void render() {
        if(gameStarted) {
		    controller.update();
        } else {
            getScreen().render(1/30f);
        }
	}

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(evt.getPropertyName().equals("setGameScreen")) {
            controller = Controller.getInstance();
            setScreen(controller.getView());
            gameStarted = true;
        } else if(evt.getPropertyName().equals("exitGame")) {
            Gdx.app.exit();
        }

    }
}
