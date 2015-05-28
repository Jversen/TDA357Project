package com.jupiter.rogue.Controller;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.jupiter.rogue.Controller.Behaviors.JumpBehaviors.JumpBehavior;
import com.jupiter.rogue.Controller.Behaviors.JumpBehaviors.NormalJump;
import com.jupiter.rogue.Controller.Behaviors.MoveBehaviors.MoveBehavior;
import com.jupiter.rogue.Controller.Behaviors.MoveBehaviors.Walk;
import com.jupiter.rogue.Model.Creatures.Hero;
import com.jupiter.rogue.Model.Items.RangedWeapon;
import com.jupiter.rogue.Utils.Position;
import com.jupiter.rogue.Utils.Enums.Direction;
import com.jupiter.rogue.Utils.WorldConstants;
import com.jupiter.rogue.View.HeroView;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
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
    private PolygonShape shape;
    private Body body;

    private BodyDef projectileBodyDef;
    private Body projectileBody;

    private Float hitBoxLength;
    private Float hitBoxHeight;
    private Float hitBoxX;
    private Float hitBoxY;
    private Float hitBoxTilt;

    private FixtureDef weaponSensorFixtureDef;

    private Fixture weaponSensorFixture;

    private Timer timer;
    private boolean weaponReady;
    private boolean swapReady;
    private boolean attackReady;

    private JumpBehavior jumpBehavior;
    private MoveBehavior moveBehavior;

    public HeroController() {
        initHero();
    }

    public void initHero() {

        hero = hero.getInstance();
        heroView = HeroView.getInstance();

        timer = new Timer();
        weaponReady = true;

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

        jumpBehavior = new NormalJump(this.body);
        moveBehavior = new Walk(this.body);

        //clears memory
        shape.dispose();
    }

    public void update(ArrayList<Integer> keys){
        updatePhysics();
        updateMoves(keys);
    }

    private void updatePhysics() {
        Position physPos = new Position(this.body.getPosition().x, this.body.getPosition().y);
        hero.setPosition(physPos);
    }

    private void updateMoves(ArrayList<Integer> keys) {
        //Move
        if(keys.contains(Input.Keys.LEFT) && !keys.contains(Input.Keys.RIGHT)) {
            hero.walk(Direction.LEFT);
            moveBehavior.move(hero.getDirection(), hero.getMovementSpeed());
            hero.setPosition(new Position(this.body.getPosition().x, this.body.getPosition().y));
        }
        //Move
        if(!keys.contains(Input.Keys.LEFT) && keys.contains(Input.Keys.RIGHT)) {
            hero.walk(Direction.RIGHT);
            moveBehavior.move(hero.getDirection(), hero.getMovementSpeed());
            hero.setPosition(new Position(this.body.getPosition().x, this.body.getPosition().y));
        }
        //Jump
        if(keys.contains(Input.Keys.SPACE)) {
            if (hero.isCreatureGrounded()) {
                hero.jump();
                jumpBehavior.jump(7f);
                hero.setPosition(new Position(this.body.getPosition().x, this.body.getPosition().y));
            }
        }
        if (keys.contains(Input.Keys.E)) {
            attack();
        }
        if (keys.contains(Input.Keys.W)) {
            swapWeapon();
        }
        if(keys.isEmpty()) {
            hero.relax();
        }
    }

    //Sets the heroes weapon hitbox to the current weapon's.
    private void createMeleeWeaponHitbox() {

        //reinitializes the shape of the fixturedef.
        shape = new PolygonShape();

        //creates a fixture with a shape on the correct side of the hero using the helpmethod.
        hitBoxShapeMaker(); //useing the helpmethod.
        weaponSensorFixtureDef.shape = shape;
        weaponSensorFixture = body.createFixture(weaponSensorFixtureDef);
        weaponSensorFixture.setSensor(true);
        weaponSensorFixture.setUserData("weaponSensor");

        //clears memory
        shape.dispose();
    }

    private void createRangedWeaponHitbox() {

        //reinitializes the shape of the fixturedef.
        shape = new PolygonShape();

        //creates a fixture with a shape on the correct side of the hero using the helpmethod.
        hitBoxShapeMaker(); //useing the helpmethod.
        weaponSensorFixtureDef.shape = shape;
        weaponSensorFixtureDef.density = 0f;
        weaponSensorFixtureDef.friction = 0f;
        weaponSensorFixtureDef.restitution = 0f;

        //creates a shapeless body
        projectileBodyDef = new BodyDef();
        projectileBodyDef.type = BodyDef.BodyType.DynamicBody;
        projectileBodyDef.fixedRotation = true;
        projectileBodyDef.position.set((hero.getX()), (hero.getY()));
        projectileBody = WorldConstants.CURRENT_WORLD.createBody(projectileBodyDef);
        projectileBody.setUserData("projectile");

        weaponSensorFixture = projectileBody.createFixture(weaponSensorFixtureDef);
        weaponSensorFixture.setSensor(true);
        weaponSensorFixture.setUserData("weaponSensor");

        WorldConstants.BODIES.add(projectileBody);

        projectileBody.applyForceToCenter(new Vector2(projectileHelper(), 0), true);

        //clears memory
        shape.dispose();
        System.out.println("Bodycount: " + WorldConstants.CURRENT_WORLD.getBodyCount());
    }

    //Removes the heroes weapon hitbox. (ranged projectile hitboxes are removed through the contactlistener)
    private void removeMeleeWeaponHitbox() {
        if (hero.isMeleeCurrentWeapon()) {
            body.destroyFixture(weaponSensorFixture);
        }
    }

    //help-method to the createWeaponHitBox methods, gets the hitbox information from the heroes weapon and then sets a hitbox accordingly depending on what direction the hero is facing.
    private void hitBoxShapeMaker() {
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
    }

    //help-method to the createRangedWeaponHitbox method, gets the projectile speed from the ranged weapon and determines direction.
    private Float projectileHelper() {
        if (hero.getDirection() == Direction.LEFT)
            return (-1 * ((RangedWeapon)hero.getCurrentWeapon()).getProjectileSpeed());
        else {
            return ((RangedWeapon)hero.getCurrentWeapon()).getProjectileSpeed();
        }
    }

    //Is here (in controller) and not it model because it uses a timer and the timers are currently all implemented here.
    private void swapWeapon() {
        if (weaponReady) {
            weaponReady = false;
            hero.swapWeapon();
            System.out.println("Swapped to: " + hero.getCurrentWeapon().toString());
            timer.schedule(new WeaponReadyTask(), 1000);
        }
    }

    //Handels the hitbox creation and deletion part of the attack.
    private void attack() {
        if (weaponReady) {
            weaponReady = false;
            hero.attack();
            timer.schedule(new AttackDelayTask(), 50);
        }
    }

    //A nestled class to implement a timertask. Timertask to control the delay of pressing attack and actually attacking.
    class AttackDelayTask extends TimerTask {
        public void run() {
            if (hero.isMeleeCurrentWeapon()) {
                createMeleeWeaponHitbox();
                timer.schedule(new RemoveHitBoxTask(), 100);
            } else {
                createRangedWeaponHitbox();
                timer.schedule(new WeaponReadyTask(), 1000);
            }
        }
    }

    //A nestled class to implement a timertask. Timertask to control the "length" of the attack.
    class RemoveHitBoxTask extends TimerTask {
        public void run() {
            removeMeleeWeaponHitbox();
            timer.schedule(new WeaponReadyTask(), 900);
        }
    }

    //A nestled class to implement a timertask. Timertask to control the attack cooldown.
    class WeaponReadyTask extends TimerTask {
        public void run() {
            weaponReady = true;
        }
    }
}
