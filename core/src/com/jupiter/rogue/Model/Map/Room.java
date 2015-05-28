package com.jupiter.rogue.Model.Map;

import com.badlogic.gdx.maps.MapProperties;
import com.jupiter.rogue.Controller.EnemyController;
import com.jupiter.rogue.Model.Creatures.Enemy;
import com.jupiter.rogue.Model.Factories.EnemyFactory;
import com.jupiter.rogue.Model.Factories.RedDeathFactory;
import com.jupiter.rogue.Model.Factories.WidowFactory;
import com.jupiter.rogue.Utils.*;
import lombok.Data;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.jupiter.rogue.Utils.WorldConstants.PPM;

/**
 * Created by hilden on 2015-04-16.
 */
@Data
public class Room {
    private ArrayList<String> doors = new ArrayList<>();
    private final int WIDTH;
    private final int HEIGHT;
    private int x;
    private int y;
    private String path;
    private TiledHandler tiledHandler;
    private boolean visited;
    private boolean bossRoom;
    private List<Enemy> enemies;



    public Room(String path, int width, int height, ArrayList<String> doors) {
        this.path = path;
        this.WIDTH = width;
        this.HEIGHT = height;
        this.doors = doors;
        visited = false;
        bossRoom = false;

        tiledHandler = new TiledHandler(path);
        enemies = new ArrayList<Enemy>();
        //enemyControllers = tiledHandler.getEnemyControllers();

    }

    public void initRoom() {
        tiledHandler.initRoom();
    }

    public void generateEnemies(){

        EnemyFactory enemyFactory;
        String enemyType;
        Enemy enemy;
        float xPos;
        float yPos;

        if (tiledHandler.getEnemySpawnLayer() != null) {
            for (int i = 0; i <= tiledHandler.getEnemySpawnLayer().getObjects().getCount()-1; i++) {

                /* checks the (enemy)type of the object. If it's "random", change to a random EnemyFactory.*/
                MapProperties properties = tiledHandler.getEnemySpawnLayer().getObjects().get(i).getProperties();

                enemyType = properties.get("type").toString();

                /* Disregard this: Divides position with PPM, we want to set the BODY's position. */
                xPos = (float) properties.get("x");
                yPos = (float) properties.get("y");

                if (enemyType.equals("random")){
                    enemyType = getRandomEnemyType();
                    //TODO possibly randomize position and level/elite as well.
                }

                /* Creates the chosen enemy factory */
                enemyFactory = createEnemyFactory(enemyType);

                enemy = enemyFactory.createEnemy(xPos, yPos, 1, false);

                enemies.add(enemy);
                System.out.println(enemies.get(i).getEnemyType() + " created.");
            }
        }

    }


    private EnemyFactory createEnemyFactory(String enemyType){
        switch (enemyType){
            case "widow": return new WidowFactory();
            case "redDeath": return new RedDeathFactory();
            default: return new WidowFactory();
        }
    }

    private String getRandomEnemyType(){
        Random rn = new Random();
        int enemyTypeIndex;
        String enemyType;

        enemyTypeIndex = rn.nextInt(WorldConstants.ENEMYTYPES.length);
        enemyType = WorldConstants.ENEMYTYPES[enemyTypeIndex];

        return enemyType;
    }


    public boolean getVisited() {
        return visited;
    }

    public boolean getBossRoom() {
        return bossRoom;
    }

}

