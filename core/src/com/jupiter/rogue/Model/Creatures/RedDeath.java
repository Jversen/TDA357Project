package com.jupiter.rogue.Model.Creatures;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.jupiter.rogue.Model.Map.Position;
import com.jupiter.rogue.Utils.WorldConstants;

import static com.jupiter.rogue.Utils.WorldConstants.PPM;

/**
 * Created by Johan on 2015-04-18.
 */
@lombok.Data
public class RedDeath extends Enemy {

    private BodyDef redDeathBodyDef;
    private PolygonShape shape;
    private FixtureDef redDeathFixtureDef;
    private Body redDeathBody;

    Array<Body> bodies = new Array<Body>();


    public RedDeath(float x, float y, String name){

        super(100, 100, 25, 1, true);

        this.name = name;

        this.position = new Position(x,y);

        //this.setPosition(x, y);

        spriteBatch = new SpriteBatch();

        //Shapeless body for the enemy RedDeath
        BodyDef redDeathBodyDef = new BodyDef();
        redDeathBodyDef.type = BodyDef.BodyType.DynamicBody;
        redDeathBodyDef.fixedRotation = true;
        redDeathBodyDef.position.set(x / PPM, y / PPM);

        shape = new PolygonShape();
        shape.setAsBox(10 / PPM, 15 / PPM);

        redDeathFixtureDef = new FixtureDef();
        redDeathFixtureDef.shape = shape;
        redDeathFixtureDef.density = 1f;
        redDeathFixtureDef.friction = 1.55f;
        redDeathFixtureDef.restitution = 0.0f;

        redDeathBody = WorldConstants.CURRENT_WORLD.createBody(redDeathBodyDef);
        redDeathBody.createFixture(redDeathFixtureDef).setUserData("Red Death");
        this.setBody(redDeathBody);



        // Creates the texture for this enemy

        Pixmap pixmap = new Pixmap(20, 30, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.RED);
        pixmap.fill();

        pixmap.setColor(Color.YELLOW);
        pixmap.drawCircle(pixmap.getWidth() / 2, pixmap.getHeight() / 3, pixmap.getWidth() / 2);

        spriteSheet = new Texture(pixmap);
        pixmap.dispose();
        /////////////////////
        sprite = new Sprite(spriteSheet);
        redDeathBody.setUserData(sprite);





        //setBounds(x, y, spriteSheet.getWidth(), spriteSheet.getHeight());

        //System.out.println("bounds size: " + bounds.getWidth() + bounds.getHeight());


    }








    public void render(Matrix4 projectionMatrix){

        spriteBatch.begin();
        spriteBatch.setProjectionMatrix(projectionMatrix);
        sprite.draw(spriteBatch);
        sprite = (Sprite) redDeathBody.getUserData();


        sprite.setPosition((redDeathBody.getPosition().x * PPM) - 10, (redDeathBody.getPosition().y * PPM) - 15);

        sprite.setScale(1/PPM, 1/PPM);
            sprite.setPosition((redDeathBody.getPosition().x) - 10, (redDeathBody.getPosition().y) - 15);

        spriteBatch.end();
    }




}
