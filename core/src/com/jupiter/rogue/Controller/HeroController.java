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
    Position heroPosition = hero.getPosition();

    public void update(){
        
    }
    public void relax() {
        hero.setMovementState(MovementState.STANDING);
    }

    public void walk(Direction direction) {
        hero.setMovementState(MovementState.WALKING);
        hero.setDirection(direction);
        float newPosX = 0;
        float newPosY = 0;

        if(walkIsPossible(direction, heroPosition)) {

            if(direction == Direction.RIGHT) {
                newPosX = heroPosition.getXPos() + hero.getMovementSpeed() * Gdx.graphics.getDeltaTime();
            } else {
                newPosX = heroPosition.getXPos() - hero.getMovementSpeed() * Gdx.graphics.getDeltaTime();
            }
            hero.setPosition(newPosX, heroPosition.getYPos());
        }
    }

    private boolean walkIsPossible(Direction direction, Position heroPosition) {
        return true;
    }

    public void jumpIfPossible() {
        hero.setMovementState(MovementState.JUMPING);
        if (!(hero.getMovementState().equals(MovementState.JUMPING))) {
            jump();
        }
    }

    private void jump() {
        float newPosY = 0;
        for (int i = 0; i < 10; i++) {
            switch (i) {
                case 1-5:
                    newPosY = heroPosition.getYPos() + 3;
                    break;
                case 6-9:
                    newPosY = heroPosition.getYPos() + 2;
                    break;
                case 10:
                    newPosY = heroPosition.getYPos() + 1;
                    break;
            }
            hero.setPosition(heroPosition.getXPos(), newPosY);
        }
    }

    public void attack() {
        //TODO finish walk() method
    }
}
