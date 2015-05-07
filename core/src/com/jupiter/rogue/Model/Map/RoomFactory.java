package com.jupiter.rogue.Model.Map;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Johan on 27/04/15.
 */
public class RoomFactory {

    //TODO break up method into smaller methods
    public static Room getRoom(int roomWidth, int roomHeight, String entrance, ArrayList<String> doorPositions) {
        Room room = null;                                   // Room to be created
        //TODO actually return the requested room;
        return new Room("Rooms/RoomSwitchingTest1.tmx");
        /*File roomFolder = new File("Rooms");                // The folder that holds all the tmx-files
        File[] tmxList = roomFolder.listFiles();            // Array that holds all the tmx files
        ArrayList<String> pathList = new ArrayList<>();     // List that will hold filepaths to all tmx-files that fit the input arguments

        for(File file : tmxList) {
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
                        pathList.add("Rooms/" + fileName);
                    }
                }
            }
        }

        if(pathList.size() > 1) {
            Random rand = new Random();
            String path = pathList.get(rand.nextInt(pathList.size()));
            room = new Room(path);
        }*/
    }
}
