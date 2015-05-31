package com.jupiter.rogue.Controller;

/**
 * Created by hilden on 2015-04-16.
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.jupiter.rogue.Model.Creatures.Hero;
import com.jupiter.rogue.View.View;

import java.util.ArrayList;

public class UserInput {
    private boolean leftPressed;
    private boolean rightPressed;
    private boolean spacePressed;
    private boolean ePressed;
    private boolean wPressed;
    private boolean f1Pressed;
    private boolean nPressed;
    private boolean key1Pressed;
    private boolean key2Pressed;
    private boolean key3Pressed;
    private boolean cPressed;

    public UserInput() {
    }

    public ArrayList<Integer> findUserInput() {

        spacePressed = Gdx.input.isKeyPressed(Input.Keys.SPACE);
        leftPressed = Gdx.input.isKeyPressed(Input.Keys.LEFT);
        rightPressed = Gdx.input.isKeyPressed(Input.Keys.RIGHT);
        ePressed = Gdx.input.isKeyPressed(Input.Keys.E);
        wPressed = Gdx.input.isKeyPressed(Input.Keys.W);
        nPressed = Gdx.input.isKeyJustPressed(Input.Keys.N);
        cPressed = Gdx.input.isKeyPressed(Input.Keys.C);
        f1Pressed = Gdx.input.isKeyJustPressed(Input.Keys.F1);
        key1Pressed = Gdx.input.isKeyJustPressed(Input.Keys.NUM_1);
        key2Pressed = Gdx.input.isKeyJustPressed(Input.Keys.NUM_2);
        key3Pressed = Gdx.input.isKeyJustPressed(Input.Keys.NUM_3);


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
        if(nPressed){
            View.getInstance().showAttributesMenu();
        }
        if(key1Pressed && View.getInstance().isShowAttributeMenu()){
            Hero.getInstance().increaseStats("strength");
        }
        if(key2Pressed && View.getInstance().isShowAttributeMenu()){
            Hero.getInstance().increaseStats("agility");
        }
        if(key3Pressed && View.getInstance().isShowAttributeMenu()){
            Hero.getInstance().increaseStats("intellect");
        }
        if (cPressed) {
            keys.add(Input.Keys.C);
        }
        return keys;
    }
}