package com.jupiter.rogue.Model.Creatures;



/**
 * Created by Johan on 2015-05-10.
 */
public class Widow extends Enemy {
    
    public Widow(float xPos, float yPos, int level, boolean elite){
        super(100, 100, 25, 25, 1, 6, true, xPos, yPos, level, elite);

        this.attackHitBoxWidth = 8;
        this.attackHitBoxHeight = 15;
        this.attackHitBoxX = 20;
        this.attackHitBoxY = 25;

        this.bodyWidth = 10;
        this.bodyHeight = 25;
        this.bodyY = 0.5f;
    }
}
