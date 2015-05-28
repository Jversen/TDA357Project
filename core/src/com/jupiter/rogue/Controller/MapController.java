package com.jupiter.rogue.Controller;

import com.jupiter.rogue.Model.Creatures.Enemy;
import com.jupiter.rogue.Model.Creatures.RedDeath;
import com.jupiter.rogue.Model.Creatures.Widow;
import com.jupiter.rogue.Model.Map.Map;
import com.jupiter.rogue.Model.Map.Room;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Johan on 06/05/15.
 */
@lombok.Data
public class MapController {

    private Map map;
    private ArrayList<Room> rooms;
    private List<Enemy> enemies;
    private List<EnemyController> enemyControllers;
    private int currentRoomNbr;

    public MapController(){
        map = Map.getInstance();
        enemyControllers = new ArrayList<EnemyController>();
    }

    public void update() {
        map.update();
        updateEnemyControllers();
    }

    /**
     * Updates all the individual enemies in the current room, also removes them if they die.
     */
    //TODO examine this method
    private void updateEnemyControllers(){

        for (int i = 0; i < enemyControllers.size(); i++) {
            EnemyController enemyController = enemyControllers.get(i);
            if (!enemyController.getEnemy().isCreatureDead()) {
                enemyController.update();
            } else {
                enemyController.getBody().setUserData("dead");
                enemyControllers.remove(i);
            }
        }
    }

    /**
     * Clears the list enemyControllers and updates it with new EnemyControllers
     * for the Enemy objects in the current room
     */
    public void remakeEnemyControllers(){
        String enemyType;
        enemyControllers.clear();
        enemies = map.getCurrentRoom().getEnemies();

        if(enemies != null) {
            for (int i = 0; i < enemies.size(); i++){
                enemyType = enemies.get(i).getEnemyType();
                switch (enemyType){
                    case("widow"): enemyControllers.add(new WidowController((Widow) enemies.get(i)));
                        break;
                    case("redDeath"): enemyControllers.add(new RedDeathController((RedDeath) enemies.get((i))));
                        break;
                }
            }
        }
    }

    /**
     * Creates bodies for all active Enemy objects.
     */
    public void placeEnemyBodies(){

        if (enemyControllers != null) {
            for (int i = 0; i < enemies.size(); i++) {
                enemyControllers.get(i).initBody();
            }
        }
    }
}
