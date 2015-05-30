package com.jupiter.rogue.Model.Chests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.jupiter.rogue.Model.Creatures.Hero;
import com.jupiter.rogue.Model.Items.Item;

import java.util.Random;

/**
 * Created by Johan on 2015-05-28.
 */
@lombok.Data
public class Chest {
    String chestType;
    Item content;
    boolean opened = false;
    boolean empty = false;
    protected float xPos;
    protected float yPos;

    public Chest(String chestType, Item content, float xPos, float yPos){
        this.chestType = chestType;
        this.content = content;
        this.xPos = xPos;
        this.yPos = yPos;

    }

    public void open(){
        this.opened = true;
    }

    public void close(){
        this.opened = false;
    }

    public Item takeContent(Hero hero){
        Item loot;
        boolean itemUsable;

        if (hero.getStrength() >= this.content.getStrengthRequirement() &&
                hero.getAgility() >= this.content.getAgilityRequirement() &&
                hero.getIntellect() >= this.content.getIntellectRequirement()){
            itemUsable = true;
        } else{
            itemUsable = false;
        }

        if(!empty && itemUsable){

            return content;
        }else {
            return null;
        }
    }

    public String describeContent(){
        return content.toString();
    }
    //TODO contents etc



}
