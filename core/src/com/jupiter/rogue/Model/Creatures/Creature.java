package com.jupiter.rogue.Model.Creatures;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.jupiter.rogue.Model.Enums.Direction;
import com.jupiter.rogue.Model.Enums.MovementState;
import com.jupiter.rogue.Model.Map.Position;


/**
 * Created by Johan on 16/04/15.
 */
@lombok.Data
public abstract class Creature {

    protected int currentHealthPoints;
    protected int maxHealthPoints;
    protected int attackPoints;
    protected float movementSpeed;
    protected float verticalSpeed;
    protected Position position;
    protected MovementState movementState = MovementState.STANDING;
    protected Direction direction = Direction.RIGHT;

    protected boolean creatureGrounded;
    protected boolean creatureFalling;

    protected Sprite sprite;
    protected Texture spriteSheet;
    protected TextureAtlas atlas;
    protected TextureRegion currentFrame;
    protected SpriteBatch spriteBatch;
    protected Animation animation;

    protected Body body;

    protected float stateTime;

    protected float scale; //sets creature sprite scaling constant
    protected Rectangle bounds = new Rectangle();

    public Position getPosition() {
        return new Position(body.getPosition().x, body.getPosition().y);
    }

    public void setPosition(float x, float y) {
        setX(x);
        setY(y);
    }

    public void setX(float x) {
        this.position.setXPos(x);
    }

    public float getX() {
        return this.position.getXPos();
    }

    public void setY(float y) {
        this.position.setYPos(y);
    }

    public float getY() {
        return this.position.getYPos();
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
