package com.jupiter.rogue.Controller;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.jupiter.rogue.Model.Creatures.Hero;
import com.jupiter.rogue.Model.Map.Position;
import com.jupiter.rogue.Model.Enums.Direction;
import com.jupiter.rogue.Utils.WorldConstants;
import com.jupiter.rogue.Utils.HeroMovement;
import com.jupiter.rogue.View.HeroView;
import com.jupiter.rogue.View.Hud;
import com.jupiter.rogue.View.View;

import static com.jupiter.rogue.Utils.WorldConstants.PPM;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by hilden on 2015-04-17.
 */
@lombok.Data
public class HeroController {

    private Hero hero;
    private HeroView heroView;
    private HeroMovement heroMovement;
    private PolygonShape shape;
    private Body body;

    private Float hitBoxLength;
    private Float hitBoxHeight;
    private Float hitBoxX;
    private Float hitBoxY;
    private Float hitBoxTilt;

    private FixtureDef weaponSensorFixtureDef;
    private FixtureDef weaponSensorLeftFixtureDef;

    private Fixture weaponSensorFixture;
    private Fixture weaponSensorLeftFixture;

    private Timer timer;
    private boolean swapReady;
    private boolean attackReady;
    private Hud hud;

    public HeroController() {
        initHero();
        hud = Hud.getInstance();

    }

    public void initHero() {

        hero = hero.getInstance();
        heroView = new HeroView();

        timer = new Timer();
        swapReady = true;
        attackReady = true;

        Position startPosition = WorldConstants.HERO_START_POSITION;

        //creates a shapeless body for the player
        BodyDef playerBodyDef = new BodyDef();
        playerBodyDef.type = BodyDef.BodyType.DynamicBody;
        playerBodyDef.fixedRotation = true;
        playerBodyDef.position.set(startPosition.getXPos() / PPM, startPosition.getYPos() / PPM);

        //creates the shape of the playerBodyDef
        shape = new PolygonShape();
        shape.setAsBox(10 / PPM, 21 / PPM, new Vector2(0, 9 / PPM), 0); //temporary values, should be dependent on sprite size

        // FixtureDef sets physical properties
        FixtureDef playerFixtureDef = new FixtureDef();
        playerFixtureDef.shape = shape;
        playerFixtureDef.density = 1f;
        playerFixtureDef.friction = 1.55f;
        playerFixtureDef.restitution = 0.0f;

        //puts the player body into the world
        body = WorldConstants.CURRENT_WORLD.createBody(playerBodyDef);

        body.setUserData("hero");
        body.createFixture(playerFixtureDef).setUserData("hero"); //naming the herofixture hero.
        heroMovement = new HeroMovement(body);

        WorldConstants.BODIES.add(body);
        //hero.setBody(body);


        //creates a sensor at the players feet
        shape.setAsBox(8 / PPM, 1 / PPM, new Vector2(0, -15 / PPM), 0);

        FixtureDef feetSensorFixtureDef = new FixtureDef();
        feetSensorFixtureDef.shape = shape;
        feetSensorFixtureDef.isSensor = true;
        body.createFixture(feetSensorFixtureDef).setUserData("foot");

        //weaponSensors
        weaponSensorFixtureDef = new FixtureDef();

        //clears memory
        shape.dispose();
    }

    //Sets the heroes weapon hitbox to the current weapon's. Has a boolean to track if its the first use of the method.
    private void createWeaponHitBox() {

        //reinitializes the shape of the fixturedef.
        shape = new PolygonShape();

        //creates a fixture with a shape on the right side of the hero using the helpmethod.
        hitBoxShapeMaker(); //useing the helpmethod.
        weaponSensorFixtureDef.shape = shape;
        weaponSensorFixture = body.createFixture(weaponSensorFixtureDef);
        weaponSensorFixture.setSensor(true);
        weaponSensorFixture.setUserData("weaponSensor");

        //clears memory
        shape.dispose();
    }

    private void removeWeaponHitBox() {
        body.destroyFixture(weaponSensorFixture);
    }

    //help-method to setWeaponHitBox, has a boolean that determines what side of the hero the hitbox is for.
    private PolygonShape hitBoxShapeMaker() {
        hitBoxLength = hero.getCurrentWeapon().getHitBoxValues().getModel().getElementAt(0);
        hitBoxHeight = hero.getCurrentWeapon().getHitBoxValues().getModel().getElementAt(1);
        hitBoxX = hero.getCurrentWeapon().getHitBoxValues().getModel().getElementAt(2);
        hitBoxY = hero.getCurrentWeapon().getHitBoxValues().getModel().getElementAt(3);
        hitBoxTilt = hero.getCurrentWeapon().getHitBoxValues().getModel().getElementAt(4);

        if (hero.getDirection() == Direction.LEFT) {
            hitBoxX = hitBoxLength * -1;
            hitBoxTilt = hitBoxTilt * -1;
        }

        shape.setAsBox(hitBoxLength / PPM, hitBoxHeight / PPM, new Vector2(hitBoxX / PPM, hitBoxY / PPM), hitBoxTilt / PPM);

        return shape;
    }

    public void update(ArrayList<Integer> keys){
        updatePhysics();
        updateMoves(keys);
    }

    private void updatePhysics() {
        Position physPos = new Position(heroMovement.getBody().getPosition().x, heroMovement.getBody().getPosition().y);
        hero.setPosition(physPos);
    }

    private void updateMoves(ArrayList<Integer> keys) {
        //Move
        if(keys.contains(Input.Keys.LEFT) && !keys.contains(Input.Keys.RIGHT)) {
            hero.walk(Direction.LEFT, heroMovement);
        }
        //Move
        if(!keys.contains(Input.Keys.LEFT) && keys.contains(Input.Keys.RIGHT)) {
            hero.walk(Direction.RIGHT, heroMovement);
        }
        //Jump
        if(keys.contains(Input.Keys.SPACE)) {
            hero.jump(heroMovement);
            hero.decreaseHealthPoints(10);
            hud.update(hero.getCurrentHealthPoints());

        }
        //Attack
        if (keys.contains(Input.Keys.E)) {
            attack();
        }
        //Swap weapons
        if (keys.contains(Input.Keys.W)) {
            swap();
        }
        if(keys.isEmpty()) {
            hero.relax(heroMovement);
        }
    }

    //Is here (in controller) and not it model because it uses a timer and the timers are currently all implemented here.
    private void swap() {
        if (swapReady) {
            swapReady = false;
            hero.swapWeapon();
            System.out.println("Swapped to: " + hero.getCurrentWeapon().toString());
            timer.schedule(new SwapTask(), 1000);
        }
    }

    //A nestled class to implement a timertask. Timertask to control the swap weapons cooldown.
    class SwapTask extends TimerTask {
        public void run() {
            setSwapReady(true);
        }
    }

    //Handels the hitbox creation and deletion part of the attack.
    private void attack() {
        if (attackReady) {
            attackReady = false;
            hero.attack(heroMovement);
            if (hero.isMeleeCurrentWeapon()) {
                meleeAttack();
            } else {
                rangedAttack();
            }
        }
    }

    private void meleeAttack() {
        timer.schedule(new meleeAttackTask(), 200);
    }

    private void rangedAttack() {
        timer.schedule(new meleeAttackTask(), 200);
    }

    //A nestled class to implement a timertask. Timertask to control the delay of pressing attack and actually attacking.
    class meleeAttackTask extends TimerTask {
        public void run() {
            createWeaponHitBox();
            timer.schedule(new meleeAttackTask2(), 50);
        }
    }

    //A nestled class to implement a timertask. Timertask to control the "length" of the attack.
    class meleeAttackTask2 extends TimerTask {
        public void run() {
            removeWeaponHitBox();
            timer.schedule(new meleeAttackTask3(), 900);
        }
    }

    //A nestled class to implement a timertask. Timertask to control the attack cooldown.
    class meleeAttackTask3 extends TimerTask {
        public void run() {
            attackReady = true;
        }
    }
}
