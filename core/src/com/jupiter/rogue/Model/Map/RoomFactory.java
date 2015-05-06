package com.jupiter.rogue.Model.Map;

import java.util.Random;

/**
 * Created by Johan on 27/04/15.
 */
public class RoomFactory {

    public Room getRoom(int roomNumber) {
        switch(roomNumber) {
            //case 1: return new Room("Rooms/BoxRoom.tmx");
            //case 2: return new Room("Rooms/BoxRoom2.tmx");
            default: return new Room();
        }
    }

    public Room getRandomRoom() {
        Random rand = new Random();
        return getRoom(rand.nextInt()%50);
    }

    /**
     *
     * @param width number of cells in width
     * @param height number of cells in height
     * @param entrance entrance to room from previous room
     * @param exit whether or not the room should have another exit
     * @return
     */

   // public Room getRoom(int width, int height, DoorPlacement entrance, boolean exit) {
     //   return new Room();
    //}

}
