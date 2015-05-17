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
import java.util.Timer;
import java.util.TimerTask;

public class UserInput {
    private boolean leftPressed;
    private boolean rightPressed;
    private boolean spacePressed;
    private boolean ePressed;
    private boolean wPressed;

    private Timer timer;

    //a boolean to track whether or not weaponswap is ready, probably move this to HeroController.
    private boolean swapReady;

    public UserInput() {
        timer = new Timer();
        swapReady = true;
    }

    public ArrayList<Integer> findUserInput() {

        spacePressed = Gdx.input.isKeyPressed(Input.Keys.SPACE);
        leftPressed = Gdx.input.isKeyPressed(Input.Keys.LEFT);
        rightPressed = Gdx.input.isKeyPressed(Input.Keys.RIGHT);
        ePressed = Gdx.input.isKeyPressed(Input.Keys.E);
        wPressed = Gdx.input.isKeyPressed(Input.Keys.W);

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
        if (wPressed && swapReady) {
            swapReady = false;
            timer.schedule(new SwapTask(), 1000);
            keys.add(Input.Keys.W);
        }
        return keys;
    }

    public void setSwapReady() {
        swapReady = true;
    }

    //A nestled class to implement a timertask.
    class SwapTask extends TimerTask {
        public void run() {
            setSwapReady();
        }
    }

}