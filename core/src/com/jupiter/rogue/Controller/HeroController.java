package com.jupiter.rogue.Controller;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.jupiter.rogue.Model.Creatures.Hero;
import com.jupiter.rogue.Model.Map.Position;
import com.jupiter.rogue.Model.Enums.Direction;
import com.jupiter.rogue.Utils.WorldConstants;
import com.jupiter.rogue.Utils.HeroMovement;
import com.jupiter.rogue.View.HeroView;

import static com.jupiter.rogue.Utils.WorldConstants.PPM;

import java.util.ArrayList;

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

    private FixtureDef weaponSensorRightFixtureDef;
    private FixtureDef weaponSensorLeftFixtureDef;


    public HeroController() {
        initHero();
    }

    public void initHero() {

        hero = hero.getInstance();
        heroView = new HeroView();

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

        //body.setUserData("hero");                                                                      ****Needed or not??***********
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



        weaponSensorRightFixtureDef = new FixtureDef();
        weaponSensorLeftFixtureDef = new FixtureDef();
        weaponSensorRightFixtureDef.isSensor = true;
        weaponSensorLeftFixtureDef.isSensor = true;

        setWeaponHitBox();
    }

    //Sets the heroes weapon hitbox to the current weapon's
    private void setWeaponHitBox() {

        //shape = new PolygonShape();
        hitBoxShapeMaker(true); //useing the helpmethod
        weaponSensorRightFixtureDef.shape = shape;
        body.createFixture(weaponSensorRightFixtureDef).setUserData("weaponSensorRight");

        hitBoxShapeMaker(false); //useing the helpmethod
        weaponSensorLeftFixtureDef.shape = shape;
        body.createFixture(weaponSensorLeftFixtureDef).setUserData("weaponSensorLeft");

        shape.dispose();
    }

    //help-method to setWeaponHitBox, has a boolean that determines what side of the hero the hitbox is for.
    private PolygonShape hitBoxShapeMaker(boolean rightHitBox) {

        hitBoxLength = hero.getCurrentWeapon().getHitBoxValues().getModel().getElementAt(0);
        hitBoxHeight = hero.getCurrentWeapon().getHitBoxValues().getModel().getElementAt(1);
        hitBoxX = hero.getCurrentWeapon().getHitBoxValues().getModel().getElementAt(2);
        hitBoxY = hero.getCurrentWeapon().getHitBoxValues().getModel().getElementAt(3);
        hitBoxTilt = hero.getCurrentWeapon().getHitBoxValues().getModel().getElementAt(4);

        if (!rightHitBox) {
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
        if(keys.contains(Input.Keys.LEFT) && !keys.contains(Input.Keys.RIGHT)) {
            hero.walk(Direction.LEFT, heroMovement);
        }
        if(!keys.contains(Input.Keys.LEFT) && keys.contains(Input.Keys.RIGHT)) {
            hero.walk(Direction.RIGHT, heroMovement);
        }
        if(keys.contains(Input.Keys.SPACE)) {
            hero.jump(heroMovement);
        }
        if (keys.contains(Input.Keys.E)) {
            hero.attack(heroMovement);
        }
        if(keys.isEmpty()) {
            hero.relax(heroMovement);
        }
    }
}
