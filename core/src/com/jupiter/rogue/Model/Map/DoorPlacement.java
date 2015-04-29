package com.jupiter.rogue.Model.Map;

import lombok.Data;

/**
 * Immutable class meant to hold the position of a door
 * Created by Johan on 29/04/15.
 */

@Data
public class DoorPlacement {
    private final int xCell;
    private final int yCell;
    private final char side;

    public DoorPlacement(int xCell, int yCell, char side) {
        this.xCell = xCell;
        this.yCell = yCell;
        this.side = side;
    }
}
