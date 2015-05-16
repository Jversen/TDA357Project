package com.jupiter.rogue.Model.Creatures;

/**
 * Created by Johan on 2015-05-10.
 */
public class Widow extends Enemy {
    
    public Widow(float xPos, float yPos, int level, boolean elite){
        super(100, 100, 25, 1, true, xPos, yPos, level, elite);
    }
}
