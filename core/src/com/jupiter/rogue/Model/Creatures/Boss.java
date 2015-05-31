package com.jupiter.rogue.Model.Creatures;

import com.jupiter.rogue.Utils.Enums.Direction;
import com.jupiter.rogue.Utils.Enums.MovementState;

/**
 * Created by hilden on 2015-05-30.
 */
@lombok.Data
public class Boss extends Enemy {

    private boolean attackInProgress1;
    private boolean attackInProgress2;

    private int attackHitBox2Width;
    private int attackHitBox2Height;
    private int attackHitBox2X;
    private int attackHitBox2Y;

    public Boss(float xPos, float yPos, int level, boolean elite) {

        super(2000, 2000, 60, 10000, 0, 0, false, xPos, yPos, level, elite);

        this.enemyType = "boss";
        this.attackHitBoxWidth = 2000;
        this.attackHitBoxHeight = 600;
        this.attackHitBoxX = 0;
        this.attackHitBoxY = -50;

        this.attackHitBox2Width = 2000;
        this.attackHitBox2Height = 600;
        this.attackHitBox2X = 0;
        this.attackHitBox2Y = 150;

        this.bodyWidth = 500;
        this.bodyHeight = 500;
        this.bodyY = 300f;

        this.xpValue = 1000;
        this.direction = Direction.LEFT;
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
