package com.jupiter.rogue.Controller;

/**
 * Created by hilden on 2015-04-16.
 */

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.jupiter.rogue.Model.Enums.Direction;

import java.util.ArrayList;

public class UserInput {
    boolean leftPressed;
    boolean rightPressed;
    boolean spacePressed;

    public UserInput() {

    }


    public ArrayList<Integer> findUserInput() {

        spacePressed = Gdx.input.isKeyPressed(Input.Keys.SPACE);
        leftPressed = Gdx.input.isKeyPressed(Input.Keys.LEFT);
        rightPressed = Gdx.input.isKeyPressed(Input.Keys.RIGHT);

        ArrayList<Integer> keys = new ArrayList();


        if (spacePressed) {
            keys.add(Input.Keys.SPACE);
            System.out.println("Space");
        }
        if (leftPressed) {
            keys.add(Input.Keys.LEFT);
            System.out.println("Left");
        }
        if (rightPressed) {
            keys.add(Input.Keys.RIGHT);
            System.out.println("Right");
        }


        return keys;
    }
}