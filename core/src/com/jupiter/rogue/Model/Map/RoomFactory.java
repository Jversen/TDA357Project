package com.jupiter.rogue.Model.Map;

import java.util.ArrayList;

/**
 * Created by Johan on 27/04/15.
 */
public class RoomFactory {

    // number of rooms of each type without en exit
    public static final int ROOMS_WITHOUT_EXIT_DOOR_LEFT = 1;
    public static final int ROOMS_WITHOUT_EXIT_DOOR_RIGHT = 2;
    public static final int ROOMS_WITHOUT_EXIT_DOOR_TOP = 1;
    public static final int ROOMS_WITHOUT_EXIT_DOOR_BOTTOM = 1;

    // number of 1x1 rooms with two doors used for connecting two larger rooms when there is no space for anything better
    public static final int CONNECTION_ROOMS_LEFT = 7;
    public static final int CONNECTION_ROOMS_RIGHT = 7;
    public static final int CONNECTION_ROOMS_TOP = 7;
    public static final int CONNECTION_ROOMS_BOTTOM = 7;


    // number of rooms of each type with an exit;
    public static final int ROOMS_WITH_EXIT_DOOR_LEFT = 6;
    public static final int ROOMS_WITH_EXIT_DOOR_RIGHT = 6;
    public static final int ROOMS_WITH_EXIT_DOOR_TOP = 2;
    public static final int ROOMS_WITH_EXIT_DOOR_BOTTOM = 1;

    public static Room getRoom(String entrance, int roomNumber, boolean exit) {
        ArrayList<String> doors = new ArrayList<>();
        if(entrance.equals("l") && !exit) {
            switch (roomNumber) { //will be random once more rooms are added
                case 1 :    doors.add("l1");
                            return new Room("Rooms/CastleRooms/11Dl1.tmx", 1, 1, doors);
                //more to be added
                default : return null;
            }
        }
        if(entrance.equals("r") && !exit) {
            switch (roomNumber) {
                case 1 :    doors.add("r1");
                            return new Room("Rooms/12Dr1.tmx", 2, 1, doors);
                case 2 :    doors.add("r1");
                            return new Room("Rooms/CastleRooms/11Dr1.tmx", 1, 1, doors);

                default : return null;
            }
        }
        if(entrance.equals("t") && !exit) {
            switch (roomNumber) {
                case 1 :    doors.add("t1");
                            return new Room("Rooms/CastleRooms/11Dt1.tmx", 1, 1, doors);

                default : return null;
            }
        }
        if(entrance.equals("b") && !exit) {
            switch (roomNumber) {
                case 1 :    doors.add("b1");
                        return new Room("Rooms/CastleRooms/11Db1.tmx", 1, 1, doors);

                default : return null;
            }
        }
        if(entrance.equals("l") && exit) {
            switch (roomNumber) { //will be random once more rooms are added
                case 1 :    doors.add("l1");
                            doors.add("r1");
                            return new Room("Rooms/13Dl1r1.tmx", 3, 1, doors);
                case 2 :    doors.add("l1");
                            doors.add("r1");
                            return new Room("Rooms/17Dl1r1.tmx", 7, 1, doors);
                case 3 :    doors.add("l1");
                            doors.add("r1");
                            doors.add("t1");
                            doors.add("b1");
                            return new Room("Rooms/24Dl1b1t1r1.tmx", 4, 2, doors);
                case 4 :    doors.add("l1");
                            doors.add("r2");
                            return new Room("Rooms/24Dl1r2.tmx", 4, 2, doors);
                case 5 :    doors.add("l1");
                            doors.add("r2");
                            doors.add("t1");
                            return new Room("Rooms/24Dl1r2t1.tmx", 4, 2, doors);
                case 6 :    doors.add("l1");
                            doors.add("r1");
                            return new Room("Rooms/24Dl1r1.tmx", 4, 2, doors);
                case 7 :    doors.add("l1");
                            doors.add("t1");
                            return new Room("Rooms/CastleRooms/11Dl1t1.tmx", 1, 1, doors);
                case 8 :    doors.add("l1");
                            doors.add("b1");
                            return new Room("Rooms/CastleRooms/11Dl1b1.tmx", 1, 1, doors);
                case 9 :    doors.add("l1");
                            doors.add("r1");
                            return new Room("Rooms/CastleRooms/11Dl1r1.tmx", 1, 1, doors);
                case 10 :   doors.add("l1");
                            doors.add("t1");
                            doors.add("b1");
                            return new Room("Rooms/CastleRooms/11Dl1t1b1.tmx", 1, 1, doors);
                case 11 :   doors.add("l1");
                            doors.add("r1");
                            doors.add("b1");
                            return new Room("Rooms/CastleRooms/11Dl1b1r1.tmx", 1, 1, doors);
                case 12 :   doors.add("l1");
                            doors.add("r1");
                            doors.add("t1");
                            return new Room("Rooms/CastleRooms/11Dl1t1r1.tmx", 1, 1, doors);
                case 13 :   doors.add("l1");
                            doors.add("r1");
                            doors.add("t1");
                            doors.add("b1");
                            return new Room("Rooms/CastleRooms/11Dl1b1r1t1.tmx", 1, 1, doors);

                //more to be added
                default :
                    System.out.println("default");
                    return null;
            }
        }
        if(entrance.equals("r") && exit) {
            switch (roomNumber) { //will be random once more rooms are added

                case 1 :    doors.add("l1");
                            doors.add("r1");
                            return new Room("Rooms/13Dl1r1.tmx", 3, 1, doors);
                case 2 :    doors.add("l1");
                            doors.add("r1");
                            return new Room("Rooms/17Dl1r1.tmx", 7, 1, doors);
                case 3 :    doors.add("l1");
                            doors.add("r1");
                            doors.add("t1");
                            doors.add("b1");
                            return new Room("Rooms/24Dl1b1t1r1.tmx", 4, 2, doors);
                case 4 :    doors.add("l1");
                            doors.add("r2");
                            return new Room("Rooms/24Dl1r2.tmx", 4, 2, doors);
                case 5 :    doors.add("l1");
                            doors.add("r2");
                            doors.add("t1");
                            return new Room("Rooms/24Dl1r2t1.tmx", 4, 2, doors);
                case 6 :    doors.add("l1");
                            doors.add("r1");
                            return new Room("Rooms/24Dl1r1.tmx", 4, 2, doors);
                case 7 :    doors.add("r1");
                            doors.add("b1");
                            return new Room("Rooms/CastleRooms/11Db1r1.tmx", 1, 1, doors);
                case 8 :    doors.add("t1");
                            doors.add("r1");
                            return new Room("Rooms/CastleRooms/11Dt1r1.tmx", 1, 1, doors);
                case 9 :    doors.add("l1");
                            doors.add("r1");
                            return new Room("Rooms/CastleRooms/11Dl1r1.tmx", 1, 1, doors);
                case 10 :   doors.add("t1");
                            doors.add("r1");
                            doors.add("b1");
                            return new Room("Rooms/CastleRooms/11Dt1r1b1.tmx", 1, 1, doors);
                case 11 :   doors.add("l1");
                            doors.add("r1");
                            doors.add("b1");
                            return new Room("Rooms/CastleRooms/11Dl1b1r1.tmx", 1, 1, doors);
                case 12 :   doors.add("l1");
                            doors.add("r1");
                            doors.add("t1");
                            return new Room("Rooms/CastleRooms/11Dl1t1r1.tmx", 1, 1, doors);
                case 13 :   doors.add("l1");
                            doors.add("r1");
                            doors.add("t1");
                            doors.add("b1");
                            return new Room("Rooms/CastleRooms/11Dl1b1r1t1.tmx", 1, 1, doors);
                //more to be added
                            default :
                            System.out.println("default");
                            return null;
            }
        }
        if(entrance.equals("t") && exit) {
            switch (roomNumber) { //will be random once more rooms are added

                case 1 :    doors.add("l1");
                            doors.add("r1");
                            doors.add("t1");
                            doors.add("b1");
                            return new Room("Rooms/24Dl1b1t1r1.tmx", 4, 2, doors);
                case 2 :    doors.add("l1");
                            doors.add("r2");
                            doors.add("t1");
                            return new Room("Rooms/24Dl1r2t1.tmx", 4, 2, doors);
                case 3 :    doors.add("l1");
                            doors.add("t1");
                            return new Room("Rooms/CastleRooms/11Dl1t1.tmx", 1, 1, doors);
                case 4 :    doors.add("t1");
                            doors.add("b1");
                            return new Room("Rooms/CastleRooms/11Dt1b1.tmx", 1, 1, doors);
                case 5 :    doors.add("t1");
                            doors.add("r1");
                            return new Room("Rooms/CastleRooms/11Dt1r1.tmx", 1, 1, doors);
                case 6 :    doors.add("t1");
                            doors.add("r1");
                            doors.add("b1");
                            return new Room("Rooms/CastleRooms/11Dt1r1b1.tmx", 1, 1, doors);
                case 7 :    doors.add("l1");
                            doors.add("t1");
                            doors.add("b1");
                            return new Room("Rooms/CastleRooms/11Dl1t1b1.tmx", 1, 1, doors);
                case 8 :    doors.add("l1");
                            doors.add("r1");
                            doors.add("t1");
                            return new Room("Rooms/CastleRooms/11Dl1t1r1.tmx", 1, 1, doors);
                case 9 :    doors.add("l1");
                            doors.add("r1");
                            doors.add("t1");
                            doors.add("b1");
                            return new Room("Rooms/CastleRooms/11Dl1b1r1t1.tmx", 1, 1, doors);
                default :
                    System.out.println("default");
                    return null;
            }
        }
        if(entrance.equals("b") && exit) {
            switch (roomNumber) { //will be random once more rooms are added
                case 1 :    doors.add("l1");
                            doors.add("r1");
                            doors.add("t1");
                            doors.add("b1");
                            return new Room("Rooms/24Dl1b1t1r1.tmx", 4, 2, doors);
                case 2 :    doors.add("r1");
                            doors.add("b1");
                            return new Room("Rooms/CastleRooms/11Db1r1.tmx", 1, 1, doors);
                case 3 :    doors.add("t1");
                            doors.add("b1");
                            return new Room("Rooms/CastleRooms/11Dt1b1.tmx", 1, 1, doors);
                case 4 :    doors.add("l1");
                            doors.add("b1");
                            return new Room("Rooms/CastleRooms/11Dl1b1.tmx", 1, 1, doors);
                case 5 :    doors.add("t1");
                            doors.add("r1");
                            doors.add("b1");
                            return new Room("Rooms/CastleRooms/11Dt1r1b1.tmx", 1, 1, doors);
                case 6 :    doors.add("l1");
                            doors.add("r1");
                            doors.add("b1");
                            return new Room("Rooms/CastleRooms/11Dl1b1r1.tmx", 1, 1, doors);
                case 7 :    doors.add("l1");
                            doors.add("b1");
                            doors.add("t1");
                            return new Room("Rooms/CastleRooms/11Dl1t1b1.tmx", 1, 1, doors);
                case 8 :    doors.add("l1");
                            doors.add("r1");
                            doors.add("t1");
                            doors.add("b1");
                            return new Room("Rooms/CastleRooms/11Dl1b1r1t1.tmx", 1, 1, doors);
                //more to be added
                default :
                    System.out.println("default");
                    return null;
            }
        }
        return null;
    }

    // getter for special rooms
    public static Room getRoom(String roomID) {
        ArrayList<String> doors = new ArrayList<>();
        switch (roomID) {
            case "StartingRoom":    doors.add("r1");
                                    return new Room("Rooms/StartingRoom.tmx", 2, 1, doors);
            case "BossRoom":     doors.add("l1");
                                    return new Room("Rooms/BossRoom.tmx", 4, 4, doors);
            default: return null;
        }
    }
}
