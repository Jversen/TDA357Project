package com.jupiter.rogue.Model.Chests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.jupiter.rogue.Model.Items.Item;

import java.util.Random;

/**
 * Created by Johan on 2015-05-28.
 */
public class Chest {
    String chestType;
    Item content;
    protected float xPos;
    protected float yPos;

    public Chest(String chestType, Item content, float xPos, float yPos){
        this.chestType = chestType;
        this.content = content;
        this.xPos = xPos;
        this.yPos = yPos;

        switch (chestType){
            case "weapon":
        }

    }

    //TODO contents etc



}
