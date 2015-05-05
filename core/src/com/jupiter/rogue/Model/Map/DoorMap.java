package com.jupiter.rogue.Model.Map;

import java.util.HashMap;

/**
 * Created by Johan on 05/05/15.
 */
public class DoorMap {
    private final HashMap<Door, Door> map;
    public DoorMap(Door doorOne, Door doorTwo) {
        map = new HashMap<>();
        map.put(doorOne,doorTwo);
    }
}
