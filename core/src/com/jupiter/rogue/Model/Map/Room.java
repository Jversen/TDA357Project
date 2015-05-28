package com.jupiter.rogue.Model.Map;

import com.jupiter.rogue.Controller.EnemyController;
import com.jupiter.rogue.Model.Creatures.Enemy;
import com.jupiter.rogue.Utils.*;
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
    private boolean visited;

    public Room(String path, int width, int height, ArrayList<String> doors) {
        this.path = path;
        this.WIDTH = width;
        this.HEIGHT = height;
        this.doors = doors;
        visited = false;

        tiledHandler = new TiledHandler(path);

        enemyControllers = tiledHandler.getEnemyControllers();

    }

    public void initRoom() {
        tiledHandler.initRoom();
        placeEnemies();
    }

    private void placeEnemies(){

        if (enemyControllers != null) {
            for (int i = 0; i < enemyControllers.size(); i++) {
                enemyControllers.get(i).initBody();
            }
        }
    }

    public boolean getVisited() {
        return visited;
    }
}

