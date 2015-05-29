package com.jupiter.rogue.Controller;

/**
 * Created by hilden on 2015-04-16.
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.jupiter.rogue.View.View;

import java.util.ArrayList;

public class UserInput {
    private boolean leftPressed;
    private boolean rightPressed;
    private boolean spacePressed;
    private boolean ePressed;
    private boolean wPressed;
    private boolean f1Pressed;

    public UserInput() {
    }

    public ArrayList<Integer> findUserInput() {

        spacePressed = Gdx.input.isKeyPressed(Input.Keys.SPACE);
        leftPressed = Gdx.input.isKeyPressed(Input.Keys.LEFT);
        rightPressed = Gdx.input.isKeyPressed(Input.Keys.RIGHT);
        ePressed = Gdx.input.isKeyPressed(Input.Keys.E);
        wPressed = Gdx.input.isKeyPressed(Input.Keys.W);

        f1Pressed = Gdx.input.isKeyJustPressed(Input.Keys.F1);

        ArrayList<Integer> keys = new ArrayList();

        if (spacePressed) {
            keys.add(Input.Keys.SPACE);
        }
        if (leftPressed) {
            keys.add(Input.Keys.LEFT);
        }
        if (rightPressed) {
            keys.add(Input.Keys.RIGHT);
        }
        if (ePressed) {
            keys.add(Input.Keys.E);
        }
        if (wPressed) {
            keys.add(Input.Keys.W);
        }
        if(f1Pressed) {
            View.getInstance().setShowDebugInfo(!View.getInstance().getShowDebugInfo());
        }
        return keys;
    }
}