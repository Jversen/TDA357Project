package com.jupiter.rogue.Controller;

import com.jupiter.rogue.Model.Creatures.Creature;
import com.jupiter.rogue.Model.Creatures.Hero;

/**
 * Created by hilden on 2015-04-17.
 */
public class HeroController {

    private Hero hero = Hero.getInstance();


    public void relax() {
        hero.setMovementStateStanding();
    }

    public void walk() {
        hero.setMovementStateWalking();
        int newPosX = hero.getPosition().getXPos() + 1;
        hero.setPosition();
    }

    public void jump() {
        //TODO finish walk() method
    }

    public void attack() {
        //TODO finish walk() method
    }

}
