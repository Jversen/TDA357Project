package com.jupiter.rogue.Model.Creatures;

/**
 * Created by Johan on 2015-04-16.
 */
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

    public int getXPos() {
        return xPos;
    }

    public int getYPos() {
        return yPos;
    }

    public void setXPos(int yPos) {
        this.xPos = yPos;
    }

    public void setYPos(int xPos) {
        this.yPos = xPos;
    }

    public void changePosition(int deltaX, int deltaY){
        this.xPos += deltaX;
        this.yPos += deltaY;
    }

}
