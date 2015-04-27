package com.jupiter.rogue.Controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.jupiter.rogue.Model.Creatures.Hero;
import com.jupiter.rogue.Model.Map.Position;
import com.jupiter.rogue.Model.Enums.Direction;
import com.jupiter.rogue.Model.Enums.MovementState;
import com.jupiter.rogue.Model.Map.WorldConstants;
import com.jupiter.rogue.Model.Map.WorldHolder;

import static com.jupiter.rogue.Model.Map.WorldConstants.PPM;
import java.util.ArrayList;

/**
 * Created by hilden on 2015-04-17.
 */
@lombok.Data
public class HeroController {

    private Hero hero;

    public HeroController() {
        initHero();
    }

    public void initHero() {

        hero = hero.getInstance();

        Position startPosition = WorldConstants.HERO_START_POSITION;

        //creates a shapeless body for the player
        BodyDef playerBodyDef = new BodyDef();
        playerBodyDef.type = BodyDef.BodyType.DynamicBody;
        playerBodyDef.fixedRotation = true;
        playerBodyDef.position.set(startPosition.getXPos() / PPM, startPosition.getYPos() / PPM);

        //creates the shape of the playerBodyDef
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(10 / PPM, 15 / PPM); //temporary values, should be dependent on sprite size

        // FixtureDef sets physical properties
        FixtureDef playerFixtureDef = new FixtureDef();
        playerFixtureDef.shape = shape;
        playerFixtureDef.density = 1f;
        playerFixtureDef.friction = 1.55f;
        playerFixtureDef.restitution = 0.0f;

        //puts the player body into the world
        Body playerBody = WorldHolder.getInstance().getWorld().createBody(playerBodyDef);
        playerBody.createFixture(playerFixtureDef).setUserData("hero"); //naming the herofixture hero.
        hero.setBody(playerBody);


        //creates a sensor at the players feet
        shape.setAsBox(10 / PPM, 1 / PPM, new Vector2(0, -18 / PPM), 0);

        FixtureDef feetSensorFixtureDef = new FixtureDef();
        feetSensorFixtureDef.shape = shape;
        feetSensorFixtureDef.isSensor = true;
        playerBody.createFixture(feetSensorFixtureDef).setUserData("foot");


        //disposes shape to save memory
        shape.dispose();
    }

    public void update(ArrayList<Integer> keys){
        checkJumping();
        updateMoves(keys);
    }


    //TODO: rewrite when box2d-ground is properly implemented
    private void checkJumping() {

/*        if(hero.getY() <= 0f) {
            hero.setGrounded(true);
            hero.setY(0);
        }*/
    }

    private void updateMoves(ArrayList<Integer> keys) {
        if(keys.contains(Input.Keys.LEFT) && !keys.contains(Input.Keys.RIGHT)) {
            hero.walk(Direction.LEFT);
        }
        if(!keys.contains(Input.Keys.LEFT) && keys.contains(Input.Keys.RIGHT)) {
            hero.walk(Direction.RIGHT);
        }
        if(keys.contains(Input.Keys.SPACE)) {
            hero.jump();
        }
        if(keys.isEmpty()) {
            hero.relax();
        }
    }


}
