package com.jupiter.rogue.Model.Map;

import com.badlogic.gdx.physics.box2d.Body;
import com.jupiter.rogue.Controller.EnemyController;
import com.jupiter.rogue.Model.Creatures.Enemy;
import com.jupiter.rogue.Utils.WorldConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import static com.jupiter.rogue.Utils.WorldConstants.BODIES;
import static com.jupiter.rogue.Utils.WorldConstants.PPM;

/**
 * Created by Johan on 16/04/15.
 */
@lombok.Data
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

    // these arraylists store all recently added rooms
    private ArrayList<Integer> exitL = new ArrayList<>();
    private ArrayList<Integer> exitR = new ArrayList<>();
    private ArrayList<Integer> exitT = new ArrayList<>();
    private ArrayList<Integer> exitB = new ArrayList<>();
    private ArrayList<Integer> noExitL = new ArrayList<>();
    private ArrayList<Integer> noExitR = new ArrayList<>();
    private ArrayList<Integer> noExitT = new ArrayList<>();
    private ArrayList<Integer> noExitB = new ArrayList<>();



    private Map() {
        rooms = new ArrayList<Room>();

        createMap();
    }

    public static Map getInstance() {
        if(map == null) {
            map = new Map();
        }
        return map;
    }

    public void update() {
        if(destroyRoom) {
            switchRoom();
        }

        //Updates all the individual enemies in the current room, also removes them if they die.
        for (int i = 0; i < getCurrentRoom().getEnemyControllers().size(); i++) {
            if (getCurrentRoom().getEnemyControllers().get(i).getEnemy().isCreatureAlive()) {
                getCurrentRoom().getEnemyControllers().get(i).update();
            } else {
                getCurrentRoom().getEnemyControllers().get(i).getBody().setUserData("dead");
                getCurrentRoom().getEnemyControllers().remove(i);
            }
        }
    }

    public void createMap() {
        initMap();
        Room startingRoom = RoomFactory.getRoom("StartingRoom");
        currentRoomNbr = 0;
        currentRoomX = 0;
        currentRoomY = 50;
        addRoom(startingRoom, currentRoomX, currentRoomY, currentRoomNbr);

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

                if(left == 1 && !entranceSide.equals("l") && !doorAlreadyFitted("l")) {
                    boolean tmpMoreRooms = moreRooms;
                    boolean notAdded = true;
                    int tries = 0;

                    // tries to find a suitable room, jumps out of loop if one is found and added or if no suitable room is found in 10 tries
                    while(notAdded) {
                        Room leftRoom = RoomFactory.getRoom("r", getPseudoRandomR(tmpMoreRooms), tmpMoreRooms);
                        System.out.println("Trying to add room " + nextRoom + " TO THE LEFT of " + currentRoomNbr + "(" + leftRoom.getPath() + ")");
                        int cellNr = getCellNr(getCurrentRoom(), "l");

                        int x = currentRoomX - leftRoom.getWIDTH();
                        int y = currentRoomY + cellNr - getCellNr(leftRoom,"r");

                        System.out.println("LeftRoom check:");
                        if(roomFits(leftRoom, x, y, "r")) {

                            entrance = "r"+getCellNr(leftRoom, "r");
                            entrances.put(nextRoom, entrance);

                            addRoom(leftRoom, x, y, nextRoom);
                            leftRoom.setX(x);
                            leftRoom.setY(y);


                            roomsAdded.add("Room " + nextRoom + "(" + leftRoom.getPath() + ") added to the LEFT of room " + currentRoomNbr);

                            nextRoom += 1;
                            left = 0;
                            notAdded = false;
                            printMap();
                        } else {
                            tries++;
                            if(tries > 100) {
                                System.out.println("Tries: " + tries);
                                tmpMoreRooms = false;
                            }
                        }
                    }
                }

                if(right == 1 && !entranceSide.equals("r") && !doorAlreadyFitted("r")) {
                    boolean tmpMoreRooms = moreRooms;
                    boolean notAdded = true;
                    int tries = 0;

                    // tries to find a suitable room, jumps out of loop if one is found and added or if no suitable room is found in 10 tries
                    while(notAdded) {
                        Room rightRoom = RoomFactory.getRoom("l", getPseudoRandomL(tmpMoreRooms), tmpMoreRooms);
                        System.out.println("Trying to add room " + nextRoom + " TO THE RIGHT of " + currentRoomNbr + "(" + rightRoom.getPath() + ")");

                        int cellNr = getCellNr(getCurrentRoom(), "r");

                        int x = currentRoomX + getCurrentRoom().getWIDTH();
                        int y = currentRoomY + cellNr - getCellNr(rightRoom,"l");

                        System.out.println("RightRoom check:");
                        if(roomFits(rightRoom, x, y, "l")) {

                            entrance = "l"+getCellNr(rightRoom, "l");
                            addRoom(rightRoom, x, y, nextRoom);
                            rightRoom.setX(x);
                            rightRoom.setY(y);
                            entrances.put(nextRoom, entrance);
                            roomsAdded.add("Room " + nextRoom + "(" + rightRoom.getPath() + ") added to the RIGHT of room " + currentRoomNbr);

                            nextRoom += 1;
                            right = 0;
                            notAdded = false;
                            printMap();
                        } else {
                            tries++;
                            if(tries > 100) {
                                System.out.println("Tries: " + tries);
                                tmpMoreRooms = false;
                            }
                        }
                    }
                }

                if(top == 1 && !entranceSide.equals("t") && !doorAlreadyFitted("t")) {
                    boolean tmpMoreRooms = moreRooms;
                    boolean notAdded = true;
                    int tries = 0;

                    // tries to find a suitable room, jumps out of loop if one is found and added or if no suitable room is found in 10 tries
                    while(notAdded) {
                        Room topRoom = RoomFactory.getRoom("b", getPseudoRandomB(tmpMoreRooms), tmpMoreRooms);
                        System.out.println("Trying to add room " + nextRoom + "ABOVE" + currentRoomNbr + "(" + topRoom.getPath() + ")");

                        int cellNr = getCellNr(getCurrentRoom(), "t");

                        int x = currentRoomX + cellNr - getCellNr(topRoom, "b");
                        int y = currentRoomY + getCurrentRoom().getHEIGHT();

                        System.out.println("TopRoom check:");
                        if(roomFits(topRoom, x, y, "b")) {

                            entrance = "b"+getCellNr(topRoom, "b");

                            addRoom(topRoom, x, y, nextRoom);
                            topRoom.setX(x);
                            topRoom.setY(y);

                            entrances.put(nextRoom, entrance);
                            roomsAdded.add("Room " + nextRoom + "(" + topRoom.getPath() + ") added ABOVE room " + currentRoomNbr);

                            nextRoom += 1;
                            top = 0;
                            notAdded = false;
                            printMap();
                        } else {
                            tries++;
                            System.out.println("Tries: " + tries);
                            if(tries > 100) {
                                tmpMoreRooms = false;
                            }
                        }
                    }
                }

                if(bottom == 1 && !entranceSide.equals("b") && !doorAlreadyFitted("b")) {
                    boolean tmpMoreRooms = moreRooms;
                    boolean notAdded = true;
                    int tries = 0;

                    // tries to find a suitable room, jumps out of loop if one is found and added or if no suitable room is found in 10 tries
                    while(notAdded) {
                        Room bottomRoom = RoomFactory.getRoom("t", getPseudoRandomT(tmpMoreRooms), tmpMoreRooms);
                        System.out.println("Trying to add room " + nextRoom + "BELOW" + currentRoomNbr + "(" + bottomRoom.getPath() + ")");
                        int cellNr = getCellNr(getCurrentRoom(), "b");

                        int x = currentRoomX + cellNr - getCellNr(bottomRoom, "t");
                        int y = currentRoomY - bottomRoom.getHEIGHT();

                        if(roomFits(bottomRoom, x, y, "t")) {

                            entrance = "t"+getCellNr(bottomRoom, "t");
                            addRoom(bottomRoom, x, y, nextRoom);
                            bottomRoom.setX(x);
                            bottomRoom.setY(y);
                            entrances.put(nextRoom, entrance);
                            roomsAdded.add("Room " + nextRoom + "(" + bottomRoom.getPath() + ") added BELOW room " + currentRoomNbr);

                            nextRoom += 1;
                            bottom = 0;
                            notAdded = false;
                            printMap();
                        } else {
                            tries++;
                            System.out.println("Tries: " + tries);
                            if(tries > 100) {
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

            if(rooms.size() > 50) {
                moreRooms = false;
            }

            if(currentRoomNbr >= rooms.size()) {
                mapFinished = true;
            }
        }

        printMap();
        currentRoomNbr = 0; // resets the value to the starting room
        currentRoomX = 0;
        currentRoomY = 50;

        /* Destroys all created enemy bodies, they will be recreated later when the player enters the right
        rooms
         */
        destroyBodies();

/*        for (int i = 0; i < rooms.size(); i++) {
            for (int j = 0; j < rooms.get(currentRoomNbr).getEnemyControllers().size(); j++) {
                rooms.get(currentRoomNbr).getEnemyControllers().get(j).destroyBody();
            }
        }*/

        getCurrentRoom().initRoom();


    }

    public int getPseudoRandomL(boolean exit) {
        Random rand = new Random();
        if(exit) {
            for(int i = 0; i < RoomFactory.ROOMS_WITH_EXIT_DOOR_LEFT; i++) {
                int room = rand.nextInt(RoomFactory.ROOMS_WITH_EXIT_DOOR_LEFT) + 1;
                if(!exitL.contains(room)) {
                    exitL.add(room);
                    return room;
                }
            }
            exitL.clear();
            int room = rand.nextInt(RoomFactory.ROOMS_WITH_EXIT_DOOR_LEFT) + 1;
            exitL.add(room);
            return room;
        } else {
            for(int i = 0; i < RoomFactory.ROOMS_WITHOUT_EXIT_DOOR_LEFT; i++) {
                int room = rand.nextInt(RoomFactory.ROOMS_WITHOUT_EXIT_DOOR_LEFT) + 1;
                if(!noExitL.contains(room)) {
                    noExitL.add(room);
                    return room;
                }
            }
            noExitL.clear();
            int room = rand.nextInt(RoomFactory.ROOMS_WITHOUT_EXIT_DOOR_LEFT) + 1;
            noExitL.add(room);
            return room;
        }
    }

    public int getPseudoRandomR(boolean exit) {
        Random rand = new Random();
        if(exit) {
            for(int i = 0; i < RoomFactory.ROOMS_WITH_EXIT_DOOR_RIGHT; i++) {
                int room = rand.nextInt(RoomFactory.ROOMS_WITH_EXIT_DOOR_RIGHT) + 1;
                if(!exitR.contains(room)) {
                    exitR.add(room);
                    return room;
                }
            }
            exitR.clear();
            int room = rand.nextInt(RoomFactory.ROOMS_WITH_EXIT_DOOR_RIGHT) + 1;
            exitR.add(room);
            return room;
        } else {
            for(int i = 0; i < RoomFactory.ROOMS_WITHOUT_EXIT_DOOR_RIGHT; i++) {
                int room = rand.nextInt(RoomFactory.ROOMS_WITHOUT_EXIT_DOOR_RIGHT) + 1;
                if(!noExitR.contains(room)) {
                    noExitR.add(room);
                    return room;
                }
            }
            noExitR.clear();
            int room = rand.nextInt(RoomFactory.ROOMS_WITHOUT_EXIT_DOOR_RIGHT) + 1;
            noExitR.add(room);
            return room;
        }
    }

    public int getPseudoRandomB(boolean exit) {
        Random rand = new Random();
        if(exit) {
            for(int i = 0; i < RoomFactory.ROOMS_WITH_EXIT_DOOR_BOTTOM; i++) {
                int room = rand.nextInt(RoomFactory.ROOMS_WITH_EXIT_DOOR_BOTTOM) + 1;
                if(!exitB.contains(room)) {
                    exitB.add(room);
                    return room;
                }
            }
            exitB.clear();
            int room = rand.nextInt(RoomFactory.ROOMS_WITH_EXIT_DOOR_BOTTOM) + 1;
            exitB.add(room);
            return room;
        } else {
            for(int i = 0; i < RoomFactory.ROOMS_WITHOUT_EXIT_DOOR_BOTTOM; i++) {
                int room = rand.nextInt(RoomFactory.ROOMS_WITHOUT_EXIT_DOOR_BOTTOM) + 1;
                if(!noExitB.contains(room)) {
                    noExitB.add(room);
                    return room;
                }
            }
            noExitB.clear();
            int room = rand.nextInt(RoomFactory.ROOMS_WITHOUT_EXIT_DOOR_BOTTOM) + 1;
            noExitB.add(room);
            return room;
        }
    }

    public int getPseudoRandomT(boolean exit) {
        Random rand = new Random();
        if(exit) {
            for(int i = 0; i < RoomFactory.ROOMS_WITH_EXIT_DOOR_TOP; i++) {
                int room = rand.nextInt(RoomFactory.ROOMS_WITH_EXIT_DOOR_TOP) + 1;
                if(!exitT.contains(room)) {
                    exitT.add(room);
                    return room;
                }
            }
            exitT.clear();
            int room = rand.nextInt(RoomFactory.ROOMS_WITH_EXIT_DOOR_TOP) + 1;
            exitT.add(room);
            return room;
        } else {
            for(int i = 0; i < RoomFactory.ROOMS_WITHOUT_EXIT_DOOR_TOP; i++) {
                int room = rand.nextInt(RoomFactory.ROOMS_WITHOUT_EXIT_DOOR_TOP) + 1;
                if(!noExitT.contains(room)) {
                    noExitT.add(room);
                    return room;
                }
            }
            noExitT.clear();
            int room = rand.nextInt(RoomFactory.ROOMS_WITHOUT_EXIT_DOOR_TOP) + 1;
            noExitT.add(room);
            return room;
        }
    }



    private boolean doorAlreadyFitted(String doorSide) {
        Room room = getCurrentRoom();
        int x = room.getX();
        int y = room.getY();
        int cell = -1;

        for(String door : room.getDoors()) {
            if(door.substring(0,1).equals(doorSide)) {
                cell = Integer.parseInt(door.substring(1));
            }
        }

        int doorX;
        int doorY;
        int nextRoomX = -1;
        int nextRoomY = -1;

        switch (doorSide) {
            case "l" :  doorX = x;
                        nextRoomX = doorX - 1;
                        doorY = y + cell - 1;
                        nextRoomY = doorY;
                        break;
            case "r" :  doorX = x + room.getWIDTH() - 1;
                        nextRoomX = doorX + 1;
                        doorY = y + cell - 1;
                        nextRoomY = doorY;
                        break;
            case "b" :  doorX = x + cell - 1;
                        nextRoomX = doorX;
                        doorY = y;
                        nextRoomY = doorY - 1;
                        break;
            case "t" :  doorX = x + cell - 1;
                        nextRoomX = doorX;
                        doorY = y + room.getHEIGHT() - 1;
                        nextRoomY = doorY + 1;
                        break;
        }

        if(nextRoomX < 0 || nextRoomX >= 100 || nextRoomY < 0 || nextRoomY >= 100 || roomMap[nextRoomX][nextRoomY] == -1) {
            return false;
        }

        int oppositeRoomNumber = roomMap[nextRoomX][nextRoomY];
        Room oppositeRoom = rooms.get(oppositeRoomNumber);
        ArrayList<String> oppositeRoomDoors = oppositeRoom.getDoors();

        for(String oppositeRoomDoor : oppositeRoomDoors) {

            String oppositeDoorSide = oppositeRoomDoor.substring(0,1);
            int oppositeDoorCell = Integer.parseInt(oppositeRoomDoor.substring(1));
            int oppositeRoomX = oppositeRoom.getX();
            int oppositeRoomY = oppositeRoom.getY();
            int oppositeDoorX = oppositeRoomX;
            int oppositeDoorY = oppositeRoomY;
            switch (oppositeDoorSide) {
                case "l" :  oppositeDoorY = oppositeRoomY + oppositeDoorCell - 1;
                    break;
                case "r" :  oppositeDoorX = oppositeRoomX + oppositeRoom.getWIDTH() - 1;
                            oppositeDoorY = oppositeRoomY + oppositeDoorCell - 1;
                    break;
                case "b" :  oppositeDoorX = oppositeRoomX + oppositeDoorCell - 1;
                    break;
                case "t" :  oppositeDoorX = oppositeRoomX + oppositeDoorCell - 1;
                            oppositeDoorY = oppositeRoomY + oppositeRoom.getHEIGHT() - 1;
            }

            if(nextRoomX == oppositeDoorX && nextRoomY == oppositeDoorY) {
                System.out.println("DOORS MATCHED!!!");
                return true;
            }
        }
        System.out.println("DOORS DIDN'T MATCH :(");
        return false;
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

        /*try {
            Thread.sleep(100);
        } catch (InterruptedException e) {

        }*/

        System.out.println("Room width: " + room.getWIDTH());
        System.out.println("Room height: " + room.getHEIGHT());

        if(xPos < 0 || yPos < 0 || xPos+room.getWIDTH() > 100 || yPos+room.getHEIGHT() > 100) {
            System.out.println("Failed initial position test");
            System.out.println("X: " + xPos);
            System.out.println("Y: " + yPos);
            return false;
        }

        for(int x = xPos; x < xPos+room.getWIDTH(); x++) {
            for(int y = yPos; y < yPos+room.getHEIGHT(); y++) {
                if(roomMap[x][y] != -1) {
                    System.out.println("Failed positioning test");
                    System.out.println("X: " + x);
                    System.out.println("Y; " + y);
                    System.out.println("Room in the way: " + roomMap[x][y]);
                    return false;
                }
            }
        }
        
        String matchingSide = null;
        if(!doorsFit(room, xPos, yPos, entranceSide)) {
            return false;
        } else {
            matchingSide = matchingDoorSide(room, xPos, yPos, entranceSide);
        }

        if(isBlockingDoor(room, xPos, yPos, entranceSide, matchingSide)) {
            System.out.println("DOOR BLOCKING TEST FAILED");
            return false;
        }


        return true;
    }

    private String matchingDoorSide(Room room, int xPos, int yPos, String entranceSide) {
        System.out.println("matchingDoorSide()");
        for(String door : room.getDoors()) {
            String side = door.substring(0,1);
            int cell = Integer.parseInt(door.substring(1));

            if(!side.substring(0,1).equals(entranceSide)) {
                int x = -1;
                int y = -1;

                switch (side) {
                    case "l" :  x = xPos - 1;
                                y = yPos + cell - 1;
                                break;
                    case "r" :  x = xPos + room.getWIDTH();
                                y = yPos + cell - 1;
                                break;
                    case "t" :  x = xPos + cell - 1;
                                y = yPos + room.getHEIGHT();
                                break;
                    case "b" :  x = xPos + cell - 1;
                                y = yPos -1;
                                break;
                }

                if(x < 0 || y < 0 || x > 100 || y > 100) {
                    return null;
                }

                if(roomMap[x][y] != -1) {
                    int oppositeRoomNumber = roomMap[x][y];
                    Room oppositeRoom = rooms.get(oppositeRoomNumber);
                    ArrayList<String> oppositeRoomDoors = oppositeRoom.getDoors();

                    for(String oppositeRoomDoor : oppositeRoomDoors) {

                        String oppositeDoorSide = oppositeRoomDoor.substring(0,1);
                        int oppositeDoorCell = Integer.parseInt(oppositeRoomDoor.substring(1));

                        int oppositeRoomX = oppositeRoom.getX();
                        int oppositeRoomY = oppositeRoom.getY();

                        int oppositeDoorX = oppositeRoomX;
                        int oppositeDoorY = oppositeRoomY;

                        switch (oppositeDoorSide) {
                            case "l" :  oppositeDoorY = oppositeRoomY + oppositeDoorCell - 1;
                                break;
                            case "r" :  oppositeDoorX = oppositeRoomX + oppositeRoom.getWIDTH() - 1;
                                oppositeDoorY = oppositeRoomY + oppositeDoorCell - 1;
                                break;
                            case "b" :  oppositeDoorX = oppositeRoomX + oppositeDoorCell - 1;
                                break;
                            case "t" :  oppositeDoorX = oppositeRoomX + oppositeDoorCell - 1;
                                oppositeDoorY = oppositeRoomY + oppositeRoom.getHEIGHT() - 1;
                        }

                        if(x == oppositeDoorX && y == oppositeDoorY) {
                            System.out.println("DOORS MATCHED!!! (matchingDoorSide)");
                            return oppositeRoomDoor;
                        }
                    }
                    return null;
                }
            }
        }

        return null;
    }

    private boolean isBlockingDoor(Room room, int xPos, int yPos, String entranceSide, String matchingSide) {
        System.out.println("isBlockingDoor()");
        switch (entranceSide) {
            case "l" : return doorBlockedBelow(room, xPos, yPos, matchingSide) || doorBlockedAbove(room, xPos, yPos, matchingSide) || doorBlockedRight(room, xPos, yPos, matchingSide);
            case "r" : return doorBlockedBelow(room, xPos, yPos, matchingSide) || doorBlockedAbove(room, xPos, yPos, matchingSide) || doorBlockedLeft(room, xPos, yPos, matchingSide);
            case "t" : return doorBlockedBelow(room, xPos, yPos, matchingSide) || doorBlockedLeft(room, xPos, yPos, matchingSide) || doorBlockedRight(room, xPos, yPos, matchingSide);
            case "b" : return doorBlockedAbove(room, xPos, yPos, matchingSide) || doorBlockedLeft(room, xPos, yPos, matchingSide) || doorBlockedRight(room, xPos, yPos, matchingSide);
            default : return false;
        }
    }

    private boolean doorBlockedRight(Room room, int xPos, int yPos, String matchingSide) {
        System.out.println("doorBlockedRight()");
        System.out.println("xPos: " + xPos);
        System.out.println("yPos: " + yPos);
        ArrayList<Room> roomsToRight = new ArrayList<>();
        int lastRoomAdded = -1;

        // collect all rooms directly to the right of the room
        for(int y = yPos; y < yPos+room.getHEIGHT(); y++) {
            if(xPos+room.getWIDTH() < 100 && y < 100) {
                int roomNumber = roomMap[xPos+room.getWIDTH()][y];
                if(roomNumber != -1 && roomNumber != lastRoomAdded) {
                    roomsToRight.add(rooms.get(roomNumber));
                    lastRoomAdded = roomNumber;
                    System.out.println("Room to right: " + roomNumber);
                }
            }
        }

        // if there are no rooms to the right, nothing else to check
        if(roomsToRight.isEmpty()) {
            return false;
        }

        for(Room roomToRight : roomsToRight) {
            //if there are no doors facing the room, nothing else to check
            if(!hasDoor(roomToRight, "l")) {
                return false;
            } else if (!checkDoors(room, roomToRight, "l", xPos, yPos, matchingSide)) {
                return true;
            }
        }

        return false;
    }

    private boolean doorBlockedLeft(Room room, int xPos, int yPos, String matchingSide) {
        System.out.println("doorBlockedLeft()");
        System.out.println("xPos: " + xPos);
        System.out.println("yPos: " + yPos);

        ArrayList<Room> roomsToLeft = new ArrayList<>();
        int lastRoomAdded = -1;

        // collect all rooms directly to the left of the room
        for(int y = yPos; y < yPos+room.getHEIGHT(); y++) {
            if(xPos-1 >= 0 && y < 100) {
                int roomNumber = roomMap[xPos - 1][y];
                if(roomNumber != -1 && roomNumber != lastRoomAdded) {
                    roomsToLeft.add(rooms.get(roomNumber));
                    lastRoomAdded = roomNumber;
                    System.out.println("Room to left: " + roomNumber);
                }
            }
        }

        // if there are no rooms to the right, nothing else to check
        if(roomsToLeft.isEmpty()) {
            return false;
        }

        for(Room roomToLeft : roomsToLeft) {
            //if there are no doors facing the room, nothing else to check
            if(!hasDoor(roomToLeft, "r")) {
                return false;
            } else if (!checkDoors(room, roomToLeft, "r", xPos, yPos, matchingSide)) {
                return true;
            }
        }

        return false;
    }


    private boolean doorBlockedAbove(Room room, int xPos, int yPos, String matchingSide) {
        System.out.println("doorBlockedAbove()");
        ArrayList<Room> roomsAbove = new ArrayList<>();
        int lastRoomAdded = -1;

        System.out.println("xPos: " + xPos);
        System.out.println("yPos: " + yPos);

        // collect all rooms directly to the left of the room
        for(int x = xPos; x < xPos+room.getWIDTH(); x++) {
            if(yPos + room.getHEIGHT() < 100 && x < 100) {
                int roomNumber = roomMap[x][yPos+room.getHEIGHT()];
                if(roomNumber != -1 && roomNumber != lastRoomAdded) {
                    roomsAbove.add(rooms.get(roomNumber));
                    lastRoomAdded = roomNumber;
                    System.out.println("Room to above: " + roomNumber);
                }
            }
        }

        // if there are no rooms to the right, nothing else to check
        if(roomsAbove.isEmpty()) {
            return false;
        }

        for(Room roomAbove : roomsAbove) {
            //if there are no doors facing the room, nothing else to check
            if(!hasDoor(roomAbove, "b")) {
                return false;
            } else if (!checkDoors(room, roomAbove, "b", xPos, yPos, matchingSide)) {
                return true;
            }
        }

        return false;
    }

    private boolean doorBlockedBelow(Room room, int xPos, int yPos, String matchingSide) {
        System.out.println("doorBlockedBelow()");
        System.out.println("xPos: " + xPos);
        System.out.println("yPos: " + yPos);
        ArrayList<Room> roomsBelow = new ArrayList<>();
        int lastRoomAdded = -1;

        // collect all rooms directly to the left of the room
        for(int x = xPos; x < xPos+room.getWIDTH(); x++) {
            if(yPos - 1 >= 0 && x < 100) {
                int roomNumber = roomMap[x][yPos - 1];
                if(roomNumber != -1 && roomNumber != lastRoomAdded) {
                    roomsBelow.add(rooms.get(roomNumber));
                    lastRoomAdded = roomNumber;
                    System.out.println("Room below: " + roomNumber);
                }
            }
        }

        // if there are no rooms to the right, nothing else to check
        if(roomsBelow.isEmpty()) {
            return false;
        }

        for(Room roomBelow : roomsBelow) {
            //if there are no doors facing the room, nothing else to check
            if(!hasDoor(roomBelow, "t")) {
                return false;
            } else if (!checkDoors(room, roomBelow, "t", xPos, yPos, matchingSide)) {
                return true;
            }
        }

        return false;
    }

    private String getOppositeSide(String side) {
        switch (side) {
            case "l" : return "r";
            case "r" : return "l";
            case "t" : return "b";
            case "b" : return "t";
            default :  return null;
        }
    }

    private boolean checkDoors(Room room, Room roomToCheck, String side, int xPos, int yPos, String matchingSide) {
        System.out.println("checkDoors()");
        ArrayList<String> matchingDoors = getDoors(roomToCheck, side);
        for (String door : matchingDoors) {
            int x = getDoorX(roomToCheck, door);
            int y = getDoorY(roomToCheck, door);

            System.out.println("Door X: " + x);
            System.out.println("Door Y: " + y);

            System.out.println("Door: " + door);

            if(side.equals("l") || side.equals("r")) {
                if(!(y < yPos || y >= yPos + room.getHEIGHT())) {
                    System.out.println("checkDoors() failed side: " + side);
                    System.out.println("Y: " + y);
                    System.out.println("Room height: " + room.getHEIGHT());
                    System.out.println("yPos: " + yPos + ", yPos+roomHeight: " + (yPos+room.getHEIGHT()));
                    if(!door.equals(matchingSide)) {
                        return false;
                    } else {
                        return true;
                    }
                }
            } else {
                if(!(x < xPos || x >= xPos + room.getWIDTH())) {
                    System.out.println("checkDoors() failed side: " + side);
                    System.out.println("X: " + x);
                    System.out.println("Room width: " + room.getWIDTH());
                    System.out.println("xPos: " + xPos + ", xPos+roomWidth: " + (xPos+room.getWIDTH()));
                    if(!door.equals(matchingSide)) {
                        return false;
                    } else {
                        return true;
                    }
                }
            }
        }
        return true;
    }

    private int getDoorY(Room room, String door) {
        System.out.println("getDoorY()");
        System.out.println("Room X: " + room.getX());
        System.out.println("Room Y: " + room.getY());

        String side = door.substring(0,1);
        int cell = Integer.parseInt(door.substring(1));
        int yPos = room.getY();

        if(side.equals("l") || side.equals("r")) {
            return yPos + cell - 1;
        } else if (side.equals("t")){
            return yPos + room.getHEIGHT() - 1;
        } else {
            return yPos;
        }

    }

    private int getDoorX(Room room, String door) {
        System.out.println("getDoorX()");
        System.out.println("Room X: " + room.getX());
        System.out.println("Room Y: " + room.getY());

        String side = door.substring(0,1);
        int cell = Integer.parseInt(door.substring(1));
        int xPos = room.getX();

        if(side.equals("t") || side.equals("b")) {
            return xPos + cell - 1;
        } else if (side.equals("r")){
            return xPos + room.getWIDTH() - 1;
        } else {
            return xPos;
        }
    }

    public ArrayList<String> getDoors(Room room, String side) {
        ArrayList<String> matchingDoors = new ArrayList<>();
        for(String door : room.getDoors()) {
            if(door.substring(0,1).equals(side)) {
                matchingDoors.add(door);
            }
        }
        return matchingDoors;
    }

    private boolean hasDoor(Room room, String side) {
        for(String door : room.getDoors()) {
            if(door.substring(0,1).equals(side)) {
                return true;
            }
        }
        return false;
    }

    private boolean doorsFit(Room room, int xPos, int yPos, String entranceSide) {
        System.out.println("doorsFit()");
        for(String door : room.getDoors()) {
            String side = door.substring(0,1);
            int cell = Integer.parseInt(door.substring(1));

            if(!side.substring(0,1).equals(entranceSide)) {
                int x = -1;
                int y = -1;

                switch (side) {
                    case "l" :  System.out.println("Testing door: " + door);
                                x = xPos - 1;
                                y = yPos + cell - 1;
                                break;
                    case "r" :  System.out.println("Testing door: " + door);
                                x = xPos + room.getWIDTH();
                                y = yPos + cell - 1;
                                break;
                    case "t" :  System.out.println("Testing door: " + door);
                                x = xPos + cell - 1;
                                y = yPos + room.getHEIGHT();
                                break;
                    case "b" :  System.out.println("Testing door: " + door);
                                x = xPos + cell - 1;
                                y = yPos -1;
                                break;
                }

                if(x < 0 || y < 0 || x > 100 || y > 100) {
                    System.out.println("Failed door test with door " + door);
                    System.out.println("X: " + x);
                    System.out.println("Y: " + y);
                    if(x >= 0 && x < 100 && y >= 0 && y < 100) {
                        System.out.println("Room blocking door: " + roomMap[x][y]);
                    } else {
                        System.out.println("Failed coordinates");
                    }
                    return false;
                }

                if(roomMap[x][y] != -1) {
                    int oppositeRoomNumber = roomMap[x][y];
                    Room oppositeRoom = rooms.get(oppositeRoomNumber);
                    ArrayList<String> oppositeRoomDoors = oppositeRoom.getDoors();

                    for(String oppositeRoomDoor : oppositeRoomDoors) {

                        String oppositeDoorSide = oppositeRoomDoor.substring(0,1);
                        int oppositeDoorCell = Integer.parseInt(oppositeRoomDoor.substring(1));

                        int oppositeRoomX = oppositeRoom.getX();
                        int oppositeRoomY = oppositeRoom.getY();

                        int oppositeDoorX = oppositeRoomX;
                        int oppositeDoorY = oppositeRoomY;

                        switch (oppositeDoorSide) {
                            case "l" :  oppositeDoorY = oppositeRoomY + oppositeDoorCell - 1;
                                        break;
                            case "r" :  oppositeDoorX = oppositeRoomX + oppositeRoom.getWIDTH() - 1;
                                        oppositeDoorY = oppositeRoomY + oppositeDoorCell - 1;
                                        break;
                            case "b" :  oppositeDoorX = oppositeRoomX + oppositeDoorCell - 1;
                                        break;
                            case "t" :  oppositeDoorX = oppositeRoomX + oppositeDoorCell - 1;
                                        oppositeDoorY = oppositeRoomY + oppositeRoom.getHEIGHT() - 1;
                        }

                        if(x == oppositeDoorX && y == oppositeDoorY) {
                            System.out.println("DOORS MATCHED!!!");
                            return true;
                        }
                    }
                    System.out.println("DOORS DIDN'T MATCH :(");
                    return false;
                }
            }
        }

        System.out.println("doorsFit() succeeded!");
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
        destroyBodies();
    }

    /*Saves the current positions of the enemies in this room, and then destroys their bodies.
        If room is re-entered, their bodies will be created at their old positions.
         */
    private void destroyBodies(){

        if(rooms.get(currentRoomNbr).getEnemyControllers() != null) {

            for (int i = 0; i < rooms.get(currentRoomNbr).getEnemyControllers().size(); i++) {
                EnemyController enemyController = rooms.get(currentRoomNbr).getEnemyControllers().get(i);
                Enemy enemy = enemyController.getEnemy();

                /* I found the PPM multiplication necessary but it is a bit strange.
            Should probably look into bo2d/rendering conversions. */
                float x = enemy.getX() * PPM;
                float y = enemy.getY() * PPM;

                enemy.setPosition(x, y);

            }

        }

        for(Body body : BODIES) {
            if(body.getUserData() != null && body.getUserData() instanceof EnemyController) {
                WorldConstants.CURRENT_WORLD.destroyBody(body);
            }
        }
    }
    private void rebuildWorld() {
        System.out.println("rebuildWorld: current room number: " + currentRoomNbr);
        rooms.get(currentRoomNbr).initRoom();

        getCurrentRoom().getTiledHandler().setHeroPosition(entrance);
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
                    currentRoomX = x;
                    currentRoomY = y;
                    return;
                }
            }
        }
    }
}
