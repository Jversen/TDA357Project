package com.jupiter.rogue.Model.Map;

import com.badlogic.gdx.physics.box2d.Body;
import com.jupiter.rogue.Model.Creatures.Hero;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Johan on 16/04/15.
 */
public class Map {

    private static Map map = null;

    private ArrayList<Room> rooms;
    private HashMap<RoomExit, RoomExit> exitMap = new HashMap<>();
    private Room room;
    private Room room2;
    private Hero hero = Hero.getInstance();
    private int currentRoomNbr; //Variable to track what room the hero is currently in.


    private Map() {
        currentRoomNbr = 0;
        room = new Room();
        room.initRoom();
        room2 = new Room();
        rooms = new ArrayList<Room>();
        rooms.add(room);

        createRooms();
    }

    public static Map getInstance() {
        if(map == null) {
            map = new Map();
        }
        return map;
    }

    /**
     * Method to create the initial map, set up the rooms and their interconnectivity
     */
    private void createRooms() {
        //This method has to be completely rewritten once we move on to randomly placed rooms
        for(int i = 0; i < 2; i++) {
            room = new Room();
            //TODO add exit connectivity code here once proper tmx-files have been added
            //Going to be a lot of waste
            rooms.add(room);
        }
    }

    public void changeRoom(int exit, int room) {
        RoomExit newRoom = getNewRoom(exit, room);
        currentRoomNbr = newRoom.getRoomID();
        rebuildWorld();
        setHeroPosition();
    }

    private RoomExit getNewRoom(int exit, int room) {
        return exitMap.get(new RoomExit(exit, room));
    }

    private void setHeroPosition() {

    }

    public void switchRoom(String doorID) {

    }

    private void rebuildWorld() {
        rooms.get(currentRoomNbr).createTileBodies();
    }

    private void destroyWorld() {
        for(Body body : rooms.get(currentRoomNbr).getBodies() ){
            WorldHolder.getInstance().getWorld().destroyBody(body);
        }
    }

    public Room getCurrentRoom() {
        return rooms.get(currentRoomNbr);
    }
}
