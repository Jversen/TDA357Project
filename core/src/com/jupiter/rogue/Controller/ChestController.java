package com.jupiter.rogue.Controller;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.jupiter.rogue.Model.Chests.Chest;
import com.jupiter.rogue.Utils.WorldConstants;
import com.jupiter.rogue.View.ChestView;

import java.util.Timer;

import static com.jupiter.rogue.Utils.WorldConstants.PPM;

/**
 * Created by Johan on 2015-05-30.
 */
public class ChestController {

    Chest chest;
    //ChestView chestView;
    Body body;

    private PolygonShape shape;

    public ChestController(Chest chest){
        this.chest = chest;
        //this.chestView = new ChestView(chest);
    }

    public void initBody() {

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.fixedRotation = true;
        bodyDef.position.set(chest.getXPos() / PPM, chest.getYPos() / PPM);
        
        shape = new PolygonShape();
        shape.setAsBox(16 / PPM, 16 / PPM, new Vector2(16 / PPM, 16 / PPM), 0);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.isSensor = true;

        body = WorldConstants.CURRENT_WORLD.createBody(bodyDef);
        body.setUserData("chest");
        body.createFixture(fixtureDef).setUserData(this);
        WorldConstants.BODIES.add(body);

        shape.dispose();
    }
}
