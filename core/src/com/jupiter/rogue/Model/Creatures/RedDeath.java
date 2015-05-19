package com.jupiter.rogue.Model.Creatures;

/**
 * Created by Johan on 2015-04-18.
 */
@lombok.Data
public class RedDeath extends Enemy {

    public RedDeath(float xPos, float yPos, int level, boolean elite) {

        super(100, 100, 25, 25, 1, 6, true, 200, 50, 1, false);

        bodyWidth = 10;
        bodyHeight = 20;

    }
}
