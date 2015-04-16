package com.jupiter.rogue.Model;

/**
 * Created by Johan on 2015-04-16.
 */
@lombok.Data
public class Position {

    private int xPos;
    private int yPos;

    public Position(){
        this.xPos = 0;
        this.yPos = 0;
    }

    public Position(int xPos, int yPos){
        this.xPos = xPos;
        this.yPos = yPos;

    }



    public void changePosition(int deltaX, int deltaY){
        this.xPos += deltaX;
        this.yPos += deltaY;

    }

}
