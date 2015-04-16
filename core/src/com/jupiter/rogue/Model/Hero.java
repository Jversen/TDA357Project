package com.jupiter.rogue.Model;

/**
 * Created by Johan on 16/04/15.
 */
public class Hero extends Creature{

    private Hero instance = null;

    private Hero () {

    }

    public Hero getInstance() {
        if(instance == null) {
            instance = new Hero();
        }
        return instance;
    }
}
