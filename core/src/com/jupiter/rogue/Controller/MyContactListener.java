package com.jupiter.rogue.Controller;

import com.badlogic.gdx.physics.box2d.*;
import com.jupiter.rogue.Model.Creatures.Hero;

/**
 * Created by hilden on 2015-04-26.
 */
public class MyContactListener implements ContactListener {

    private Hero hero;


    public MyContactListener() {
        hero = Hero.getInstance();
    }

    @Override
    public void beginContact(Contact contact) {

        Fixture fa = contact.getFixtureA(); //room                     **********JOHANNES*************
        Fixture fb = contact.getFixtureB(); //hero
        hero.getInstance().setGrounded(true);
        System.out.print(fa.getUserData() + ", " + fb.getUserData());
    }

    @Override
    public void endContact(Contact contact) {
        System.out.print("Contact stopped");
        hero.getInstance().setGrounded(false);
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }
}
