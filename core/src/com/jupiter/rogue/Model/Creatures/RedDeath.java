package com.jupiter.rogue.Model.Creatures;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.jupiter.rogue.Model.Map.WorldHolder;
import com.badlogic.gdx.Gdx;

import static com.jupiter.rogue.Model.Map.WorldConstants.PPM;

/**
 * Created by Johan on 2015-04-18.
 */
@lombok.Data
public class RedDeath extends Enemy {

    public RedDeath(float x, float y){

        spriteBatch = new SpriteBatch();

        //Shapeless body for the enemy RedDeath
        BodyDef redDeathBodyDef = new BodyDef();
        redDeathBodyDef.type = BodyDef.BodyType.DynamicBody;
        redDeathBodyDef.fixedRotation = true;
        redDeathBodyDef.position.set(x / PPM, y / PPM);

        Body redDeathBody = WorldHolder.getInstance().getWorld().createBody(redDeathBodyDef);

        this.setBody(redDeathBody);

        setPosition(x, y);

        // Creates the texture for this enemy

        Pixmap pixmap = new Pixmap(25, 50, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.RED);
        pixmap.fill();

        pixmap.setColor(Color.YELLOW);
        pixmap.drawCircle(pixmap.getWidth() / 2, pixmap.getHeight() / 4, pixmap.getWidth() / 2);

        spriteSheet = new Texture(pixmap);
        pixmap.dispose();
        /////////////////////
        sprite = new Sprite(spriteSheet);





        //setBounds(x, y, spriteSheet.getWidth(), spriteSheet.getHeight());

        //System.out.println("bounds size: " + bounds.getWidth() + bounds.getHeight());


    }

    public void render(){
        spriteBatch.begin();
        sprite.setPosition(this.getX(), this.getY());
        sprite.draw(spriteBatch);
        spriteBatch.end();
    }




}
