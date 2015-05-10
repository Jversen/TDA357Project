package com.jupiter.rogue.Controller;

/**
 * Created by Johan on 2015-05-10.
 */
public class EnemyFactory {
    EnemyController enemyController;

    public EnemyController createEnemy(String enemyType, int xPos, int yPos){

        switch (enemyType){
            case "widow":
                enemyController = new WidowController(xPos, yPos);
                return enemyController;
            default:
                System.out.println("not valid");
                return null;
        }
    }
}
