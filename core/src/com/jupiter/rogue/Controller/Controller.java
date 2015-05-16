package com.jupiter.rogue.Controller;

import com.jupiter.rogue.Model.Map.Map;
import com.jupiter.rogue.Utils.EnemyFactory;
import com.jupiter.rogue.Utils.WidowFactory;

import java.util.ArrayList;
import java.util.List;

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
    private RedDeathController redDeathController;
    private MapController mapController;
    private Map map;

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
        redDeathController = new RedDeathController();
        mapController = new MapController();
        map = Map.getInstance();
        
        //TODO initialize everything at start of game
    }

    public void update() {
        worldController.update();
        mapController.update();
        heroController.update(userInput.findUserInput());
        redDeathController.update();


        //TODO add rest of controllers
    }
}
