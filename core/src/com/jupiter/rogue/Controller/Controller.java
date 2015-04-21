package com.jupiter.rogue.Controller;

import com.jupiter.rogue.Model.Creatures.Hero;
import com.jupiter.rogue.Model.Map.Map;

/**
 * Created by Johan on 16/04/15.
 */
public class Controller {

    private HeroController heroController;
    private WorldController worldController;
    private UserInput userInput;
    private ModelController modelController;
    private AIController aiController;

    private Hero hero = Hero.getInstance();
    private Map map = new Map();

    public Controller() {
        init();
    }

    private void init() {
        heroController = new HeroController();
        worldController = new WorldController();
        userInput = new UserInput();
        modelController = new ModelController();
        aiController = new AIController();

        heroController.initHero();
        //TODO initialize everything at start of game
    }

    public void update() {
        worldController.update();
        heroController.update(userInput.findUserInput());



        //TODO add rest of controllers
    }
}
