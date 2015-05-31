package com.jupiter.rogue.Controller;

import com.jupiter.rogue.Model.Creatures.Boss;
import com.jupiter.rogue.Model.Chests.Chest;
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
    private List<Chest> chests;
    private List<ChestController> chestControllers;
    private int currentRoomNbr;

    public MapController(){
        map = Map.getInstance();
        enemyControllers = new ArrayList<EnemyController>();
        chestControllers = new ArrayList<ChestController>();
    }

    public void update() {
        updateEnemyControllers();
        map.update();
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
                    case("boss"): enemyControllers.add(new BossController((Boss) enemies.get(i)));
                        break;
                    default: enemyControllers.add(new WidowController((Widow) enemies.get(i)));
                }
            }
        }
    }

    /**
     * Clears the list chestControllers and updates it with new ChestControllers
     * for the Chest objects in the current room
     */
    public void remakeChestControllers(){
        chestControllers.clear();
        chests = map.getCurrentRoom().getChests();

        if(chests != null) {
            for (int i = 0; i < chests.size(); i++){
                chestControllers.add(new ChestController(chests.get(i)));
            }
        }

    }

    /**
     * Creates bodies for all active Enemy objects.
     */
    public void placeEnemyBodies(){

        if (enemyControllers != null) {
            for (int i = 0; i < enemies.size(); i++) {
                System.out.println("skapad fiende position x: " + enemies.get(i).getX() + ", y:  " + enemies.get(i).getY() );
                enemyControllers.get(i).initBody();
            }
        }
    }
    /**
     * Creates bodies for all Chest objects in the current room.
     */
    public void placeChestBodies(){
        if (chestControllers != null) {
            for (int i = 0; i < chests.size(); i++) {
                chestControllers.get(i).initBody();
            }
        }
    }

}
