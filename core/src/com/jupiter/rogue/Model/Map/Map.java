package com.jupiter.rogue.Model.Map;

import java.util.ArrayList;

/**
 * Created by Johan on 16/04/15.
 */
public class Map {

    private ArrayList<Room> rooms;
    private Room room;

    public Map() {
        room = new Room();
        rooms = new ArrayList<Room>(1);
        rooms.add(room);
    }

    public Room getARoom() {
        return room;
    }
}
