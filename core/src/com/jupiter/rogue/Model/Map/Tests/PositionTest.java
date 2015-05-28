package com.jupiter.rogue.Model.Map.Tests;

import com.jupiter.rogue.Utils.Position;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Oskar on 2015-05-28.
 */
public class PositionTest {

    Position pos = new Position(50, 50);

    @Test
    public void testChangePosition() throws Exception {
        pos.changePosition(25,25);
        assertEquals(pos.getXPos(), 75, 0.1);
        assertEquals(pos.getYPos(), 75, 0.1);

    }
}