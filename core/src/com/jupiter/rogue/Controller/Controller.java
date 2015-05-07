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
    private MapController mapController;

    public Controller() {
        init();
    }

    private void init() {
        heroController = new HeroController();
        worldController = new WorldController();
        userInput = new UserInput();
        modelController = new ModelController();
        aiController = new AIController();
        mapController = new MapController();
        //TODO initialize everything at start of game
    }

    public void update() {
        worldController.update();
        mapController.update();
        heroController.update(userInput.findUserInput());
        //AIController.redDeath2.update();
        AIController.redDeath1.update();


        //TODO add rest of controllers
    }
}
