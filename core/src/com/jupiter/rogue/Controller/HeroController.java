package com.jupiter.rogue.Controller;

import com.badlogic.gdx.Gdx;
import com.jupiter.rogue.Model.Creatures.Creature;
import com.jupiter.rogue.Model.Creatures.Hero;
import com.jupiter.rogue.Model.Map.Position;
import com.jupiter.rogue.Model.Enums.Direction;
import com.jupiter.rogue.Model.Enums.MovementState;

/**
 * Created by hilden on 2015-04-17.
 */

public class HeroController {

    private Hero hero = Hero.getInstance();


    public void relax() {
        hero.setMovementState(MovementState.STANDING);
    }

    public void walk(Direction direction) {
        hero.setMovementState(MovementState.WALKING);
        Position heroPosition = hero.getPosition();
        float newPosX = 0;
        if(walkIsPossible(direction, heroPosition)) {
            if(direction == Direction.RIGHT) {
                newPosX = heroPosition.getXPos() + 100 * Gdx.graphics.getDeltaTime();
            } else {
                newPosX = heroPosition.getXPos() - 100 * Gdx.graphics.getDeltaTime();
            }
            hero.setPosition(newPosX, heroPosition.getYPos());
        }
    }

    private boolean walkIsPossible(Direction direction, Position heroPosition) {
        return true;
    }

    public void jump() {
        hero.setMovementState(MovementState.JUMPING);

    }

    public void attack() {
        //TODO finish walk() method
    }

}
