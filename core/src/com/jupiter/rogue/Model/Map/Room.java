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
    private ArrayList<Door> doors = new ArrayList<>();
    private String path;
    private TiledHandler tiledHandler;

    public Room(String path) {
        this.path = path;

        //TEMPORARY, JUST FOR TESTING

    }

    public void initRoom() {
        tiledHandler = new TiledHandler(path);
        tiledHandler.initRoom();
    }
}
