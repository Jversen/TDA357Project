package com.jupiter.rogue.Controller.Behaviors.AttackBehaviors;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.jupiter.rogue.Model.Creatures.Hero;
import com.jupiter.rogue.Model.Enums.Direction;
import com.jupiter.rogue.Controller.Behaviors.Behavior;
import com.jupiter.rogue.Utils.WorldConstants;

import static com.jupiter.rogue.Utils.WorldConstants.PPM;

/**
 * Created by Oskar on 2015-05-18.
 */
public class MeleeAttack extends Behavior implements AttackBehavior {

    private Body heroBody = WorldConstants.BODIES.get(0);

    public MeleeAttack(Body body){
        this.body = body;
    }

    //Method for enemies that use attacks in melee range.

    public void attack(Direction direction){
        body.setLinearVelocity(0.0f, body.getLinearVelocity().y);
        if(direction == Direction.LEFT){
            if(Math.abs(this.getBody().getPosition().y - (Hero.getInstance().getY())) <= 20/PPM){
                heroBody.applyLinearImpulse(new Vector2(-5f, 0f), heroBody.getPosition(), false);
                if(heroBody.getLinearVelocity().x < -5) {
                    heroBody.setLinearVelocity(-5, heroBody.getLinearVelocity().y);
                }
            }
            else{
                heroBody.applyLinearImpulse(new Vector2(-4f, 4f), heroBody.getPosition(), false);
                if(heroBody.getLinearVelocity().x < -4 || heroBody.getLinearVelocity().y > 4) {
                    heroBody.setLinearVelocity(-4, 4);
                }
            }
        }
        else{
            if(Math.abs(this.getBody().getPosition().y - (Hero.getInstance().getY())) <= 20/PPM){
                heroBody.applyLinearImpulse(new Vector2(5f, 0f), heroBody.getPosition(), false);
                if(heroBody.getLinearVelocity().x > 5) {
                    heroBody.setLinearVelocity(5, heroBody.getLinearVelocity().y);
                }
            }
            else{
                heroBody.applyLinearImpulse(new Vector2(4f, 4f), heroBody.getPosition(), false);
                if(heroBody.getLinearVelocity().x > 4 || heroBody.getLinearVelocity().y > 4) {
                    heroBody.setLinearVelocity(4, 4);
                }
            }
        }
    }

}
