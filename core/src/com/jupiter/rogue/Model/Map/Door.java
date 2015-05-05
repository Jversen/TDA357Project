package com.jupiter.rogue.Model.Map;

import lombok.Data;

/**
 * Created by Johan on 05/05/15.
 */
@Data
public class Door {
    private final int xCell;
    private final int yCell;
    private final char side;
    private DoorMap doorMap;

    public Door(int xCell, int yCell, char side, Door oppositeDoor) {
        this.xCell = xCell;
        this.yCell = yCell;
        this.side = side;
        this.doorMap = new DoorMap(this, oppositeDoor);
    }
}
