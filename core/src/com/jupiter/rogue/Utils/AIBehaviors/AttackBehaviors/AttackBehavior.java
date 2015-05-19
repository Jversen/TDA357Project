package com.jupiter.rogue.Utils.AIBehaviors.AttackBehaviors;

import com.jupiter.rogue.Model.Enums.Direction;

/**
 * Created by Oskar on 2015-05-18.
 */

//Interface which is implemented by all AttackBehaviors.
public interface AttackBehavior {

    void attack(Direction direction);

}
