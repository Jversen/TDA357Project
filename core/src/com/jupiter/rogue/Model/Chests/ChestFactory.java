package com.jupiter.rogue.Model.Chests;

/**
 * Created by Johan on 2015-05-28.
 */
public class ChestFactory {
    String chestType;

    public ChestFactory(String chestType){
        this.chestType = chestType;
    }

    public Chest createChest(){
        switch (chestType) {
            case ("weapon"): return new Chest(chestType);
            case ("ring"): return new Chest(chestType);
            default: return new Chest("weapon");
        }
    }
}
