package com.jupiter.rogue.Controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.physics.box2d.*;
import com.jupiter.rogue.Model.Creatures.Hero;
import com.jupiter.rogue.Model.Map.Position;
import com.jupiter.rogue.Model.Enums.Direction;
import com.jupiter.rogue.Model.Enums.MovementState;
import com.jupiter.rogue.Model.Map.WorldConstants;

import java.util.ArrayList;

/**
 * Created by hilden on 2015-04-17.
 */
@lombok.Data
public class HeroController {

    private Hero hero;
    WorldConstants constants = WorldConstants.getInstance();

    public HeroController() {
        initHero();
    }

    public void initHero() {

        hero = hero.getInstance();

        Position startPosition = constants.getHEROSTARTPOSITION();

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        bodyDef.position.set(startPosition.getXPos(), startPosition.getYPos());


        PolygonShape boundingBox = new PolygonShape();
        boundingBox.setAsBox(10, 10); //temporary values, should be dependent on sprite size

        // FixtureDef sets physical properties
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = boundingBox;
        fixtureDef.density = 1f;
        /*fixtureDef.friction = 1f;
        fixtureDef.restitution = 0.5f;*/

        Body body = constants.getWorld().createBody(bodyDef);
        body.createFixture(fixtureDef);

        hero.setBody(body);

        boundingBox.dispose();
    }

    public void update(ArrayList<Integer> keys){
        checkIfGrounded();
        updateMoves(keys);
    }

    //TODO: rewrite when box2d-ground is properly implemented
    private void checkIfGrounded() {
        if(hero.getY() <= 0f) {
            hero.setGrounded(true);
            hero.setY(0);
        }
    }

    private void updateMoves(ArrayList<Integer> keys) {
        if(keys.contains(Input.Keys.LEFT) && !keys.contains(Input.Keys.RIGHT)) {
            walk(Direction.LEFT);
        }
        if(!keys.contains(Input.Keys.LEFT) && keys.contains(Input.Keys.RIGHT)) {
            walk(Direction.RIGHT);
        }
        if(keys.contains(Input.Keys.SPACE)) {
            jumpIfPossible();
        }
        if(keys.isEmpty()) {
            relax();
        }
    }

    public void walk(Direction direction) {
        hero.setMovementState(MovementState.WALKING);
        hero.setDirection(direction);
        float newPosX = 0;

        if(walkIsPossible(direction, hero.getPosition())) {

            if(direction == Direction.RIGHT) {
                newPosX = hero.getX() + hero.getMovementSpeed() * Gdx.graphics.getDeltaTime();
            } else {
                newPosX = hero.getX() - hero.getMovementSpeed() * Gdx.graphics.getDeltaTime();
            }
            hero.setX(newPosX);
        }
    }

    private boolean walkIsPossible(Direction direction, Position heroPosition) {
        return true;
    }

    public void jumpIfPossible() {
        if(hero.isGrounded()) {
            jump();
        }
    }

    private void jump() {
        hero.setGrounded(false);
        hero.setMovementState(MovementState.JUMPING);
        hero.getBody().setLinearVelocity(0,100f);
    }

    private void relax() {
        hero.setMovementState(MovementState.STANDING);
    }

    public void attack() {
        //TODO finish walk() method
    }
}
