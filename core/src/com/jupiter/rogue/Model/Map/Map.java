package com.jupiter.rogue.Model.Map;

import com.jupiter.rogue.Utils.WorldConstants;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Johan on 16/04/15.
 */
public class Map {

    private static Map map = null;

    private ArrayList<Room> rooms;
    private int[][] roomMap = new int[100][100]; // TODO change to proper values
    private HashMap<RoomExit, RoomExit> exitMap = new HashMap<>();
    private int currentRoomNbr; //Variable to track what room the hero is currently in.
    private int currentRoomX;
    private int currentRoomY;
    private String exit;
    private boolean destroyRoom = false;


    private Map() {
        currentRoomNbr = 0;
        ArrayList<String> doors= new ArrayList<>();
        doors.add("l1");
        doors.add("r1");
        Room room = new Room("Rooms/RoomSwitchingTest1.tmx", 4, 2, doors);
        room.initRoom();
        Room room2 = new Room("Rooms/RoomSwitchingTest2.tmx", 4, 2, doors);
        rooms = new ArrayList<Room>();
        rooms.add(room);
        rooms.add(room2);

        //createMap();
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
        initMap();
        Room startingRoom = RoomFactory.getRoom("StartingRoom");
        currentRoomNbr = 0;
        addRoom(startingRoom, 0, 50, currentRoomNbr);
        currentRoomX = 0;
        currentRoomY = 50;

        for(int roomsRemaining = WorldConstants.MAP_SIZE; roomsRemaining > 0; roomsRemaining--) {
            ArrayList<String> doors = getCurrentRoom().getDoors();
            int left = 0;
            int right = 0;
            int top = 0;
            int bottom = 0;

            for(String door : doors) {
                String side = door.substring(0,1);
                switch (side) {
                    case "l" :  left += 1;
                                break;
                    case "r" :  right += 1;
                                break;
                    case "t" :  top += 1;
                                break;
                    case "b" :  bottom += 1;
                                break;
                    default:    break;
                }
            }

            if(left == 1) {
                boolean notAdded = true;
                currentRoomNbr += 1;
                while(notAdded) {
                    Room leftRoom = RoomFactory.getRoom("r", false);
                    int cellNr = getCellNr(getCurrentRoom(), "l");

                    int doorX = currentRoomX - leftRoom.getWIDTH();
                    int doorY = currentRoomY + cellNr - getCellNr(leftRoom,"r");

                    if(leftRoomFits(leftRoom, doorX, doorY)) {
                        addRoom(leftRoom, doorX, doorY, currentRoomNbr);
                        notAdded = false;
                    }
                }
            }

            if(right == 1) {
                boolean notAdded = true;
                currentRoomNbr += 1;
                while(notAdded) {
                    Room rightRoom = RoomFactory.getRoom("l", false);

                    int cellNr = getCellNr(getCurrentRoom(), "r");

                    int doorX = currentRoomX + getCurrentRoom().getWIDTH();
                    int doorY = currentRoomY + cellNr - getCellNr(rightRoom,"l");
                    if(leftRoomFits(rightRoom, doorX, doorY)) {
                        addRoom(rightRoom, doorX, doorY, currentRoomNbr);
                        notAdded = false;
                    }
                }
            }

            if(top == 1) {
                boolean notAdded = true;
                currentRoomNbr += 1;
                while(notAdded) {
                    Room topRoom = RoomFactory.getRoom("b", false);

                    int cellNr = getCellNr(getCurrentRoom(), "t");

                    int doorX = currentRoomX + cellNr - getCellNr(topRoom, "b");
                    int doorY = currentRoomY + getCurrentRoom().getWIDTH();
                    if(leftRoomFits(topRoom, doorX, doorY)) {
                        addRoom(topRoom, doorX, doorY, currentRoomNbr);
                        notAdded = false;
                    }
                }
            }

            if(bottom == 1) {
                boolean notAdded = true;
                currentRoomNbr += 1;
                while(notAdded) {
                    Room bottomRoom = RoomFactory.getRoom("t", false);

                    int cellNr = getCellNr(getCurrentRoom(), "b");

                    int doorX = currentRoomX + cellNr - getCellNr(bottomRoom, "t");
                    int doorY = currentRoomY + getCurrentRoom().getWIDTH();

                    if(leftRoomFits(bottomRoom, doorX, doorY)) {
                        addRoom(bottomRoom, doorX, doorY, currentRoomNbr);
                        notAdded = false;
                    }
                }
            }

        }

        currentRoomNbr = 0; // resets the value to the starting room


    }

    private int getCellNr(Room room, String sideToBeFound) {
        ArrayList<String> doors = room.getDoors();
        int cellNr = -1;
        for(String door : doors) {
            String side = door.substring(0,1);
            int cell = Integer.parseInt(door.substring(1));
            if(side.equals(sideToBeFound)) {
                cellNr = cell;
            }
        }
        return cellNr;
    }

    private boolean leftRoomFits(Room room, int doorX, int doorY) {
        for(int x = doorX; x <= x+room.getWIDTH(); x++) {
            for(int y = doorY; y <= y+room.getHEIGHT(); y++) {
                if(roomMap[x][y] != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    private void initMap() {
        for(int x = 0; x < roomMap.length; x++) {
            for(int y = 0; y < roomMap[x].length ; y++) {
                roomMap[x][y] = -1;
            }
        }
    }

    //TEMPORARY
    private void printMap() {
        for(int x = 0; x < roomMap.length; x++) {
            //System.out.println();
            for(int y = 0; y < roomMap[x].length ; y++) {
                //System.out.print(roomMap[x][y] + " ");
            }
        }
    }

    private void addRoom(Room room, int xPos, int yPos, int ID) {
        rooms.add(room);
        for(int x = xPos; x <= xPos+room.getWIDTH(); x++) {
            for(int y = yPos; y <= yPos+room.getHEIGHT(); y++) {
                roomMap[x][y] = ID;
            }
        }
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

        //TODO finish and test the actual method below
        /*String side = exit.substring(0,1);
        int cell = Integer.parseInt(exit.substring(1));

        int newRoomX = 0;
        int newRoomY = 0;

        switch(side) {
            case "l":   newRoomX = getNextRoomX("l");
                        newRoomY = getNextRoomY(cell);
                        break;
            case "b":   newRoomY = getNextRoomY("b");
                        newRoomX = getNextRoomX(cell);
                        break;
            case "r":   newRoomX = getNextRoomX("r");
                        newRoomY = getNextRoomY(cell);
                        break;
            case "t":   newRoomY = getNextRoomY("t");
                        newRoomX = getNextRoomY(cell);
        }

        currentRoomNbr = roomMap[newRoomX][newRoomY];*/
    }

    private int getNextRoomX(String side) {
        if(side.equals("l")) {
            return currentRoomX-1;
        }
        if(side.equals("r")) {
            return currentRoomX + getCurrentRoom().getWIDTH()+currentRoomX + 1;
        }
        return -1;
    }

    private int getNextRoomX(int cell) {
        return currentRoomX + cell;
    }

    private int getNextRoomY(String side) {
        if(side.equals("t")) {
            return currentRoomX + getCurrentRoom().getHEIGHT() +1;
        }
        if(side.equals("b")) {
            return currentRoomY-1;
        }
        return -1;
    }

    private int getNextRoomY(int cell) {
        return currentRoomY + cell;
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
