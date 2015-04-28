package com.jupiter.rogue.Model.Map;

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
}
