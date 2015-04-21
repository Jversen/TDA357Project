package com.jupiter.rogue.Model.World;

import com.jupiter.rogue.Model.Creatures.Hero;

import java.util.ArrayList;

/**
 * Created by Johan on 16/04/15.
 */
public class Map {

    private ArrayList<Room> rooms;
    private Room room;
    private Hero hero = Hero.getInstance();
    private int currentRoomNbr; //Variable to track what room the hero is currently in.

    public Map() {
        currentRoomNbr = 0;
        room = new Room();
        rooms = new ArrayList<Room>();
        rooms.add(room);
    }

    public void setCurrentRoomNbr(int currentRoomNbr) {
        this.currentRoomNbr = currentRoomNbr;
    }

    public int getCurrentRoomNbr() {
        return currentRoomNbr;
    }

    public Room getCurrentRoom() {
        return rooms.get(currentRoomNbr);
    }
}
