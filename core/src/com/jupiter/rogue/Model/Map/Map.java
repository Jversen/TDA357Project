package com.jupiter.rogue.Model.Map;

import com.badlogic.gdx.physics.box2d.Body;
import com.jupiter.rogue.Controller.EnemyController;
import com.jupiter.rogue.Controller.WorldController;
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
    private ArrayList<String> roomsAdded = new ArrayList<>();
    private int[][] roomMap = new int[100][100]; // TODO change to proper values
    private HashMap<RoomExit, RoomExit> exitMap = new HashMap<>();
    private int currentRoomNbr; //Variable to track what room the hero is currently in.
    private int currentRoomX;
    private int currentRoomY;
    private int nextRoom;
    private String exit;
    private String entrance;
    private boolean destroyRoom = false;
    private HashMap<Integer, String> entrances = new HashMap<>();

    // these arraylists store all recently added rooms
    private ArrayList<Integer> exitL = new ArrayList<>();
    private ArrayList<Integer> exitR = new ArrayList<>();
    private ArrayList<Integer> exitT = new ArrayList<>();
    private ArrayList<Integer> exitB = new ArrayList<>();
    private ArrayList<Integer> noExitL = new ArrayList<>();
    private ArrayList<Integer> noExitR = new ArrayList<>();
    private ArrayList<Integer> noExitT = new ArrayList<>();
    private ArrayList<Integer> noExitB = new ArrayList<>();
    private ArrayList<Integer> conRoomL = new ArrayList<>();
    private ArrayList<Integer> conRoomR = new ArrayList<>();
    private ArrayList<Integer> conRoomT = new ArrayList<>();
    private ArrayList<Integer> conRoomB = new ArrayList<>();



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
            EnemyController enemyController = getCurrentRoom().getEnemyControllers().get(i);
            if (!enemyController.getEnemy().isCreatureDead()) {
                enemyController.update();
            } else {
                enemyController.getBody().setUserData("dead");
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

        String entranceSide = "l";
        boolean moreRooms = true;
        boolean mapFinished = false;
        boolean firstRun = true;
        boolean bossRoomAdded = false;
        nextRoom = currentRoomNbr+1;

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

                if(left == 1 && !entranceSide.equals("l") && !doorAlreadyFitted("l")) {
                    findAddRoom(moreRooms, "l");
                }

                if(right == 1 && !entranceSide.equals("r") && !doorAlreadyFitted("r")) {
                    findAddRoom(moreRooms, "r");
                }

                if(top == 1 && !entranceSide.equals("t") && !doorAlreadyFitted("t")) {
                    findAddRoom(moreRooms, "t");
                }

                if(bottom == 1 && !entranceSide.equals("b") && !doorAlreadyFitted("b")) {
                    findAddRoom(moreRooms, "b");
                }

                for(String string : roomsAdded) {
                    System.out.println(string);
                }


            currentRoomNbr += 1;
            setNewRoomPosition();

            while(currentRoomNbr < rooms.size() && !doorsLeft()) {
                currentRoomNbr += 1;
                setNewRoomPosition();
            }

            if(rooms.size() > 40 && !bossRoomAdded) {
                moreRooms = false;
                addBossRoom();
                bossRoomAdded = true;
            }

            if(currentRoomNbr >= rooms.size()) {
                mapFinished = true;
            }
        }

        printMap();
        System.out.println();
        System.out.println("BOSSROOM ADDED: " + bossRoomAdded);
        currentRoomNbr = 0; // resets the value to the starting room
        currentRoomX = 0;
        currentRoomY = 50;

        /* Destroys all created enemy bodies, they will be recreated later when the player enters the right
        rooms
         */
        getCurrentRoom().getTiledHandler().destroy();

/*        for (int i = 0; i < rooms.size(); i++) {
            for (int j = 0; j < rooms.get(currentRoomNbr).getEnemyControllers().size(); j++) {
                rooms.get(currentRoomNbr).getEnemyControllers().get(j).destroyBody();
            }
        }*/

        getCurrentRoom().initRoom();


    }

    private void findAddRoom(boolean moreRooms, String side) {
        boolean exit = moreRooms;
        boolean connectionRoom = false;
        boolean notAdded = true;
        String entrance;
        int tries = 0;
        String oppositeSide = getOppositeSide(side);

        // tries to find a suitable room, jumps out of loop if one is found and added
        while(notAdded) {
            Room room = RoomFactory.getRoom(oppositeSide, getPseudoRandom(exit, connectionRoom, oppositeSide), exit);

            System.out.println("Trying to add room " + nextRoom + getString(side) + currentRoomNbr + "(" + room.getPath() + ")");

            int cellNr = getCellNr(getCurrentRoom(), side);

            int x = getX(room, oppositeSide, cellNr);
            int y = getY(room, oppositeSide, cellNr);

            if(roomFits(room, x, y, oppositeSide)) {

                entrance = oppositeSide+getCellNr(room, oppositeSide);
                entrances.put(nextRoom, entrance);

                addRoom(room, x, y, nextRoom);

                //TODO remove
                roomsAdded.add("Room " + nextRoom + "(" + room.getPath() + ") added" + getString(side) + "room " + currentRoomNbr);

                clearRoomNumbers();
                notAdded = false;

                //TODO remove
                printMap();
            } else {
                tries++;
                if(moreRooms) {
                    if(tries >= getRoomQuantity(true, false, oppositeSide)) {
                        connectionRoom = true;
                    }
                    if(tries >= getRoomQuantity(true, false, oppositeSide) + getRoomQuantity(true, true, oppositeSide)) {
                        //TODO remove
                        System.out.println("Tries: " + tries);
                        exit = false;
                    }
                } else if(tries >= getRoomQuantity(false, false, oppositeSide)) {
                    exit = true;
                    connectionRoom = true;
                }
            }
        }
    }


    //TODO remove
    private String getString(String side) {
        switch (side) {
            case "l": return " TO THE LEFT OF ";
            case "r": return " TO THE RIGHT OF ";
            case "t": return " ABOVE ";
            case "b": return " BELOW ";
            default:  return " FAILED ";
        }
    }

    private int getRoomQuantity(boolean exit, boolean conDoor, String side) {
        if(conDoor) {
            switch (side) {
                case "l" : return RoomFactory.CONNECTION_ROOMS_LEFT;
                case "r" : return RoomFactory.CONNECTION_ROOMS_RIGHT;
                case "t" : return RoomFactory.CONNECTION_ROOMS_TOP;
                case "b" : return RoomFactory.CONNECTION_ROOMS_BOTTOM;
            }
        } else if (exit) {
            switch (side) {
                case "l" : return RoomFactory.ROOMS_WITH_EXIT_DOOR_LEFT;
                case "r" : return RoomFactory.ROOMS_WITH_EXIT_DOOR_RIGHT;
                case "t" : return RoomFactory.ROOMS_WITH_EXIT_DOOR_TOP;
                case "b" : return RoomFactory.ROOMS_WITH_EXIT_DOOR_BOTTOM;
            }
        } else {
            switch (side) {
                case "l" : return RoomFactory.ROOMS_WITHOUT_EXIT_DOOR_LEFT;
                case "r" : return RoomFactory.ROOMS_WITHOUT_EXIT_DOOR_RIGHT;
                case "t" : return RoomFactory.ROOMS_WITHOUT_EXIT_DOOR_TOP;
                case "b" : return RoomFactory.ROOMS_WITHOUT_EXIT_DOOR_BOTTOM;
            }
        }
        System.out.println("getRoomQuantity() failed");
        return -1;
    }

    private void clearRoomNumbers() {
        exitL.clear();
        exitR.clear();
        exitT.clear();
        exitB.clear();
        noExitL.clear();
        noExitR.clear();
        noExitT.clear();
        noExitB.clear();
        conRoomL.clear();
        conRoomR.clear();
        conRoomT.clear();
        conRoomB.clear();
    }

    private int getY(Room room, String side, int cellNr) {
        switch(side) {
            case "l" : return currentRoomY + cellNr - getCellNr(room, side);
            case "r" : return currentRoomY + cellNr - getCellNr(room, side);
            case "t" : return currentRoomY - room.getHEIGHT();
            case "b" : return currentRoomY + getCurrentRoom().getHEIGHT();
            default  : return -1;
        }
    }

    private int getX(Room room, String side, int cellNr) {
        switch(side) {
            case "l" : return currentRoomX + getCurrentRoom().getWIDTH();
            case "r" : return currentRoomX - room.getWIDTH();
            case "t" : return currentRoomX + cellNr - getCellNr(room, side);
            case "b" : return currentRoomX + cellNr - getCellNr(room, side);
            default  : return -1;
        }
    }


    private void addBossRoom() {
        System.out.println("addBossRoom");

        ArrayList<Integer> excludedRooms = new ArrayList<>();
        Room bossRoom = RoomFactory.getRoom("BossRoom");
        int tmp = currentRoomNbr;

        loop:
        for(int i = 0; i < rooms.size(); i++) {
            int rightmostRoom = getRightmostEmptyRoom(excludedRooms);
            System.out.println("Trying to add bossRoom (" + nextRoom + ", " + bossRoom.getPath() + ") by room " + rightmostRoom);
            if(rightmostRoom == -1) {
                System.out.println("addBossRoom failed");
                break loop;
            }
            excludedRooms.add(rightmostRoom);
            currentRoomNbr = rightmostRoom;
            setNewRoomPosition();

            int cellNr = getCellNr(getCurrentRoom(), "r");
            int x = getX(bossRoom, "l", cellNr);
            int y = getY(bossRoom, "l", cellNr);

            System.out.println("X: " + x);
            System.out.println("Y: " + y);


            if(roomFits(bossRoom, x, y, "l")) {
                String entrance = "l"+getCellNr(bossRoom, "l");
                entrances.put(nextRoom, entrance);
                addRoom(bossRoom, x, y, nextRoom);
                roomsAdded.add("Room " + nextRoom + "(" + bossRoom.getPath() + ") added TO THE RIGHT room " + currentRoomNbr);
                System.out.println("BossRoom added!");
                break loop;
            } else {
                System.out.println("BossRoom not added.");
            }
        }

        currentRoomNbr = tmp;
        setNewRoomPosition();
    }

    private int getRightmostEmptyRoom(ArrayList<Integer> excludedRooms) {
        for(int x = 99; x >= 0; x--) {
            for(int y = 99; y >= 0; y--) {
                if(roomMap[x][y] != -1 && !excludedRooms.contains(roomMap[x][y])) {
                    Room room = rooms.get(roomMap[x][y]);
                    ArrayList<String> doors = room.getDoors();
                    for(String door : doors) {
                        if(door.substring(0,1).equals("r") && doorsLeft()) {
                            return roomMap[x][y];
                        }
                    }
                }
            }
        }
        System.out.println("no empty rooms left");
        return -1;
    }

    private int getPseudoRandom(boolean exit, boolean connectionRoom, String side) {
        switch(side) {
            case "l" : return getPseudoRandomL(exit, connectionRoom);
            case "r" : return getPseudoRandomR(exit, connectionRoom);
            case "t" : return getPseudoRandomT(exit, connectionRoom);
            case "b" : return getPseudoRandomB(exit, connectionRoom);
            default  : return -1;
        }
    }

    private int getPseudoRandomL(boolean exit, boolean connectiveRoom) {
        System.out.println("getPseudoRandomL (" + exit + ", " + connectiveRoom + ")");
        System.out.println("exitL: " + exitL);
        System.out.println("noExitL: " + noExitL);
        System.out.println("conRoomL: " + conRoomL);
        Random rand = new Random();
        if(exit && !connectiveRoom) {
            System.out.println("Trying to find roomnumber with exit");
            while(exitL.size()<RoomFactory.ROOMS_WITH_EXIT_DOOR_LEFT){
                int room = rand.nextInt(RoomFactory.ROOMS_WITH_EXIT_DOOR_LEFT) + 1;
                if(!exitL.contains(room)) {
                    System.out.println("Room " + room + "added");
                    exitL.add(room);
                    return room;
                }
            }
            System.out.println("All");
        } else if (exit && connectiveRoom){
            System.out.println("Trying to find roomnumber to connection room");
            for(int i = RoomFactory.ROOMS_WITH_EXIT_DOOR_LEFT + 1; i <= RoomFactory.ROOMS_WITH_EXIT_DOOR_LEFT + RoomFactory.CONNECTION_ROOMS_LEFT; i++) {
                if(!conRoomL.contains(i)) {
                    System.out.println("Room " + i + "added");
                    conRoomL.add(i);
                    return i;
                }
            }
        } else {
            System.out.println("Trying to find roomnumber without exit");
            while(noExitL.size() < RoomFactory.ROOMS_WITHOUT_EXIT_DOOR_LEFT) {
                int room = rand.nextInt(RoomFactory.ROOMS_WITHOUT_EXIT_DOOR_LEFT) + 1;
                if(!noExitL.contains(room)) {
                    System.out.println("Room " + room + "added");
                    noExitL.add(room);
                    return room;
                }
            }
        }
        System.out.println("getPseudoRandomL() FAILED");
        System.out.println("exitL: " + exitL);
        System.out.println("noExitL: " + noExitL);
        System.out.println("conRoomL: " + conRoomL);
        return -1;
    }

    private int getPseudoRandomR(boolean exit, boolean connectiveRoom) {
        System.out.println("getPseudoRandomR (" + exit + ", " + connectiveRoom + ")");
        System.out.println("exitR: " + exitR);
        System.out.println("noExitR: " + noExitR);
        System.out.println("conRoomR: " + conRoomR);
        Random rand = new Random();
        if(exit && !connectiveRoom) {
            System.out.println("Trying to find roomnumber with exit");
            while(exitR.size()<RoomFactory.ROOMS_WITH_EXIT_DOOR_RIGHT){
                int room = rand.nextInt(RoomFactory.ROOMS_WITH_EXIT_DOOR_RIGHT) + 1;
                if(!exitR.contains(room)) {
                    System.out.println("Room " + room + "added");
                    exitR.add(room);
                    return room;
                }
            }
        } else if (exit && connectiveRoom){
            System.out.println("Trying to find roomnumber to connection room");
            for(int i = RoomFactory.ROOMS_WITH_EXIT_DOOR_RIGHT + 1; i <= RoomFactory.ROOMS_WITH_EXIT_DOOR_RIGHT + RoomFactory.CONNECTION_ROOMS_RIGHT; i++) {
                if(!conRoomR.contains(i)) {
                    System.out.println("Room " + i + "added");
                    conRoomR.add(i);
                    return i;
                }
            }
        } else {
            System.out.println("Trying to find roomnumber without exit");
            while(noExitR.size() < RoomFactory.ROOMS_WITHOUT_EXIT_DOOR_RIGHT) {
                int room = rand.nextInt(RoomFactory.ROOMS_WITHOUT_EXIT_DOOR_RIGHT) + 1;
                if(!noExitR.contains(room)) {
                    System.out.println("Room " + room + "added");
                    noExitR.add(room);
                    return room;
                }
            }
        }
        System.out.println("getPseudoRandomR() FAILED");
        System.out.println("exitR: " + exitR);
        System.out.println("noExitR: " + noExitR);
        System.out.println("conRoomR: " + conRoomR);
        return -1;
    }

    private int getPseudoRandomB(boolean exit, boolean connectiveRoom) {
        System.out.println("getPseudoRandomB (" + exit + ", " + connectiveRoom + ")");
        System.out.println("exitB: " + exitB);
        System.out.println("noExitB: " + noExitB);
        System.out.println("conRoomB: " + conRoomB);
        Random rand = new Random();
        if(exit && !connectiveRoom) {
            System.out.println("Trying to find roomnumber with exit");
            while(exitB.size()<RoomFactory.ROOMS_WITH_EXIT_DOOR_BOTTOM){
                int room = rand.nextInt(RoomFactory.ROOMS_WITH_EXIT_DOOR_BOTTOM) + 1;
                if(!exitB.contains(room)) {
                    System.out.println("Room " + room + "added");
                    exitB.add(room);
                    return room;
                }
            }
        } else if (exit && connectiveRoom){
            System.out.println("Trying to find roomnumber to connection room");
            for(int i = RoomFactory.ROOMS_WITH_EXIT_DOOR_BOTTOM + 1; i <= RoomFactory.ROOMS_WITH_EXIT_DOOR_BOTTOM + RoomFactory.CONNECTION_ROOMS_BOTTOM; i++) {
                if(!conRoomB.contains(i)) {
                    System.out.println("Room " + i + "added");
                    conRoomB.add(i);
                    return i;
                }
            }
        } else {
            System.out.println("Trying to find roomnumber without exit");
            while(noExitB.size() < RoomFactory.ROOMS_WITHOUT_EXIT_DOOR_BOTTOM) {
                int room = rand.nextInt(RoomFactory.ROOMS_WITHOUT_EXIT_DOOR_BOTTOM) + 1;
                if(!noExitB.contains(room)) {
                    System.out.println("Room " + room + "added");
                    noExitB.add(room);
                    return room;
                }
            }
        }
        System.out.println("getPseudoRandomB() FAILED");
        System.out.println("exitB: " + exitB);
        System.out.println("noExitB: " + noExitB);
        System.out.println("conRoomB: " + conRoomB);
        return -1;
    }

    private int getPseudoRandomT(boolean exit, boolean connectiveRoom) {
        System.out.println("getPseudoRandomT (" + exit + ", " + connectiveRoom + ")");
        System.out.println("exitT: " + exitT);
        System.out.println("noExitT: " + noExitT);
        System.out.println("conRoomT: " + conRoomT);
        System.out.println(exitT);
        Random rand = new Random();
        if(exit && !connectiveRoom) {
            System.out.println("Trying to find roomnumber with exit");
            while(exitT.size() < RoomFactory.ROOMS_WITH_EXIT_DOOR_TOP){
                int room = rand.nextInt(RoomFactory.ROOMS_WITH_EXIT_DOOR_TOP) + 1;
                if(!exitT.contains(room)) {
                    System.out.println("Room " + room + "added");
                    exitT.add(room);
                    return room;
                }
            }
        } else if (exit && connectiveRoom){
            System.out.println("Trying to find roomnumber to connection room");
            for(int i = RoomFactory.ROOMS_WITH_EXIT_DOOR_TOP + 1; i <= RoomFactory.ROOMS_WITH_EXIT_DOOR_TOP + RoomFactory.CONNECTION_ROOMS_TOP; i++) {
                if(!conRoomT.contains(i)) {
                    System.out.println("Room " + i + "added");
                    conRoomT.add(i);
                    return i;
                }
            }
        } else {
            System.out.println("Trying to find roomnumber without exit");
            while(noExitT.size() < RoomFactory.ROOMS_WITHOUT_EXIT_DOOR_TOP) {
                int room = rand.nextInt(RoomFactory.ROOMS_WITHOUT_EXIT_DOOR_TOP) + 1;
                if(!noExitT.contains(room)) {
                    System.out.println("Room " + room + "added");
                    noExitT.add(room);
                    return room;
                }
            }
        }
        System.out.println("getPseudoRandomT() FAILED");
        System.out.println("exitT: " + exitT);
        System.out.println("noExitT: " + noExitT);
        System.out.println("conRoomT: " + conRoomT);
        return -1;
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
        System.out.println("doorsLeft()");
        ArrayList<String> doors = getCurrentRoom().getDoors();
        for (String door : doors) {
            System.out.println(door);
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
        System.out.println("getCellNr()");
        ArrayList<String> doors = room.getDoors();

        System.out.println(doors);
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
        System.out.println("roomFits()");
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
        
        ArrayList<String> matchingDoors;
        if(!doorsFit(room, xPos, yPos, entranceSide)) {
            return false;
        } else {
            matchingDoors = matchingDoorSide(room, xPos, yPos, entranceSide);
        }

        if(isBlockingDoor(room, xPos, yPos, matchingDoors)) {
            System.out.println("DOOR BLOCKING TEST FAILED");
            return false;
        }


        return true;
    }

    private ArrayList<String> matchingDoorSide(Room room, int xPos, int yPos, String entranceSide) {
        System.out.println("matchingDoorSide()");

        ArrayList<String> matchingDoors = new ArrayList<>();
        for(String door : room.getDoors()) {
            String side = door.substring(0,1);
            int cell = Integer.parseInt(door.substring(1));

            doorfit:
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

                if(x < 0 || y < 0 || x >= 100 || y >= 100) {
                    return null;
                }

                if(roomMap[x][y] != -1) {
                    int oppositeRoomNumber = roomMap[x][y];
                    Room oppositeRoom = rooms.get(oppositeRoomNumber);
                    ArrayList<String> oppositeRoomDoors = oppositeRoom.getDoors();

                    for(String oppositeRoomDoor : oppositeRoomDoors) {
                        if(oppositeRoomDoor.substring(0,1).equals(getOppositeSide(side))) {
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

                            if(x == oppositeDoorX && y == oppositeDoorY && oppositeDoorSide.equals(getOppositeSide(side))) {
                                System.out.println("DOORS MATCHED!!! (matchingDoorSide)");
                                matchingDoors.add(oppositeRoomDoor);
                                break doorfit;
                            }
                        }
                    }
                }
            }
        }

        return matchingDoors;
    }

    private boolean isBlockingDoor(Room room, int xPos, int yPos, ArrayList<String> matchingDoors) {
        System.out.println("isBlockingDoor()");
        return doorBlockedBelow(room, xPos, yPos, matchingDoors) || doorBlockedAbove(room, xPos, yPos, matchingDoors) || doorBlockedRight(room, xPos, yPos, matchingDoors) || doorBlockedLeft(room, xPos, yPos, matchingDoors);
    }

    private boolean doorBlockedRight(Room room, int xPos, int yPos, ArrayList<String> matchingDoors) {
        System.out.println("doorBlockedRight()");
        System.out.println("xPos: " + xPos);
        System.out.println("yPos: " + yPos);
        ArrayList<Room> roomsToRight = new ArrayList<>();
        int lastRoomAdded = -1;

        // collect all rooms directly to the right of the room
        for(int y = yPos; y < yPos+room.getHEIGHT(); y++) {
            if(xPos+room.getWIDTH() < 100 && y < 100) {
                int roomNumber = roomMap[xPos+room.getWIDTH()][y];
                if(roomNumber != -1 && roomNumber != lastRoomAdded && roomNumber != currentRoomNbr) {
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
            if (!doorsBlocked(room, roomToRight, "l", xPos, yPos, matchingDoors)) {
                System.out.println("checkDoors() right failed");
                return true;
            }
        }

        return false;
    }

    private boolean doorBlockedLeft(Room room, int xPos, int yPos, ArrayList<String> matchingDoors) {
        System.out.println("doorBlockedLeft()");
        System.out.println("xPos: " + xPos);
        System.out.println("yPos: " + yPos);

        ArrayList<Room> roomsToLeft = new ArrayList<>();
        int lastRoomAdded = -1;

        // collect all rooms directly to the left of the room
        for(int y = yPos; y < yPos+room.getHEIGHT(); y++) {
            if(xPos-1 >= 0 && y < 100) {
                int roomNumber = roomMap[xPos - 1][y];
                if(roomNumber != -1 && roomNumber != lastRoomAdded && roomNumber != currentRoomNbr) {
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
            if (!doorsBlocked(room, roomToLeft, "r", xPos, yPos, matchingDoors)) {
                return true;
            }
        }

        return false;
    }


    private boolean doorBlockedAbove(Room room, int xPos, int yPos, ArrayList<String> matchingDoors) {
        System.out.println("doorBlockedAbove()");
        ArrayList<Room> roomsAbove = new ArrayList<>();
        int lastRoomAdded = -1;

        System.out.println("xPos: " + xPos);
        System.out.println("yPos: " + yPos);

        // collect all rooms directly to the left of the room
        for(int x = xPos; x < xPos+room.getWIDTH(); x++) {
            if(yPos + room.getHEIGHT() < 100 && x < 100) {
                int roomNumber = roomMap[x][yPos+room.getHEIGHT()];
                if(roomNumber != -1 && roomNumber != lastRoomAdded && roomNumber != currentRoomNbr) {
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
            if (!doorsBlocked(room, roomAbove, "b", xPos, yPos, matchingDoors)) {
                System.out.println("checkDoors failed with room " + roomAbove);
                return true;
            }
        }

        return false;
    }

    private boolean doorBlockedBelow(Room room, int xPos, int yPos, ArrayList<String> matchingDoors) {
        System.out.println("doorBlockedBelow()");
        System.out.println("xPos: " + xPos);
        System.out.println("yPos: " + yPos);
        ArrayList<Room> roomsBelow = new ArrayList<>();
        int lastRoomAdded = -1;

        // collect all rooms directly to the left of the room
        for(int x = xPos; x < xPos+room.getWIDTH(); x++) {
            if(yPos - 1 >= 0 && x < 100) {
                int roomNumber = roomMap[x][yPos - 1];
                if(roomNumber != -1 && roomNumber != lastRoomAdded && roomNumber != currentRoomNbr) {
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
            if (!doorsBlocked(room, roomBelow, "t", xPos, yPos, matchingDoors)) {
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

    private boolean doorsBlocked(Room room, Room roomToCheck, String side, int xPos, int yPos, ArrayList<String> doors) {
        System.out.println("doorsBlocked()");

        // no point in checking anything else if there are no doors to check
        if (!hasDoor(roomToCheck, side)) {
            System.out.println("Room has no doors that can interfere");
            return true;
        }
        System.out.println("Doors to ignore:");
        if(doors != null) {
            System.out.println(doors);
        }

        // gathers all doors pointing towards the current room
        ArrayList<String> matchingDoors = getDoors(roomToCheck, side);
        System.out.println("Doors to check in opposite room:");
        System.out.println(matchingDoors);

        for (String door : matchingDoors) {
            int x = getDoorX(roomToCheck, door);
            int y = getDoorY(roomToCheck, door);

            System.out.println("Door: " + door);

            if(side.equals("l") || side.equals("r")) {
                if(!(y < yPos || y >= yPos + room.getHEIGHT())) {
                    System.out.println("doorsBlocked() failed side: " + side);
                    System.out.println("Y: " + y);
                    System.out.println("Room height: " + room.getHEIGHT());
                    System.out.println("yPos: " + yPos + ", yPos+roomHeight: " + (yPos+room.getHEIGHT()));
                    if(doors != null) {
                        if(!doors.contains(door)) {
                            System.out.println("doesn't contain side " + side);
                            return false;
                        } else {
                            System.out.println("failed, but doors fit so it's ok");
                            return true;
                        }
                    } else {
                        System.out.println("no matching doors to check");
                        return false;
                    }
                }
            } else {
                if(!(x < xPos || x >= xPos + room.getWIDTH())) {
                    System.out.println("doorsBlocked() failed side: " + side);
                    System.out.println("X: " + x);
                    System.out.println("Room width: " + room.getWIDTH());
                    System.out.println("xPos: " + xPos + ", xPos+roomWidth: " + (xPos+room.getWIDTH()));
                    if(doors != null) {
                        if(!doors.contains(door)) {
                            System.out.println("doesn't contain side " + side);
                            return false;
                        } else {
                            System.out.println("failed, but doors fit so it's ok");
                            return true;
                        }
                    } else {
                        System.out.println("no matching doors to check");
                        return false;
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
        System.out.println("hasDoor()");
        for(String door : room.getDoors()) {
            if(door.substring(0,1).equals(side)) {
                System.out.println("has doors!");
                return true;
            }
        }
        return false;
    }

    private boolean doorsFit(Room room, int xPos, int yPos, String entranceSide) {
        System.out.println("doorsFit()");
        boolean retval = true;
        for(String door : room.getDoors()) {
            String side = door.substring(0,1);
            int cell = Integer.parseInt(door.substring(1));

            doorfit:
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

                if(x < 0 || y < 0 || x >= 100 || y >= 100) {
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
                    System.out.println("found room blocking door");
                    int oppositeRoomNumber = roomMap[x][y];
                    Room oppositeRoom = rooms.get(oppositeRoomNumber);
                    ArrayList<String> oppositeRoomDoors = oppositeRoom.getDoors();

                    boolean foundMatch = false;

                    for(String oppositeRoomDoor : oppositeRoomDoors) {
                        if(oppositeRoomDoor.substring(0,1).equals(getOppositeSide(side))) {
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

                            if(x == oppositeDoorX && y == oppositeDoorY && oppositeDoorSide.equals(getOppositeSide(side))) {
                                System.out.println("DOORS MATCHED (" + oppositeRoomDoor + ")");
                                System.out.println("x: " + x + ", oX: " + oppositeDoorX);
                                System.out.println("y: " + y + ", oY: " + oppositeDoorY);
                                foundMatch = true;
                                break doorfit;
                            } else {
                                System.out.println("DOORS DIDN'T MATCH (" + oppositeRoomDoor + ")");
                                retval = false;
                            }
                        }
                    }
                    if(!foundMatch) {
                        return false;
                    }
                }
            }
        }
        return retval;
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
        room.setX(xPos);
        room.setY(yPos);
        nextRoom += 1;
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

        String entranceSide = getOppositeSide(side);

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
