package com.jupiter.rogue.Controller;

import com.badlogic.gdx.physics.box2d.Body;
import com.jupiter.rogue.Model.Creatures.Enemy;
import com.jupiter.rogue.Model.Creatures.Hero;
import com.jupiter.rogue.Utils.WorldConstants;
import com.jupiter.rogue.View.EnemyView;

import static com.jupiter.rogue.Utils.WorldConstants.PPM;

/**
 * Created by Johan on 2015-05-10.
 */
@lombok.Data
public abstract class EnemyController {

    EnemyView enemyView;
    Enemy enemy;
    Body body;

   // public EnemyController(float xPos, float yPos, int level, boolean elite){} //May change later
    public abstract void update();
    public abstract void initBody();

    public boolean heroNotNear(){
        return((Math.abs((enemy.getX() + (enemy.getBodyWidth()/2)/PPM) - (Hero.getInstance().getX() + 5/PPM)) > 200/PPM) ||
                (Math.abs((enemy.getY() + (enemy.getBodyHeight()/2)/PPM) - (Hero.getInstance().getY() + 10.5/PPM)) > 200/PPM));
    }

    public boolean heroInRange(){
        return((Math.abs((enemy.getX() + (enemy.getBodyWidth()/2)/PPM) - (Hero.getInstance().getX() + 5/PPM)) <= enemy.getAttackRange()/PPM) &&
                (Math.abs((enemy.getY() + (enemy.getBodyHeight()/2)/PPM) - (Hero.getInstance().getY() + 10.5/PPM)) <= 38/PPM));
    }

    /* destroys the body when leaving the room. This information should possibly be pooled instead.
    (libgdx poolable interface)*/

/*    public void destroyBody(){
        WorldConstants.CURRENT_WORLD.destroyBody(body);
    };*/

}
