package com.jupiter.rogue.Controller.Behaviors.MoveBehaviors;

import com.jupiter.rogue.Utils.Enums.Direction;

/**
 * Created by Oskar on 2015-05-18.
 */

//interface which is implemented by all MoveBehaviors.
public interface MoveBehavior {

    void move(Direction direction, float moveSpeed);

}
