package com.jupiter.rogue.Controller;

import com.badlogic.gdx.physics.box2d.Body;
import com.jupiter.rogue.Model.Creatures.Enemy;
import com.jupiter.rogue.Utils.WorldConstants;
import com.jupiter.rogue.View.EnemyView;

/**
 * Created by Johan on 2015-05-10.
 */
@lombok.Data
public abstract class EnemyController {

    EnemyView enemyView;
    Enemy enemy;
    Body body;

    public EnemyController(float xPos, float yPos, int level, boolean elite){} //May change later
    public abstract void update();
    public abstract void initBody();
    /* destroys the body when leaving the room. This information should possibly be pooled instead.
    (libgdx poolable interface)
     */

/*    public void destroyBody(){
        WorldConstants.CURRENT_WORLD.destroyBody(body);
    };*/

}
