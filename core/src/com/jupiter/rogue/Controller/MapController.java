package com.jupiter.rogue.Controller;

import com.jupiter.rogue.Model.Map.Map;
import com.jupiter.rogue.Model.Map.Room;
import java.util.ArrayList;

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
