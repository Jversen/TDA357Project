package com.jupiter.rogue.Controller;

/**
 * Created by Johan on 16/04/15.
 */

@lombok.Data
public class Controller {

    //Singleton instance
    private static Controller instance = null;

    private HeroController heroController;
    private WorldController worldController;
    private UserInput userInput;
    private MapController mapController;

    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }

    private Controller() {
        initController();

    }

    private void initController() {
        heroController = new HeroController();
        worldController = new WorldController();
        userInput = new UserInput();
        mapController = new MapController();
        //TODO initialize everything at start of game
    }

    public void update() {
        worldController.update();
        mapController.update();
        heroController.update(userInput.findUserInput());

        //TODO add rest of controllers
    }
}
