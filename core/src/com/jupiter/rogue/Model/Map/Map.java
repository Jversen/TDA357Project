package com.jupiter.rogue.Model.Map;

import com.badlogic.gdx.physics.box2d.Body;
import com.jupiter.rogue.Controller.EnemyController;
import com.jupiter.rogue.Model.Creatures.Enemy;
import com.jupiter.rogue.Utils.WorldConstants;

import java.util.ArrayList;
import java.util.HashMap;

import static com.jupiter.rogue.Utils.WorldConstants.BODIES;
import static com.jupiter.rogue.Utils.WorldConstants.PPM;
import static com.jupiter.rogue.Utils.WorldConstants.TILE_SIZE;

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

        for (int i = 0; i < getCurrentRoom().getEnemyControllers().size(); i++) {
            getCurrentRoom().getEnemyControllers().get(i).update();
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

                if(left == 1 && !entranceSide.equals("l") && !doorAlreadyFitted("l")) {
                    boolean tmpMoreRooms = moreRooms;
                    boolean notAdded = true;
                    int tries = 0;

                    // tries to find a suitable room, jumps out of loop if one is found and added or if no suitable room is found in 10 tries
                    while(notAdded) {
                        Room leftRoom = RoomFactory.getRoom("r", tmpMoreRooms);
                        System.out.println("Trying to add room " + nextRoom + " TO THE LEFT of " + currentRoomNbr);
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
                            if(tries > 10) {
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
                        Room rightRoom = RoomFactory.getRoom("l", tmpMoreRooms);
                        System.out.println("Trying to add room " + nextRoom + " TO THE RIGHT of " + currentRoomNbr);

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
                            if(tries > 10) {
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
                        Room topRoom = RoomFactory.getRoom("b", tmpMoreRooms);
                        System.out.println("Trying to add room " + nextRoom + "ABOVE" + currentRoomNbr);

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
                            if(tries > 10) {
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
                        Room bottomRoom = RoomFactory.getRoom("t", tmpMoreRooms);
                        System.out.println("Trying to add room " + nextRoom + "BELOW" + currentRoomNbr);
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

            if(rooms.size() > 20) {
                moreRooms = false;
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

        int doorX = -1;
        int doorY = -1;
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

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {

        }

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

        if(!doorsFit(room, xPos, yPos, entranceSide)) {
            return false;
        }


        return true;
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

        for(int i = 0; i < rooms.get(currentRoomNbr).getEnemyControllers().size(); i++){
            EnemyController enemyController = rooms.get(currentRoomNbr).getEnemyControllers().get(i);
            Enemy enemy = enemyController.getEnemy();
            float x = enemy.getX() * PPM;
            float y = enemy.getY() * PPM;

            enemy.setPosition(x, y);
            /* I found the PPM multiplication necessary but it is a bit strange.
            Should probably look into bo2d/rendering conversions. */

        }
        for(Body body : BODIES) {
            if(body.getUserData() != null && body.getUserData().equals("enemy")) {
                WorldConstants.CURRENT_WORLD.destroyBody(body);
            }
        }
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
