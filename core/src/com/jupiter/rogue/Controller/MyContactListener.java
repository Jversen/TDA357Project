package com.jupiter.rogue.Controller;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

/**
 * Created by hilden on 2015-04-26.
 */
public class MyContactListener implements ContactListener {


    @Override
    public void beginContact(Contact contact) {
        System.out.print("Contact started");
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
