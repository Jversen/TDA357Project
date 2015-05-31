package com.jupiter.rogue.Model.Creatures;

import com.jupiter.rogue.Utils.Enums.Direction;

/**
 * Created by hilden on 2015-05-30.
 */
public class Boss extends Enemy {

    public Boss() {
        /**
         * creates a boss enemy.
         *@see com.jupiter.rogue.Model.Creatures.Enemy
         */
        super(2000, 2000, 60, 10000, 0, 0, false, 100, 100, 1, false);

        this.enemyType = "boss";
        this.attackHitBoxWidth = 2000;
        this.attackHitBoxHeight = 600;
        this.attackHitBoxX = 0;
        this.attackHitBoxY = 100;

        this.bodyWidth = 500;
        this.bodyHeight = 500;
        this.bodyY = 300f;

        this.xpValue = 1000;
        this.direction = Direction.LEFT;
    }
}
