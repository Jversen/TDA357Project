package com.jupiter.rogue.Model.Creatures;

import com.jupiter.rogue.Utils.Enums.Direction;
import com.jupiter.rogue.Utils.Enums.MovementState;

/**
 * Created by hilden on 2015-05-30.
 */
@lombok.Data
public class Boss extends Enemy {

<<<<<<< HEAD
    public Boss() {
        /**
         * creates a boss enemy.
         *@see com.jupiter.rogue.Model.Creatures.Enemy
         */
        super(2000, 2000, 60, 10000, 0, 0, false, 100, 100, 1, false);
=======
    private boolean attackInProgress1;
    private boolean attackInProgress2;

    private int attackHitBox2Width;
    private int attackHitBox2Height;
    private int attackHitBox2X;
    private int attackHitBox2Y;

    public Boss(float xPos, float yPos, int level, boolean elite) {

        super(2000, 2000, 60, 10000, 0, 0, false, xPos, yPos, level, elite);
>>>>>>> 56346a09370a3c0d32686530e9c8f81bd428d4ee

        this.enemyType = "boss";
        this.attackHitBoxWidth = 2000;
        this.attackHitBoxHeight = 600;
        this.attackHitBoxX = 0;
        this.attackHitBoxY = -50;

        this.attackHitBox2Width = 2000;
        this.attackHitBox2Height = 600;
        this.attackHitBox2X = 0;
        this.attackHitBox2Y = 150;

        this.bodyWidth = 50;
        this.bodyHeight = 70;
        this.bodyY = 0f;

        this.xpValue = 1000;
        this.direction = Direction.RIGHT;
    }

    @Override
    public void relax() {
        this.setMovementState(MovementState.STANDING);
    }

    @Override
    public void attack() {
        this.setAttackInProgress1(true);
    }

    public void attack2() {
        this.setAttackInProgress2(true);
    }
}
