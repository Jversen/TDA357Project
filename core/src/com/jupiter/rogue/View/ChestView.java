package com.jupiter.rogue.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Matrix4;
import com.jupiter.rogue.Model.Chests.Chest;
import static com.jupiter.rogue.Utils.WorldConstants.PPM;

/**
 * Created by Johan on 2015-05-28.
 */
public class ChestView {

    Chest chest;
    String chestType;
    Texture spriteSheet;
    Sprite sprite;
    Sprite spriteLocked;
    Sprite spriteOpened;
    TextureAtlas atlas;
    protected SpriteBatch spriteBatch;

    public ChestView(Chest chest){
        this.chest = chest;
        this.chestType = chest.getChestType();
        spriteBatch = new SpriteBatch();
        String texturePath;
        atlas = new TextureAtlas(Gdx.files.internal("Data/chestTextures/box.atlas"));

        switch (chestType){
            case ("weapon"): texturePath = "Data/chestTextures/box_blue.png";
                break;
            case ("ring"): texturePath = "Data/chestTextures/box_orange.png";
                break;
            default: texturePath = "Data/chestTextures/box_blue.png";
        }

        spriteSheet = new Texture(texturePath);
        sprite = new Sprite(spriteSheet);

       // spriteLocked = new Sprite(spriteSheet, 0, 0, 32, 32);
       // spriteOpened = new Sprite(spriteSheet, 32, 0, 32, 32);
    }

    public void draw(Matrix4 projectionMatrix){

        if(this.chest.isOpened()){
            sprite.setRegion(0, 0, 32, 32);
        }else{
            sprite.setRegion(31, 0, 31, 31);
        }

        sprite.setPosition(chest.getXPos(), chest.getYPos());
        spriteBatch.begin();
        spriteBatch.setProjectionMatrix(projectionMatrix);
        sprite.draw(spriteBatch);
        spriteBatch.end();
    }
}
