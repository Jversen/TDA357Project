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
        System.out.println("agility req: " + content.getAgilityRequirement());
        System.out.println(content.getDescription());
        this.xPos = xPos;
        this.yPos = yPos;

    }

    /**
     * Opens this chest
     */
    public void open(){
        this.opened = true;
    }

    /**
     * Closes this chest
     */
    public void close(){
        this.opened = false;
    }

    /**
     * Returns the Item in this chest, if the chest is not empty and the hero has the required attributes.
     */
    public Item takeContent(Hero hero){
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

    /**
     * Describes the content of this chest
     */
    public String describeContent(){
        return content.toString();
    }

}
