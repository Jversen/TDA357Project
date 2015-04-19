package com.jupiter.rogue.Model.Creatures;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.jupiter.rogue.Model.Enums.Direction;
import com.jupiter.rogue.Model.Enums.MovementState;
import com.jupiter.rogue.Model.Map.Position;


/**
 * Created by Johan on 16/04/15.
 */
@lombok.Data
public abstract class Creature {

    protected Position position = new Position();
    protected int currentHealthPoints;
    protected int maxHealthPoints;
    protected int attackPoints;
    protected int movementSpeed;
    protected MovementState movementState = MovementState.STANDING;
    protected Direction direction = Direction.RIGHT;
    protected Texture spriteSheet;
    protected Sprite sprite;
    protected Animation animation;
    protected float scale; //sets creature sprite scaling constant
    protected Rectangle bounds = new Rectangle();
    protected int frameCols;
    protected int frameRows;

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position.setXPos(position.getXPos());
        this.position.setYPos(position.getYPos());
    }

    public void setPosition(float x, float y) {
        this.position.setXPos(x);
        this.position.setYPos(y);
    }

    public void setBounds(float x, float y, float w, float h){

        this.bounds.setX(x);
        this.bounds.setY(y);

        this.bounds.setWidth(w);
        this.bounds.setHeight(h);
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public float getBoundsX(){
        return this.bounds.getX();
    }
    public float getBoundsY(){
        return this.bounds.getY();
    }

    public void setSpriteSheet(Texture texture) {
        this.spriteSheet = texture;
    }


    public MovementState getMovementState() {
        return movementState;
    }

    public void setMovementState(MovementState movementState) {
        this.movementState = movementState;
    }

    public int getCurrentHealthPoints() {
        return currentHealthPoints;
    }

    public int getMaxHealthPoints() {
        return maxHealthPoints;
    }

    public void setHealthPoints(int HP) {
        if (this.maxHealthPoints >= HP || HP >= 0) {
            this.currentHealthPoints = HP;
        }
    }

    public void decreaseHealthPoints(int HP) {
        if (HP > 0) {
            this.currentHealthPoints -= HP;
        }
        if (currentHealthPoints < 0) {
            currentHealthPoints = 0;
        }
    }
}
