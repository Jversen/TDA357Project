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
            if(fa.getUserData().equals("l1") || fb.getUserData().equals("l1")) {
                map.flagRoomForDestruction("l1");
            }

            if(fa.getUserData().equals("r1") || fb.getUserData().equals("r1")) {
                map.flagRoomForDestruction("r1");
            }

            if(fa.getUserData().equals("l2") || fb.getUserData().equals("l2")) {
                map.flagRoomForDestruction("l2");
            }

            if(fa.getUserData().equals("r2") || fb.getUserData().equals("r2")) {
                map.flagRoomForDestruction("r2");
            }

            if(fa.getUserData().equals("t1") || fb.getUserData().equals("t1")) {
                map.flagRoomForDestruction("t1");
            }

            if(fa.getUserData().equals("b1") || fb.getUserData().equals("b1")) {
                map.flagRoomForDestruction("b1");
            }

        }

        //Combat
        if ((fa.getUserData().equals("weaponSensorRight") && fb.getUserData().equals("enemy")) ||
                (fa.getUserData().equals("enemy") && fb.getUserData().equals("weaponSensorRight"))) {
            hero.setEnemyInRangeRight(true);
        }

        if ((fa.getUserData().equals("weaponSensorLeft") && fb.getUserData().equals("enemy")) ||
                (fa.getUserData().equals("enemy") && fb.getUserData().equals("weaponSensorLeft"))) {
            hero.setEnemyInRangeLeft(true);
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

        //Combat
        if ((fa.getUserData().equals("weaponSensorRight") && fb.getUserData().equals("enemy")) ||
                (fa.getUserData().equals("enemy") && fb.getUserData().equals("weaponSensorRight"))) {
            hero.setEnemyInRangeRight(false);
        }

        if ((fa.getUserData().equals("weaponSensorLeft") && fb.getUserData().equals("enemy")) ||
                (fa.getUserData().equals("enemy") && fb.getUserData().equals("weaponSensorLeft"))) {
            hero.setEnemyInRangeLeft(false);
        }

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }
}
