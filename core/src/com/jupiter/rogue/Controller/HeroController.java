package com.jupiter.rogue.Controller;

import com.jupiter.rogue.Model.Creatures.Creature;
import com.jupiter.rogue.Model.Creatures.Hero;

/**
 * Created by hilden on 2015-04-17.
 */
public class HeroController {

    private Hero hero = Hero.getInstance();
    private Hero.MovementState state = Hero.MovementState.STANDING;


    public void walk() {
        posX = hero.getPosition().getXPos();

    }

    public void jump() {
        //TODO finish walk() method
    }

    public void attack() {
        //TODO finish walk() method
    }

}
