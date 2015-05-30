package com.jupiter.rogue.Model.Chests;

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

    public Chest(String chestType){
        this.chestType = chestType;

        switch (chestType){
            case "weapon":
        }

    }

    //TODO contents etc



}
