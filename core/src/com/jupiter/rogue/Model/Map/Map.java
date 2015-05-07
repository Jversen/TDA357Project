package com.jupiter.rogue.Model.Map;

import com.badlogic.gdx.physics.box2d.Body;
import com.jupiter.rogue.Model.Creatures.Hero;
import com.jupiter.rogue.Utils.TiledHandler;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Johan on 16/04/15.
 */
public class Map {

    private static Map map = null;

    private ArrayList<Room> rooms;
    private int[][] roomMap;
    private HashMap<RoomExit, RoomExit> exitMap = new HashMap<>();
    private int currentRoomNbr; //Variable to track what room the hero is currently in.
    private String exit;
    private boolean destroyRoom = false;


    private Map() {
        currentRoomNbr = 0;
        Room room = RoomFactory.getRoom(1,1, "test", null);
        room.initRoom();
        Room room2 = new Room("Rooms/RoomSwitchingTest2.tmx");
        rooms = new ArrayList<Room>();
        rooms.add(room);
        rooms.add(room2);

        //createRooms();
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
    /*private void createRooms() {
        //This method has to be completely rewritten once we move on to randomly placed rooms
        for(int i = 0; i < 2; i++) {
            room = new Room();
            //TODO add exit connectivity code here once proper tmx-files have been added
            //Going to be a lot of waste
            rooms.add(room);
        }
    }*/

    public void update() {
        if(destroyRoom) {
            switchRoom();
        }
    }

    private RoomExit getNewRoom(int exit, int room) {
        return exitMap.get(new RoomExit(exit, room));
    }

    public void createMap() {
        //startingRoom = RoomFactory
    }

    //temporary, will be rewritten
    public void switchRoom() {
        destroyWorld();
        changeActiveRoom();
        rebuildWorld();
        destroyRoom = false;

    }

    private void changeActiveRoom() {
        if(currentRoomNbr == 0) {
            currentRoomNbr = 1;
        } else {
            currentRoomNbr = 0;
        }
        System.out.println("currentRoom: " + currentRoomNbr);
    }

    private void destroyWorld() {
        rooms.get(currentRoomNbr).getTiledHandler().destroy();
    }

    private void rebuildWorld() {
        rooms.get(currentRoomNbr).initRoom();
        if(exit.equals("leftDoor")) {
            rooms.get(currentRoomNbr).getTiledHandler().setHeroPosition("right");
        } else if(exit.equals("rightDoor")) {
            rooms.get(currentRoomNbr).getTiledHandler().setHeroPosition("left");
        }
    }

    public Room getCurrentRoom() {
        return rooms.get(currentRoomNbr);
    }

    public void flagRoomForDestruction(String door) {
        this.exit = door;
        this.destroyRoom = true;
    }
}
