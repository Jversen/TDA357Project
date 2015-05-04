package com.jupiter.rogue.Utils.libGDX;

import com.jupiter.rogue.Model.Enums.Direction;

/**
 * Created by Johan on 04/05/15.
 */
public interface Movement {
    public void jump();
    public void attack();
    public void move(Direction direction);
}
