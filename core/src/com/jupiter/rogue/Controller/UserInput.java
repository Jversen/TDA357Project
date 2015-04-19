package com.jupiter.rogue.Controller;

/**
 * Created by hilden on 2015-04-16.
 */

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.jupiter.rogue.Model.Enums.Direction;

public class UserInput {

    private HeroController heroController;
    boolean anyKeyPressed;
    boolean leftPressed;
    boolean rightPressed;
    boolean spacePressed;

    public UserInput() {
        heroController = new HeroController();
        anyKeyPressed = Gdx.input.isKeyPressed(Input.Keys.ANY_KEY);
        leftPressed = Gdx.input.isKeyPressed(Input.Keys.LEFT);
        rightPressed = Gdx.input.isKeyPressed(Input.Keys.RIGHT);
        spacePressed = Gdx.input.isKeyPressed(Input.Keys.SPACE);
    }


    public void findUserInput() {

        if (!anyKeyPressed) {
            heroController.relax();
        } else {
            if (leftPressed) {
                heroController.walk(Direction.LEFT);
            }
            if (rightPressed) {
                heroController.walk(Direction.RIGHT);
            }
            if (spacePressed) {
                heroController.jumpIfPossible();
            }
        }
    }
}