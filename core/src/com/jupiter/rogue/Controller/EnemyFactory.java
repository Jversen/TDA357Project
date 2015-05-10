package com.jupiter.rogue.Controller;

/**
 * Created by Johan on 2015-05-10.
 */
public class EnemyFactory {
    String enemyType;
    float xPos;
    float yPos;
    EnemyController enemyController;

    public EnemyController createEnemy(String enemyType, float xPos, float yPos){
        this.enemyType = enemyType;
        this.xPos = xPos;
        this.yPos = yPos;

        switch (enemyType){
            case "widow":
                System.out.println("widow");
                enemyController = new WidowController();
                return enemyController;
            default:
                System.out.println("not valid");
                return null;
        }
    }
}
