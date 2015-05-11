package com.jupiter.rogue.Model.Map;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Johan on 27/04/15.
 */
public class RoomFactory {

    //TODO break up method into smaller methods
    public static Room getRoom(int roomWidth, int roomHeight, ArrayList<String> doorPositions) {
        Room room = null;                                   // Room to be created
        //TODO actually return the requested room;
        /*File roomFolder = new File("Rooms");                // The folder that holds all the tmx-files
        File[] tmxList = roomFolder.listFiles();            // Array that holds all the tmx files
        ArrayList<String> pathList = new ArrayList<>();     // List that will hold filepaths to all tmx-files that fit the input arguments

        for(File file : tmxList) {

            if(!fileIsValid(file)) {
                continue;
            }

            String fileName = file.getName();

            int width = Character.getNumericValue(fileName.charAt(0));
            int height = Character.getNumericValue(fileName.charAt(1));

            String doorPositionsInFileName = fileName.substring(fileName.indexOf('D'));

            //checks if the requested room size matches the current file
            if(width == roomWidth && height == roomHeight) {
                // checks if the number of doors in the current file matches the number of doors requested
                if(doorPositionsInFileName.length() / 2 < doorPositions.size()) {
                    ArrayList<String> fileDoorPositions = new ArrayList<>();
                    int positionIndex = 0;

                    // parses each positionlabel from the file name
                    while(positionIndex <= doorPositionsInFileName.length()-2) {
                        fileDoorPositions.add(doorPositionsInFileName.substring(positionIndex, positionIndex + 2));
                        positionIndex += 2;
                    }

                    boolean validRoom = true;
                    for(int i = 0; i < doorPositions.size(); i++) {
                        if(!fileDoorPositions.contains(doorPositions.get(i))) {
                            validRoom = false;
                        }
                    }

                    if(validRoom) {
                        pathList.add("Rooms/" + fileName + ".tmx");
                    }
                }
            }
        }

        String path = null;

        if(pathList.size() > 1) {
            Random rand = new Random();
            path = pathList.get(rand.nextInt(pathList.size()));
        } else {
            path = pathList.get(0);
        }

        return new Room(path, roomWidth, roomHeight);*/
        return null;
    }

    private static boolean fileIsValid(File file) {
        if(file == null) {

        }
        if(file.isDirectory()) {
            return false;
        }
        String fileName = file.getName();
        try {
            String twoFirstLetters = fileName.substring(0,2);
            int i = Integer.parseInt(twoFirstLetters);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public static Room getRoom(String entrance, boolean exit) {
        Random rand = new Random();
        ArrayList<String> doors = new ArrayList<>();
        if(entrance.equals("l") && !exit) {
            switch (1) { //will be random once more rooms are added
                case 1 :    doors.add("l1");
                            return new Room("Rooms/11Dl1.tmx", 1, 1, doors);
                //more to be added
                default : return null;
            }
        }
        if(entrance.equals("r") && !exit) {
            switch (1) {
                case 1 :    doors.add("r1");
                            return new Room("Rooms/12Dr1.tmx", 2, 1, doors);

                default : return null;
            }
        }
        if(entrance.equals("t") && !exit) {
            switch (rand.nextInt(1)+1) {
                case 1 :    doors.add("t1");
                            return new Room("Rooms/11Dt1.tmx", 1, 1, doors);

                default : return null;
            }
        }
        if(entrance.equals("b") && !exit) {
            switch (rand.nextInt(1)+1) {
                case 1 :    doors.add("b1");
                        return new Room("Rooms/11Db1.tmx", 1, 1, doors);

                default : return null;
            }
        }
        if(entrance.equals("l") && exit) {
            switch (rand.nextInt(1)+1) { //will be random once more rooms are added
                /*case 1 :    doors.add("l1");
                            doors.add("b1");
                            return new Room("Rooms/11Dl1b1.tmx", 1, 1, doors);
                case 2 :    doors.add("l1");
                            doors.add("r1");
                            return new Room("Rooms/11Dl1r1.tmx", 1, 1, doors);
                case 3 :    doors.add("l1");
                            doors.add("r1");
                            return new Room("Rooms/13Dl1r1.tmx", 3, 1, doors);
                case 4 :    doors.add("l1");
                            doors.add("r1");
                            return new Room("Rooms/17Dl1r1.tmx", 7, 1, doors);
                case 5 :    doors.add("l1");
                            doors.add("r1");
                            doors.add("t1");
                            doors.add("b1");
                            return new Room("Rooms/24Dl1b1t1r1.tmx", 4, 2, doors);*/
                case 1 :    doors.add("l1");
                            doors.add("r2");
                            return new Room("Rooms/24Dl1r2.tmx", 4, 2, doors);
                /*case 7 :    doors.add("l1");
                            doors.add("l2");
                            doors.add("r1");
                            doors.add("r2");
                            return new Room("Rooms/24Dl1r2l2r1.tmx", 4, 2, doors);
                case 8 :    doors.add("l1");
                            doors.add("r2");
                            doors.add("t1");
                            return new Room("Rooms/24Dl1r2t1.tmx", 4, 2, doors);
                case 9 :    doors.add("l1");
                            doors.add("r2");
                            doors.add("t1");
                            doors.add("r1");
                            return new Room("Rooms/24Dl1r2t1r1.tmx", 4, 2, doors);
                case 10 :   doors.add("l1");
                            doors.add("r1");
                            return new Room("Rooms/24Dl1r1.tmx", 4, 2, doors);*/

                //more to be added
                default :
                    System.out.println("default");
                    return null;
            }
        }
        if(entrance.equals("r") && exit) {
            switch (rand.nextInt(3)+1) { //will be random once more rooms are added

                case 1 :    doors.add("l1");
                            doors.add("r1");
                            return new Room("Rooms/11Dl1r1.tmx", 1, 1, doors);

                case 2 :    doors.add("l1");
                            doors.add("r1");
                            return new Room("Rooms/13Dl1r1.tmx", 3, 1, doors);

                case 3 :    doors.add("l1");
                            doors.add("r1");
                            return new Room("Rooms/17Dl1r1.tmx", 7, 1, doors);

                /*case 4 :    doors.add("l1");
                            doors.add("r1");
                            doors.add("t1");
                            doors.add("b1");
                            return new Room("Rooms/24Dl1b1t1r1.tmx", 4, 2, doors);

                case 5 :    doors.add("l1");
                            doors.add("r2");
                            return new Room("Rooms/24Dl1r2.tmx", 4, 2, doors);

                case 6 :    doors.add("l1");
                            doors.add("l2");
                            doors.add("r1");
                            doors.add("r2");
                            return new Room("Rooms/24Dl1r2l2r1.tmx", 4, 2, doors);

                case 7 :    doors.add("l1");
                            doors.add("r2");
                            doors.add("t1");
                            return new Room("Rooms/24Dl1r2t1.tmx", 4, 2, doors);

                case 8 :    doors.add("l1");
                            doors.add("r2");
                            doors.add("t1");
                            doors.add("r1");
                            return new Room("Rooms/24Dl1r2t1r1.tmx", 4, 2, doors);
                case 9 :    doors.add("l1");
                            doors.add("r1");
                            return new Room("Rooms/24Dl1r1.tmx", 4, 2, doors);*/
                //more to be added
                default :
                    System.out.println("default");
                    return null;
            }
        }
        if(entrance.equals("t") && exit) {
            switch (rand.nextInt(3)+1) { //will be random once more rooms are added

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
                            doors.add("r2");
                            doors.add("t1");
                            doors.add("r1");
                            return new Room("Rooms/24Dl1r2t1r1.tmx", 4, 2, doors);
                //more to be added
                default :
                    System.out.println("default");
                    return null;
            }
        }
        if(entrance.equals("b") && exit) {
            switch (rand.nextInt(2)+1) { //will be random once more rooms are added
                case 1 :    doors.add("l1");
                            doors.add("b1");
                            return new Room("Rooms/11Dl1b1.tmx", 1, 1, doors);

                case 2 :    doors.add("l1");
                            doors.add("r1");
                            doors.add("t1");
                            doors.add("b1");
                            return new Room("Rooms/24Dl1b1t1r1.tmx", 4, 2, doors);
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
                                    return new Room("Rooms/StartingRoom.tmx", 4, 2, doors);
            case "BossRoomOne":     doors.add("l1");
                                    return new Room("Rooms/BossRoomOne.tmx", 4, 4, doors);
            default: return null;
        }
    }
}
