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
    private String entrance;
    private boolean destroyRoom = false;


    private Map() {
        rooms = new ArrayList<Room>();
        /*currentRoomNbr = 0;
        ArrayList<String> doors= new ArrayList<>();
        doors.add("l1");
        doors.add("r1");
        Room room = new Room("Rooms/RoomSwitchingTest1.tmx", 4, 2, doors);
        room.initRoom();
        Room room2 = new Room("Rooms/RoomSwitchingTest2.tmx", 4, 2, doors);
        rooms = new ArrayList<Room>();
        rooms.add(room);
        rooms.add(room2);*/

        createMap();
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
        printMap();
        currentRoomX = 0;
        currentRoomY = 50;

        String entrance;
        String entranceSide = "l";
        boolean moreRooms = true;
        System.out.println();
        System.out.println("for loop");
        for(int roomsRemaining = WorldConstants.MAP_SIZE-1; roomsRemaining > 0; roomsRemaining--) {
            System.out.println("Current room number: " + currentRoomNbr);
            System.out.println("Current room x: " + currentRoomX);
            System.out.println("Current Room y: " + currentRoomY);

            System.out.println();
            System.out.println(roomsRemaining);
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
            System.out.println("l: " + left);
            System.out.println("r: " + right);
            System.out.println("t: " + top);
            System.out.println("b: " + bottom);

            if(left == 1 && !entranceSide.equals("l")) {
                boolean notAdded = true;
                while(notAdded) {
                    System.out.println("leftroom");
                    Room leftRoom = RoomFactory.getRoom("r", moreRooms);
                    System.out.println("Room width: " + leftRoom.getWIDTH());
                    System.out.println("Room height: " + leftRoom.getHEIGHT());
                    int cellNr = getCellNr(getCurrentRoom(), "l");

                    int doorX = currentRoomX - leftRoom.getWIDTH();
                    int doorY = currentRoomY + cellNr - getCellNr(leftRoom,"r");

                    System.out.println(doorX);
                    System.out.println(doorY);

                    if(roomFits(leftRoom, doorX, doorY)) {
                        entrance = "r"+getCellNr(leftRoom, "r");
                        entranceSide = "r";
                        currentRoomNbr += 1;
                        addRoom(leftRoom, doorX, doorY, currentRoomNbr);
                        setNewRoomPosition();
                        notAdded = false;
                    }
                }
            }

            if(right == 1) {
                boolean notAdded = true;
                boolean printed = false;
                while(notAdded) {
                    //System.out.println("rightroom");
                    Room rightRoom = RoomFactory.getRoom("l", moreRooms);

                    int cellNr = getCellNr(getCurrentRoom(), "r");

                    int doorX = currentRoomX + getCurrentRoom().getWIDTH();
                    System.out.println(rightRoom.getDoors());
                    int doorY = currentRoomY + cellNr - getCellNr(rightRoom,"l");
                    if(!printed) {
                        System.out.println();
                        System.out.println("doorX: " + doorX);
                        System.out.println("doorY: " + doorY);
                        printed = true;
                    }
                    if(roomFits(rightRoom, doorX, doorY)) {
                        entrance = "l"+getCellNr(rightRoom, "l");
                        entranceSide = "l";
                        currentRoomNbr += 1;
                        addRoom(rightRoom, doorX, doorY, currentRoomNbr);
                        setNewRoomPosition();
                        notAdded = false;
                    }
                }
            }

            if(top == 1) {
                boolean notAdded = true;
                while(notAdded) {
                    System.out.println("toproom");
                    Room topRoom = RoomFactory.getRoom("b", moreRooms);

                    int cellNr = getCellNr(getCurrentRoom(), "t");

                    int x = currentRoomX + cellNr - getCellNr(topRoom, "b");
                    int y = currentRoomY + getCurrentRoom().getWIDTH();
                    if(roomFits(topRoom, x, y)) {
                        entrance = "b"+getCellNr(topRoom, "b");
                        entranceSide = "b";
                        currentRoomNbr += 1;
                        addRoom(topRoom, x, y, currentRoomNbr);
                        setNewRoomPosition();
                        notAdded = false;
                    }
                }
            }

            if(bottom == 1) {
                boolean notAdded = true;
                while(notAdded) {
                    System.out.println("bottomroom");
                    Room bottomRoom = RoomFactory.getRoom("t", moreRooms);

                    int cellNr = getCellNr(getCurrentRoom(), "b");

                    int x = currentRoomX + cellNr - getCellNr(bottomRoom, "t");
                    int y = currentRoomY + getCurrentRoom().getWIDTH();

                    if(roomFits(bottomRoom, x, y)) {
                        currentRoomNbr += 1;
                        addRoom(bottomRoom, x, y, currentRoomNbr);
                        setNewRoomPosition();
                        notAdded = false;
                    }
                }
            }

            if(roomsRemaining < 10) {
                moreRooms = false;
            }

        }
        printMap();
        currentRoomNbr = 0; // resets the value to the starting room
        currentRoomX = 0;
        currentRoomY = 50;
        getCurrentRoom().initRoom();


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

    private boolean roomFits(Room room, int xPos, int yPos) {
        for(int x = xPos; x < xPos+room.getWIDTH(); x++) {
            //System.out.println();
            for(int y = yPos; y < yPos+room.getHEIGHT(); y++) {
                //System.out.print(roomMap[x][y]);
                System.out.println();
                System.out.println("x: " + x);
                System.out.println("y: " + y);

                if(roomMap[x][y] != -1) {
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
        System.out.println("MAP");
        for(int x = 0; x < roomMap.length; x++) {
            System.out.println();
            for(int y = 0; y < roomMap[x].length ; y++) {
                System.out.print(roomMap[x][y] + " ");
            }
        }
    }

    private void addRoom(Room room, int xPos, int yPos, int ID) {
        rooms.add(room);
        for(int x = xPos; x < xPos+room.getWIDTH(); x++) {
            for(int y = yPos; y < yPos+room.getHEIGHT(); y++) {
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
        System.out.println("changeActiveRoom()");
        /*if(currentRoomNbr == 0) {
            currentRoomNbr = 1;
        } else {
            currentRoomNbr = 0;
        }
        System.out.println("currentRoom: " + currentRoomNbr);*/

        //TODO finish and test the actual method below
        System.out.println("Exit: " + exit);
        String side = exit.substring(0,1);
        int cell = Integer.parseInt(exit.substring(1));

        int newRoomX = 0;
        int newRoomY = 0;

        String entranceSide = "n";

        System.out.println("Current room x position: " + currentRoomX);
        System.out.println("Current room y position: " + currentRoomY);

        switch(side) {
            case "l":   newRoomX = getNextRoomX("l");
                        System.out.println("switch to l");
                        entranceSide = "r";
                        newRoomY = getNextRoomY(cell);
                        break;
            case "b":   newRoomY = getNextRoomY("b");
                        entranceSide = "t";
                        newRoomX = getNextRoomX(cell);
                        break;
            case "r":   newRoomX = getNextRoomX("r");
                        entranceSide = "l";
                        newRoomY = getNextRoomY(cell);
                        break;
            case "t":   newRoomY = getNextRoomY("t");
                        entranceSide = "b";
                        newRoomX = getNextRoomY(cell);
        }

        System.out.println("New room x: " + newRoomX + ", New room y: " + newRoomY);
        currentRoomNbr = roomMap[newRoomX][newRoomY];
        System.out.println("currentRoomNumber: " + currentRoomNbr);
        setNewRoomPosition();

        int entranceCell = 0;
        System.out.println("EntranceSide: " + entranceSide);
        if(entranceSide.equals("r") || entranceSide.equals("l")) {
            entranceCell = Math.abs(currentRoomY-newRoomY);
            System.out.println("CurrentRoomY: " + currentRoomY + ", NewRoomY: " + newRoomY);
        } else {
            entranceCell = Math.abs(currentRoomX-newRoomX);
            System.out.println("CurrentRoomX: " + currentRoomX + ", NewRoomX: " + newRoomX);
        }
        System.out.println("EntranceCell: " + entranceCell);
        this.entrance = entranceSide + entranceCell;
        System.out.println("entrance: " + entrance);


    }

    private int getNextRoomX(String side) {
        if(side.equals("l")) {
            System.out.println("getNextRoomX: L = " + (currentRoomX-1));
            return currentRoomX-1;
        }
        if(side.equals("r")) {
            System.out.println( getCurrentRoom().getWIDTH()+currentRoomX);
            return getCurrentRoom().getWIDTH()+currentRoomX;
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
        return currentRoomY + cell-1;
    }



    private void destroyWorld() {
        rooms.get(currentRoomNbr).getTiledHandler().destroy();
    }

    private void rebuildWorld() {
        System.out.println("rebuildWorld: current room number: " + currentRoomNbr);
        rooms.get(currentRoomNbr).initRoom();

        getCurrentRoom().getTiledHandler().setHeroPosition(entrance);

        /*
        if(exit.equals("leftDoor")) {
            rooms.get(currentRoomNbr).getTiledHandler().setHeroPosition("right");
        } else if(exit.equals("rightDoor")) {
            rooms.get(currentRoomNbr).getTiledHandler().setHeroPosition("left");
        }*/
    }

    public Room getCurrentRoom() {
        return rooms.get(currentRoomNbr);
    }

    public void flagRoomForDestruction(String door) {
        this.exit = door;
        this.destroyRoom = true;
    }

    public void setNewRoomPosition() {
        for(int x = 0; x < 100; x++) {
            for(int y = 0; y < 100; y++) {
                if(roomMap[x][y] == currentRoomNbr) {
                    System.out.println("setNewRoomPosition()");
                    System.out.println("CurrentRoom: " + currentRoomNbr);
                    System.out.println("X: " + x);
                    System.out.println("Y: " + y);
                    currentRoomX = x;
                    currentRoomY = y;
                    return;
                }
            }
        }
    }
}
