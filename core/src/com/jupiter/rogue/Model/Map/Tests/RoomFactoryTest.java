package com.jupiter.rogue.Model.Map.Tests;

import com.jupiter.rogue.Model.Map.Room;
import org.junit.Test;
import com.jupiter.rogue.Model.Map.RoomFactory;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by Oskar on 2015-05-28.
 */
public class RoomFactoryTest {

    ArrayList<String> doorsTest = new ArrayList<>();


    @Test
    public void testGetRoom1() throws Exception {
        doorsTest.clear();
        Room r1a = new Room("Rooms/11Dl1.tmx", 1, 1, doorsTest);
        Room r1b = RoomFactory.getRoom("l", 1, false);
        assertEquals(r1a, r1b);

        doorsTest.clear();
        Room r2a = new Room("Rooms/11Dr1.tmx", 1, 1, doorsTest);
        Room r2b = RoomFactory.getRoom("r", 2, false);
        assertEquals(r2a, r2b);

        doorsTest.clear();
        Room nullRoom = RoomFactory.getRoom("t", 5, false);
        assertEquals(null, nullRoom);

        doorsTest.clear();
        Room r3a = new Room("Rooms/11Db1.tmx", 1, 1, doorsTest);
        Room r3b = RoomFactory.getRoom("b", 1, false);
        assertEquals(r3a, r3b);

        doorsTest.clear();
        doorsTest.add("l1");
        doorsTest.add("r1");
        doorsTest.add("t1");
        doorsTest.add("b1");
        Room r4a = new Room("Rooms/24Dl1b1t1r1.tmx", 4, 2, doorsTest);
        Room r4b = RoomFactory.getRoom("l", 3, true);
        assertEquals(r4a, r4b);

        doorsTest.clear();
        doorsTest.add("l1");
        doorsTest.add("r2");
        doorsTest.add("t1");
        Room r5a = new Room("Rooms/24Dl1r2t1.tmx", 4, 2, doorsTest);
        Room r5b = RoomFactory.getRoom("r", 5, true);
        assertEquals(r5a, r5b);

        doorsTest.clear();
        doorsTest.add("l1");
        doorsTest.add("r1");
        doorsTest.add("b1");
        Room r6a = new Room("Rooms/11Dl1r1b1.tmx", 1, 1, doorsTest);
        Room r6b = RoomFactory.getRoom("t", 7, true);
        assertEquals(r6a, r6b);

        doorsTest.clear();
        doorsTest.add("t1");
        doorsTest.add("r1");
        doorsTest.add("b1");
        Room r7a = new Room("Rooms/11Dr1t1b1.tmx", 1, 1, doorsTest);;
        Room r7b = RoomFactory.getRoom("b", 5, true);
        assertEquals(r7a, r7b);

        doorsTest.clear();
        Room r8 = RoomFactory.getRoom("nope", 99, true);
        assertEquals(r8, null);

    }

    @Test
    public void testGetRoom2() throws Exception {
        doorsTest.clear();
        doorsTest.add("r1");
        Room start1 = new Room("Rooms/StartingRoom.tmx", 2, 1, doorsTest);
        Room start2 = RoomFactory.getRoom("StartingRoom");
        assertEquals(start1, start2);

        doorsTest.clear();
        doorsTest.add("l1");
        Room boss1 = new Room("Rooms/BossRoom.tmx", 4, 4, doorsTest);
        Room boss2 = RoomFactory.getRoom("BossRoom");
        assertEquals(boss1, boss2);
    }
}