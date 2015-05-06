package com.jupiter.rogue.Controller;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.jupiter.rogue.Model.Creatures.Hero;
import com.jupiter.rogue.Model.Map.Map;
import com.jupiter.rogue.Utils.WorldConstants;

/**
 * Created by hilden on 2015-04-26.
 */
public class MyContactListener implements ContactListener {

    private Hero hero;
    private Map map;
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

        if (fa.getUserData().equals("foot") || fb.getUserData().equals("foot")) {
            hero.incNbrOfPlatforms();
        }

        if(fa.getUserData().equals("hero") || fb.getUserData().equals("hero")){
            if(fa.getUserData().equals("leftDoor") || fb.getUserData().equals("leftDoor")) {
                map.flagRoomForDestruction("leftDoor");
                System.out.println("leftDoor sensor hit");
            }

            if(fa.getUserData().equals("rightDoor") || fb.getUserData().equals("rightDoor")) {
                map.flagRoomForDestruction("rightDoor");
                for(Body body : WorldConstants.BODIES) {
                    if(body.getUserData() != null && body.getUserData().equals("hero")) {
                        body.getPosition().set(100,100);
                    }
                }
                System.out.println("rightDoor sensor hit");
            }
        }
    }

    @Override
    public void endContact(Contact contact) {

        fa = contact.getFixtureA();
        fb = contact.getFixtureB();

        if ((fa.getUserData().equals("foot") && fb.getUserData().equals("room")) ||
                (fa.getUserData().equals("room") && fb.getUserData().equals("foot"))) {
            hero.decNbrOfPlatforms();
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }
}
