package com.jupiter.rogue.Model.World;

/**
 * Created by Johan on 2015-04-16.
 */
@lombok.Data
public class Position {

    private float xPos;
    private float yPos;

    public Position(){
        this.xPos = 0;
        this.yPos = 0;
    }

    public void setXPos(float xPos) {
        this.xPos = xPos;
    }

    public void setYPos(float yPos) {
        this.yPos = yPos;
    }

    public float getXPos() {
        return xPos;
    }

    public float getYPos() {
        return yPos;
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
