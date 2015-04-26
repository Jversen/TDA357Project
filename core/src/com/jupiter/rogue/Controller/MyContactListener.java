package com.jupiter.rogue.Controller;

import com.badlogic.gdx.physics.box2d.*;

/**
 * Created by hilden on 2015-04-26.
 */
public class MyContactListener implements ContactListener {


    @Override
    public void beginContact(Contact contact) {

        Fixture fa = contact.getFixtureA(); //room                     **********JOHANNES*************
        Fixture fb = contact.getFixtureB(); //hero

        //System.out.print(fa.getUserData() + ", " + fb.getUserData());
    }

    @Override
    public void endContact(Contact contact) {
        System.out.print("Contact stopped");
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }
}
