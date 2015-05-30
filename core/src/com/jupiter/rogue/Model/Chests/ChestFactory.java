package com.jupiter.rogue.Model.Chests;

import com.jupiter.rogue.Model.Items.Item;
import com.jupiter.rogue.Model.Items.Weapon;
import com.jupiter.rogue.Utils.Position;
import com.jupiter.rogue.Utils.WorldConstants;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by Johan on 2015-05-28.
 */
public class ChestFactory {
    String chestType;
    Item content;
    //Position position;
    float xPos;
    float yPos;

    public ChestFactory(String chestType, float xPos, float yPos){
        this.chestType = chestType;
        this.xPos = xPos;
        this.yPos = yPos;


    }

    public Chest createChest(){
        switch (chestType) {
            case ("weapon"):
            case ("ring"): return new Chest(chestType);

            default: return new Chest("weapon");
        }
    }

    private Item createContent(String chestType){

        switch (chestType){
            case ("weapon"):
            case ("ring"): return randomizeContent();
        }
    }

    private Item randomizeContent(){
        Item item;
        Random rn = new Random();
        int itemIndex;
        Map map;

        switch (this.chestType){
            case ("weapon"):
                map = WorldConstants.weaponMap;
                break;
            case ("ring"):
                map = WorldConstants.ringMap;
                break;
        }
        
        itemIndex = rn.nextInt(map.size());
        item = map.get(itemIndex);
        return item;

    }
}
