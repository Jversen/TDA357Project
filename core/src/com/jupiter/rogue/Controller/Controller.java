package com.jupiter.rogue.Controller;

import com.jupiter.rogue.Model.Creatures.Hero;
import com.jupiter.rogue.Model.Map.Map;

/**
 * Created by Johan on 16/04/15.
 */
public class Controller {

    private HeroController heroController;
    private UserInput inputController;
    private ModelController modelController;

    private Hero hero = Hero.getInstance();
    private Map map = new Map();

    public Controller() {
        init();
    }

    private void init() {
        //TODO initialize everything at start of game
    }

    public void update() {
        inputController.findUserInput();
    }
}
