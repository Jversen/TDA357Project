package com.jupiter.rogue.Controller;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.jupiter.rogue.Model.Creatures.Hero;
import com.jupiter.rogue.Model.Creatures.RedDeath;
import com.jupiter.rogue.Model.Enums.Direction;
import com.jupiter.rogue.Model.Map.Position;
import com.jupiter.rogue.Utils.EnemyMovement;
import com.jupiter.rogue.Utils.HeroMovement;
import com.jupiter.rogue.Utils.WorldConstants;

import java.util.ArrayList;

import static com.jupiter.rogue.Utils.WorldConstants.PPM;

/**
 * Created by Johan on 17/04/15.
 */
public class RedDeathController {

    private RedDeath redDeath;
    private EnemyMovement redDeathMovement;
    private Position startPosition;

    public RedDeathController() {
        initRedDeath();
    }

    public void initRedDeath() {

        redDeath = new RedDeath();

        startPosition = redDeath.getPosition();

        //creates a shapeless body for the player
        BodyDef redDeathBodyDef = new BodyDef();
        redDeathBodyDef.type = BodyDef.BodyType.DynamicBody;
        redDeathBodyDef.fixedRotation = true;
        redDeathBodyDef.position.set(startPosition.getXPos() / PPM, startPosition.getYPos() / PPM);

        //creates the shape of the playerBodyDef
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(6 / PPM, 10 / PPM); //temporary values, should be dependent on sprite size

        // FixtureDef sets physical properties
        FixtureDef redDeathFixtureDef = new FixtureDef();
        redDeathFixtureDef.shape = shape;
        redDeathFixtureDef.density = 100f;
        redDeathFixtureDef.friction = 0.2f;
        redDeathFixtureDef.restitution = 0.0f;

        //puts the player body into the world
        Body redDeathBody = WorldConstants.CURRENT_WORLD.createBody(redDeathBodyDef);
        redDeathBody.setUserData("RedDeath");
        redDeathBody.createFixture(redDeathFixtureDef).setUserData("RedDeath"); //naming the herofixture hero.
        redDeathMovement = new EnemyMovement(redDeathBody);

        WorldConstants.BODIES.add(redDeathBody);

        //disposes shape to save memory
        shape.dispose();
    }

    public void update(){
        updatePhysics();
    }

    private void updatePhysics() {
        Position physPos = new Position(redDeathMovement.getBody().getPosition().x, redDeathMovement.getBody().getPosition().y);
        redDeath.setPosition(physPos);
    }

    public RedDeath getRedDeath() {
        return redDeath;
    }
}
