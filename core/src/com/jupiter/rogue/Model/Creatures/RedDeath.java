package com.jupiter.rogue.Model.Creatures;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Johan on 2015-04-18.
 */
public class RedDeath extends Enemy {

    public RedDeath(float x, float y){
        setPosition(x, y);

        // Creates the texture for this enemy
        Pixmap pixmap = new Pixmap(25, 50, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.RED);
        pixmap.fill();

        pixmap.setColor(Color.YELLOW);
        pixmap.drawCircle(pixmap.getWidth() / 2, pixmap.getHeight() / 4, pixmap.getWidth() / 2);

        setSpriteSheet(new Texture(pixmap));
        pixmap.dispose();
        /////////////////////

        setBounds(x, y, spriteSheet.getWidth(), spriteSheet.getHeight());

        System.out.println("bounds size: " + bounds.getWidth() + bounds.getHeight());


    }
    public RedDeath(){
        this(0, 0);
    }


}
