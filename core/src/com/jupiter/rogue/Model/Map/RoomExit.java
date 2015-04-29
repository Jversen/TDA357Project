package com.jupiter.rogue.Model.Map;

import lombok.Data;

/**
 * Created by Johan on 29/04/15.
 */
@Data
public class RoomExit {
    int roomID;
    int exit;

    public RoomExit(int roomID, int exit) {
        this.roomID = roomID;
        this.exit = exit;
    }
}
