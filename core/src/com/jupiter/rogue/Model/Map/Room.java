package com.jupiter.rogue.Model.Map;

import com.badlogic.gdx.maps.MapProperties;
import com.jupiter.rogue.Controller.EnemyController;
import com.jupiter.rogue.Model.Chests.Chest;
import com.jupiter.rogue.Model.Chests.ChestFactory;
import com.jupiter.rogue.Model.Creatures.Enemy;
import com.jupiter.rogue.Model.Factories.BossFactory;
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
    private List<Chest> chests;



    public Room(String path, int width, int height, ArrayList<String> doors) {
        this.path = path;
        this.WIDTH = width;
        this.HEIGHT = height;
        this.doors = doors;
        visited = false;
        bossRoom = false;

        tiledHandler = new TiledHandler(path);
        enemies = new ArrayList<Enemy>();
        chests = new ArrayList<Chest>();

    }

    /**
     * Initializes the TiledHandler needed to build the room
     */
    public void initRoom() {
        tiledHandler.initRoom();

    }

    public void generateEnemies(){

        EnemyFactory enemyFactory;
        String enemyType;
        Enemy enemy;
        float xPos;
        float yPos;

        if (tiledHandler.getEnemySpawnLayer() != null && !visited) {
            for (int i = 0; i < tiledHandler.getEnemySpawnLayer().getObjects().getCount(); i++) {

                /* checks the (enemy)type of the object. If it's "random", change to a random EnemyFactory.*/
                MapProperties properties = tiledHandler.getEnemySpawnLayer().getObjects().get(i).getProperties();

                enemyType = properties.get("type").toString();

                /* Disregard this: Divides position with PPM, we want to set the BODY's position. */
                xPos = (float) properties.get("x") / PPM;
                yPos = (float) properties.get("y") / PPM;


                if (enemyType.equals("random")){
                    enemyType = getRandomEnemyType();
                    //TODO possibly randomize position and level/elite as well.
                }

                /* Creates the chosen enemy factory */
                enemyFactory = createEnemyFactory(enemyType);

                enemy = enemyFactory.createEnemy(xPos, yPos, 1, false);

                enemies.add(enemy);
            }
        }

    }


    private EnemyFactory createEnemyFactory(String enemyType){
        switch (enemyType){
            case "widow": return new WidowFactory();
            case "redDeath": return new RedDeathFactory();
            case "boss": return new BossFactory();
            default: return new WidowFactory();
        }
    }

    private String getRandomEnemyType(){
        Random rn = new Random();
        int enemyTypeIndex;
        String enemyType;

        enemyTypeIndex = rn.nextInt(WorldConstants.ENEMYTYPES.size());
        enemyType = WorldConstants.ENEMYTYPES.get(enemyTypeIndex);

        return enemyType;
    }

    public void createChests() {

        ChestFactory chestFactory;
        String chestType;
        float xPos;
        float yPos;
        Random rn = new Random();
        int chestTypeIndex;

        if (tiledHandler.getChestLayer() != null && !visited) {
            for (int i = 0; i < tiledHandler.getChestLayer().getObjects().getCount(); i++) {
                MapProperties properties = tiledHandler.getChestLayer().getObjects().get(i).getProperties();
                if (properties.get("type") != null) {
                    chestType = properties.get("type").toString();
                } else {
                    chestType = "random";
                }
                xPos = (float)properties.get("x");
                yPos = (float)properties.get("y");


                if (chestType.equals("random")) {
                    chestTypeIndex = rn.nextInt(WorldConstants.CHESTTYPES.size());
                    chestType = WorldConstants.CHESTTYPES.get(chestTypeIndex);
                }

                chestFactory = new ChestFactory(chestType, xPos, yPos);

                if(rn.nextInt(3) == 0) {
                    chests.add(chestFactory.createChest());
                }
            }
        }
    }

    public boolean getVisited() {
        return visited;
    }

    public boolean getBossRoom() {
        return bossRoom;
    }

}

