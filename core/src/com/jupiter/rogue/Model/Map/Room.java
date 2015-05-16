package com.jupiter.rogue.Model.Map;

import com.jupiter.rogue.Model.Creatures.Enemy;
import com.jupiter.rogue.Utils.TiledHandler;
import lombok.Data;


import java.util.ArrayList;

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

    public Room(String path, int width, int height, ArrayList<String> doors) {
        this.path = path;
        this.WIDTH = width;
        this.HEIGHT = height;
        this.doors = doors;
        //TEMPORARY, JUST FOR TESTING

    }

    public void initRoom() {
        tiledHandler = new TiledHandler(path);
        tiledHandler.initRoom();
    }
}
