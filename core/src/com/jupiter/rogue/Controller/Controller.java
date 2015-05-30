package com.jupiter.rogue.Controller;

import com.badlogic.gdx.Screen;
import com.jupiter.rogue.View.View;

/**
 * Created by Johan on 16/04/15.
 */

@lombok.Data
public class Controller{

    //Singleton instance
    private static Controller instance = null;

    private HeroController heroController;
    private WorldController worldController;
    private UserInput userInput;
    private MapController mapController;
    private View view;

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
        worldController = new WorldController();
        heroController = new HeroController();
        userInput = new UserInput();
        mapController = new MapController();
        view = View.getInstance();
        //TODO initialize everything at start of game
    }

    public void update() {
        worldController.update();
        mapController.update();
        heroController.update(userInput.findUserInput());
        newRoomCheck();
        view.update();
    }

    private void newRoomCheck(){
        if(mapController.getMap().isEnteredNewRoom()){
            mapController.remakeEnemyControllers();
            mapController.placeEnemyBodies();
            view.remakeEnemyViews();
            mapController.getMap().setEnteredNewRoom(false);
        }
    }

    private Screen getScreen() {
        return view;
    }
}
