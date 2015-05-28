package com.jupiter.rogue.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by Johan on 2015-05-28.
 */
public class ChestView {

    Texture spriteSheet;
    Sprite spriteLocked;
    Sprite spriteOpened;

    public ChestView(){
        spriteSheet = new Texture(Gdx.files.internal("Data/box_yellow.png"));
        spriteLocked = new Sprite(spriteSheet, 0, 0, 32, 32);
        spriteOpened = new Sprite(spriteSheet, 32, 0, 32, 32);



    }

}
