package com.jupiter.rogue.Model.Map;

import com.jupiter.rogue.Controller.EnemyController;
import com.jupiter.rogue.Model.Creatures.Enemy;
import com.jupiter.rogue.Utils.EnemyFactory;
import com.jupiter.rogue.Utils.TiledHandler;
import com.jupiter.rogue.Utils.WidowFactory;
import com.jupiter.rogue.Utils.WorldConstants;
import lombok.Data;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by hilden on 2015-04-16.
 */
@Data
public class Room {
    private ArrayList<Enemy> enemies = new ArrayList<>();
    private ArrayList<String> doors = new ArrayList<>();
    private final int WIDTH;
    private final int HEIGHT;
    private int x;
    private int y;
    private String path;
    private TiledHandler tiledHandler;
    private List<EnemyController> enemyControllers;
    private int nbrOfEnemies;



    public Room(String path, int width, int height, ArrayList<String> doors) {
        this.path = path;
        this.WIDTH = width;
        this.HEIGHT = height;
        this.doors = doors;
        this.nbrOfEnemies = 3; //arbitrarily chosen number
        enemyControllers = new ArrayList<EnemyController>(); //enemy controllers in this room
        generateEnemies();
        //TEMPORARY, JUST FOR TESTING

    }

    public void initRoom() {
        tiledHandler = new TiledHandler(path);
        tiledHandler.initRoom();
        placeEnemies();
    }

    /* Method to generate the enemies in this room
     */
    private void generateEnemies(){
        Random rn = new Random();
        int enemyType;
        int enemyLevel;
        int xPos;
        int yPos;
        boolean enemyElite;
        EnemyFactory enemyFactory;
        EnemyController enemyController;

        for (int i = 0; i < nbrOfEnemies; i++){

            enemyType = rn.nextInt(WorldConstants.ENEMYTYPES);
            enemyLevel = rn.nextInt(5) + 1;
            enemyElite = rn.nextBoolean();
            xPos = rn.nextInt(250) + 64;
            yPos = rn.nextInt(250) + 64; /* Just randomizes the position between 64-249,
            make dependant on special enemy tiles placed in map editor later.*/
            //System.out.println("creating enemytype " + enemyType + " with lvl: " + enemyLevel + " at pos x: " + xPos + ", y: " + yPos);
            enemyFactory = createEnemyFactory(enemyType);
            enemyController = enemyFactory.createEnemy(xPos, yPos, enemyLevel, enemyElite);

            if (enemyController != null) {
                this.enemyControllers.add(enemyController); //add the created enemy to this room's list of enemies
            }
        }
    }

    private EnemyFactory createEnemyFactory(int enemyType){
        switch (enemyType){
            case 0: return new WidowFactory();
            case 1: return new WidowFactory();  //change this to RedDeathFactory and others later.
            default: return null;
        }
    }

    private void placeEnemies(){

        EnemyController enemyController;

        for (int i = 0; i < enemyControllers.size(); i++){
            enemyController = enemyControllers.get(i);
            enemyController.initBody(); //create enemybodies in the positions of the enemy models
        }
    }
}

