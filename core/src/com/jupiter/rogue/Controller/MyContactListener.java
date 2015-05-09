package com.jupiter.rogue.Controller;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.jupiter.rogue.Model.Creatures.Hero;
import com.jupiter.rogue.Model.Enums.MovementState;
import com.jupiter.rogue.Model.Map.Map;
import com.jupiter.rogue.Utils.WorldConstants;

/**
 * Created by hilden on 2015-04-26.
 */
public class MyContactListener implements ContactListener {

    private Hero hero;
    private Map map;

    //The two fixtures touching.
    private Fixture fa;
    private Fixture fb;


    public MyContactListener() {
        hero = Hero.getInstance();
        map = Map.getInstance();
    }

    @Override
    public void beginContact(Contact contact) {

        fa = contact.getFixtureA();
        fb = contact.getFixtureB();

        if ((fa.getUserData().equals("foot") && fb.getUserData().equals("obstacle")) || (fb.getUserData().equals("foot") && fa.getUserData().equals("obstacle"))) {
            hero.setCreatureGrounded(true);
            hero.setCreatureFalling(false);
        }

        if(fa.getUserData().equals("hero") || fb.getUserData().equals("hero")){
            if(fa.getUserData().equals("leftDoor") || fb.getUserData().equals("leftDoor")) {
                map.flagRoomForDestruction("leftDoor");
            }

            if(fa.getUserData().equals("rightDoor") || fb.getUserData().equals("rightDoor")) {
                map.flagRoomForDestruction("rightDoor");
            }
        }
    }

    @Override
    public void endContact(Contact contact) {

        fa = contact.getFixtureA();
        fb = contact.getFixtureB();

        if ((fa.getUserData().equals("foot") && fb.getUserData().equals("obstacle")) ||
                (fa.getUserData().equals("obstacle") && fb.getUserData().equals("foot"))) {     //Remember to use the correct UserDatas. They tend to get changed.......... **************
            hero.setCreatureGrounded(false);
            if (hero.getMovementState() != MovementState.JUMPING) {
                hero.setCreatureFalling(true);
            }
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }
}
