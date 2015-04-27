package com.jupiter.rogue.Controller;

import com.badlogic.gdx.physics.box2d.*;
import com.jupiter.rogue.Model.Creatures.Hero;

/**
 * Created by hilden on 2015-04-26.
 */
public class MyContactListener implements ContactListener {

    private Hero hero;
    private Fixture fa;
    private Fixture fb;


    public MyContactListener() {
        hero = Hero.getInstance();
    }

    @Override
    public void beginContact(Contact contact) {

        fa = contact.getFixtureA();
        fb = contact.getFixtureB();

        if (fa.getUserData().equals("foot") || fb.getUserData().equals("foot")) {
            hero.getInstance().setGrounded(true);
            System.out.println("Contact started");
        }
       // System.out.println(fa.getUserData() + ", " + fb.getUserData());
    }

    @Override
    public void endContact(Contact contact) {

        fa = contact.getFixtureA();
        fb = contact.getFixtureB();

        if ((fa.getUserData().equals("foot") || fb.getUserData().equals("room")) || (fa.getUserData().equals("room") || fb.getUserData().equals("foot"))) {
            hero.getInstance().setGrounded(false);
            System.out.println("Contact stopped");
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }
}
