package com.jupiter.rogue.Model.Creatures;



/**
 * Created by Johan on 2015-05-10.
 */
public class Widow extends Enemy {
    
    public Widow(float xPos, float yPos, int level, boolean elite){
        super(100, 100, 25, 25, 1, 6, true, xPos, yPos, level, elite);

        this.hitBoxWidth = 5;
        this.hitBoxHeight = 15;
        this.hitBoxX = 10;
        this.hitBoxY = 5;

        this.bodyWidth = 10;
        this.bodyHeight = 20;
    }
}
