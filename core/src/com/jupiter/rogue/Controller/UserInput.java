package com.jupiter.rogue.Controller;

/**
 * Created by hilden on 2015-04-16.
 */

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class UserInput {

    private HeroController heroController;

    public UserInput() {
        heroController = new HeroController();
    }


    public void findUserInput() {

        boolean keyPressed = Gdx.input.isKeyPressed(Input.Keys.ANY_KEY);
        boolean left = Gdx.input.isKeyPressed(Input.Keys.LEFT);
        boolean right = Gdx.input.isKeyPressed(Input.Keys.RIGHT);
        boolean space = Gdx.input.isKeyPressed(Input.Keys.SPACE);

        if (!keyPressed) {
            heroController.relax();
        } else {
            if (left) {
                heroController.walk();
            }
            if (right) {

            }
            if (space) {

            }
        }

    }
}