package com.jupiter.rogue.Controller;

import com.badlogic.gdx.physics.box2d.Body;
import com.jupiter.rogue.Model.Creatures.Enemy;
import com.jupiter.rogue.Model.Map.Map;
import com.jupiter.rogue.Model.Map.Room;
import com.jupiter.rogue.Utils.WorldConstants;

import java.util.ArrayList;

import static com.jupiter.rogue.Utils.WorldConstants.BODIES;
import static com.jupiter.rogue.Utils.WorldConstants.PPM;

/**
 * Created by Johan on 06/05/15.
 */
public class MapController {

    Map map;
    ArrayList<Room> rooms;
    int currentRoomNbr;

    public MapController(){
        map = Map.getInstance();
    }

    public void update() {
        map.update();
    }
}
