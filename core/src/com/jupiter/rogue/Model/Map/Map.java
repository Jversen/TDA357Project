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

        currentRoomX = 0;
        currentRoomY = 50;

        ArrayList<String> roomsAdded = new ArrayList<>();

        HashMap<Integer, String> entrances = new HashMap<>();
        String entrance;
        String entranceSide = "l";
        boolean moreRooms = true;
        boolean mapFinished = false;
        boolean firstRun = true;
        int nextRoom = currentRoomNbr+1;
        while(!mapFinished) {

            if(firstRun) {
                firstRun = false;
            } else {
                entranceSide = entrances.get(currentRoomNbr).substring(0,1);
            }

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

            int totalDoors = left + right + top + bottom;

            for(int remainingDoors = totalDoors; remainingDoors > 0; remainingDoors--) {

                if(left == 1 && !entranceSide.equals("l")) {
                    boolean tmpMoreRooms = moreRooms;
                    boolean notAdded = true;
                    int tries = 0;

                    // tries to find a suitable room, jumps out of loop if one is found and added or if no suitable room is found in 10 tries
                    while(notAdded) {
                        Room leftRoom = RoomFactory.getRoom("r", tmpMoreRooms);
                        int cellNr = getCellNr(getCurrentRoom(), "l");

                        int doorX = currentRoomX - leftRoom.getWIDTH();
                        int doorY = currentRoomY + cellNr - getCellNr(leftRoom,"r");

                        System.out.println("LeftRoom check:");
                        if(roomFits(leftRoom, doorX, doorY, "r")) {

                            entrance = "r"+getCellNr(leftRoom, "r");

                            addRoom(leftRoom, doorX, doorY, nextRoom);
                            entrances.put(nextRoom, entrance);

                            roomsAdded.add("Room " + nextRoom + " added to the LEFT of room " + currentRoomNbr);

                            nextRoom += 1;
                            left = 0;
                            notAdded = false;
                            //printMap();
                        } else {
                            tries++;
                            if(tries > 10) {
                                tmpMoreRooms = false;
                            }
                        }
                    }
                }

                if(right == 1 && !entranceSide.equals("r")) {
                    boolean tmpMoreRooms = moreRooms;
                    boolean notAdded = true;
                    int tries = 0;

                    // tries to find a suitable room, jumps out of loop if one is found and added or if no suitable room is found in 10 tries
                    while(notAdded) {
                        Room rightRoom = RoomFactory.getRoom("l", moreRooms);

                        int cellNr = getCellNr(getCurrentRoom(), "r");

                        int doorX = currentRoomX + getCurrentRoom().getWIDTH();
                        int doorY = currentRoomY + cellNr - getCellNr(rightRoom,"l");

                        System.out.println("RightRoom check:");
                        if(roomFits(rightRoom, doorX, doorY, "l")) {

                            entrance = "l"+getCellNr(rightRoom, "l");
                            addRoom(rightRoom, doorX, doorY, nextRoom);
                            entrances.put(nextRoom, entrance);
                            roomsAdded.add("Room " + nextRoom + " added to the RIGHT of room " + currentRoomNbr);

                            nextRoom += 1;
                            right = 0;
                            notAdded = false;
                            //printMap();
                        } else {
                            tries++;
                            if(tries > 10) {
                                tmpMoreRooms = false;
                            }
                        }
                    }
                }

                if(top == 1 && !entranceSide.equals("t")) {
                    boolean tmpMoreRooms = moreRooms;
                    boolean notAdded = true;
                    int tries = 0;

                    // tries to find a suitable room, jumps out of loop if one is found and added or if no suitable room is found in 10 tries
                    while(notAdded) {
                        Room topRoom = RoomFactory.getRoom("b", tmpMoreRooms);

                        int cellNr = getCellNr(getCurrentRoom(), "t");

                        int x = currentRoomX + cellNr - getCellNr(topRoom, "b");
                        int y = currentRoomY + getCurrentRoom().getHEIGHT();

                        System.out.println("TopRoom check:");
                        if(roomFits(topRoom, x, y, "b")) {

                            entrance = "b"+getCellNr(topRoom, "b");

                            addRoom(topRoom, x, y, nextRoom);

                            entrances.put(nextRoom, entrance);
                            roomsAdded.add("Room " + nextRoom + " added ABOVE room " + currentRoomNbr);

                            nextRoom += 1;
                            top = 0;
                            notAdded = false;
                            //printMap();
                        } else {
                            tries++;
                            if(tries > 10) {
                                tmpMoreRooms = false;
                            }
                        }
                    }
                }

                if(bottom == 1 && !entranceSide.equals("b")) {
                    boolean tmpMoreRooms = moreRooms;
                    boolean notAdded = true;
                    int tries = 0;

                    // tries to find a suitable room, jumps out of loop if one is found and added or if no suitable room is found in 10 tries
                    while(notAdded) {
                        Room bottomRoom = RoomFactory.getRoom("t", tmpMoreRooms);

                        int cellNr = getCellNr(getCurrentRoom(), "b");

                        int x = currentRoomX + cellNr - getCellNr(bottomRoom, "t");
                        int y = currentRoomY - bottomRoom.getHEIGHT();

                        if(roomFits(bottomRoom, x, y, "t")) {

                            entrance = "t"+getCellNr(bottomRoom, "t");
                            addRoom(bottomRoom, x, y, nextRoom);
                            entrances.put(nextRoom, entrance);
                            roomsAdded.add("Room " + nextRoom + " added BELOW room " + currentRoomNbr);

                            nextRoom += 1;
                            bottom = 0;
                            notAdded = false;
                            //printMap();
                        } else {
                            tries++;
                            if(tries > 10) {
                                tmpMoreRooms = false;
                            }
                        }
                    }
                }

                for(String string : roomsAdded) {
                    System.out.println(string);
                }
            }

            currentRoomNbr += 1;
            setNewRoomPosition();

            while(currentRoomNbr < rooms.size() && !doorsLeft()) {
                currentRoomNbr += 1;
                setNewRoomPosition();
            }

            if(currentRoomNbr >= rooms.size()) {
                mapFinished = true;
            }

            /*System.out.println("Trying to parse entrance from current room: ");
            entrance = entrances.get(currentRoomNbr);
            System.out.println("Entrance: " + entrance);
            entranceSide = entrance.substring(0,1);*/

        }

        printMap();
        currentRoomNbr = 0; // resets the value to the starting room
        currentRoomX = 0;
        currentRoomY = 50;
        getCurrentRoom().initRoom();


    }

    /**
     * Returns true if the current room has doors leading nowhere
     */
    private boolean doorsLeft() {
        ArrayList<String> doors = getCurrentRoom().getDoors();
        for (String door : doors) {
            String entranceSide = door.substring(0, 1);
            int entranceCell = Integer.parseInt(door.substring(1));

            int newRoomX = -1;
            int newRoomY = -1;

            switch (entranceSide) {
                case "l":
                    newRoomX = getNextRoomX("l");
                    newRoomY = getNextRoomY(entranceCell);
                    break;
                case "b":
                    newRoomY = getNextRoomY("b");
                    newRoomX = getNextRoomX(entranceCell);
                    break;
                case "r":
                    newRoomX = getNextRoomX("r");
                    newRoomY = getNextRoomY(entranceCell);
                    break;
                case "t":
                    newRoomY = getNextRoomY("t");
                    newRoomX = getNextRoomY(entranceCell);
                    break;
            }

            if (roomMap[newRoomX][newRoomY] == -1) {
                return true;
            }
        }
        return false;
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

    private boolean roomFits(Room room, int xPos, int yPos, String entranceSide) {
        if(xPos < 0 || yPos < 0 || xPos+room.getWIDTH() > 100 || yPos+room.getHEIGHT() > 100) {
            return false;
        }

        for(String door : room.getDoors()) {
            if(!door.substring(0,1).equals(entranceSide)) {
                String side = door.substring(0,1);
                int cell = Integer.parseInt(door.substring(1));

                int x = -1;
                int y = -1;

                switch(side) {
                    case "l":   x = getNextRoomX("l");
                                y = getNextRoomY(cell);
                                break;
                    case "b":   y = getNextRoomY("b");
                                x = getNextRoomX(cell);
                                break;
                    case "r":   x = getNextRoomX("r");
                                y = getNextRoomY(cell);
                                break;
                    case "t":   y = getNextRoomY("t");
                                x = getNextRoomX(cell);
                                break;
                }

                if(x < 0 || y < 0 || x > 100 || y > 100 || !(roomMap[x][y] == -1)) {
                    return false;
                }
            }
        }

        for(int x = xPos; x < xPos+room.getWIDTH(); x++) {
            for(int y = yPos; y < yPos+room.getHEIGHT(); y++) {
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
                System.out.print((roomMap[x][y]+1) + " ");
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
        //TODO finish and test the actual method below
        String side = exit.substring(0,1);
        int cell = Integer.parseInt(exit.substring(1));

        int newRoomX = 0;
        int newRoomY = 0;

        String entranceSide = "n";

        switch(side) {
            case "l":   newRoomX = getNextRoomX("l");
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
                        newRoomX = getNextRoomX(cell);
        }

        currentRoomNbr = roomMap[newRoomX][newRoomY];

        setNewRoomPosition();

        int entranceCell = 0;

        if(entranceSide.equals("r") || entranceSide.equals("l")) {
            entranceCell = Math.abs(currentRoomY-newRoomY+1);
        } else {
            entranceCell = Math.abs(currentRoomX-newRoomX+1);
        }
        this.entrance = entranceSide + entranceCell;
    }

    private int getNextRoomX(String side) {
        if(side.equals("l")) {
            return currentRoomX-1;
        }
        if(side.equals("r")) {
            return getCurrentRoom().getWIDTH()+currentRoomX;
        }
        return -1;
    }

    private int getNextRoomX(int cell) {
        return currentRoomX + cell-1;
    }

    private int getNextRoomY(String side) {
        if(side.equals("t")) {
            return currentRoomY + getCurrentRoom().getHEIGHT();
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
