package com.jupiter.rogue.Model.Creatures;

/**
 * Created by Johan on 2015-04-18.
 */
@lombok.Data
public class RedDeath extends Enemy {

    public RedDeath(float xPos, float yPos, int level, boolean elite) {

        super(100, 100, 25, 25, 1, 6, true, xPos, yPos, level, elite);

        this.hitBoxWidth = 3;
        this.hitBoxHeight = 5;
        this.hitBoxX = 6;
        this.hitBoxY = 5;

        this.bodyWidth = 10;
        this.bodyHeight = 20;
    }
}
