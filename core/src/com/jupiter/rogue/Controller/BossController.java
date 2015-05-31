package com.jupiter.rogue.Controller;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.jupiter.rogue.Controller.Behaviors.AttackBehaviors.MeleeAttack;
import com.jupiter.rogue.Controller.Behaviors.AttackedBehaviors.Immovable;
import com.jupiter.rogue.Controller.Behaviors.JumpBehaviors.CanNotJump;
import com.jupiter.rogue.Controller.Behaviors.MoveBehaviors.Stationary;
import com.jupiter.rogue.Model.Creatures.Boss;
import com.jupiter.rogue.Utils.Position;
import com.jupiter.rogue.Utils.WorldConstants;
import com.jupiter.rogue.View.BossView;

import java.util.Timer;
import java.util.TimerTask;

import static com.jupiter.rogue.Utils.WorldConstants.PPM;

/**
 * Created by hilden on 2015-05-30.
 */
public class BossController extends EnemyController {
    private Position startPosition;

    private boolean attackReady;
    private boolean random;
    private Timer timer;

    private PolygonShape shape;
    private FixtureDef weaponSensorFixtureDef;
    private Fixture weaponSensorFixture;

    public BossController(Boss boss) {
        this.enemy = boss;

        startPosition = enemy.getPosition();
        this.initBody();
    }

    @Override
    public void initBody() {
        super.initBody();

        //Sets the move behaivors of this enemy.
        attackBehavior = new MeleeAttack(this.body);
        jumpBehavior = new CanNotJump();
        moveBehavior = new Stationary();
        takeDamageBehavior = new Immovable();

        this.attackReady = false;
        timer = new Timer();
        random = true;
    }

    @Override
    public void update() {
        super.updatePhysics();

        if (enemy.isCreatureDying()) {
            this.die();
        } else {
            if (attackReady) {
                attack();
            } else {
                this.idle();
            }
        }
    }

    protected void createHitbox() {
        //reinitializes the shape of the fixturedef.
        shape = new PolygonShape();

        //creates a fixture with a shape on the correct side of the hero using the helpmethod.
        hitBoxShapeMaker(); //useing the helpmethod.
        weaponSensorFixtureDef.shape = shape;
        if (!WorldConstants.CURRENT_WORLD.isLocked()) {
            weaponSensorFixture = body.createFixture(weaponSensorFixtureDef);
        }
        weaponSensorFixture.setSensor(true);
        weaponSensorFixture.setUserData("enemyHitbox");
        //clears memory
        shape.dispose();
    }

    protected void removeHitbox() {
        weaponSensorFixture.setUserData("dead");
    }

    //help-method to the createWeaponHitBox methods, gets the hitbox information from the heroes weapon and then sets a hitbox accordingly depending on what direction the hero is facing.
    private void hitBoxShapeMaker() {
        if (((Boss)enemy).isAttackInProgress1()) {
            shape.setAsBox(enemy.getAttackHitBoxWidth() / PPM, enemy.getAttackHitBoxHeight() / PPM, new Vector2((-1 * enemy.getAttackHitBoxX()) / PPM, enemy.getAttackHitBoxY() / PPM), 0);
        } else if (((Boss)enemy).isAttackInProgress2()) {
            shape.setAsBox(((Boss)enemy).getAttackHitBox2Width() / PPM, ((Boss)enemy).getAttackHitBox2Height() / PPM, new Vector2((-1 * ((Boss)enemy).getAttackHitBox2X()) / PPM, ((Boss)enemy).getAttackHitBox2Y() / PPM), 0);
        }
    }

    private void attack() {
        if (random) {
            enemy.attack();
            timer.schedule(new CreateHitBoxTask(), 1000);
        } else {
            ((Boss)enemy).attack2();
            timer.schedule(new CreateHitBoxTask(), 1000);
        }
        random ^= true;
    }

    private void idle() {
        enemy.relax();
    }

    private void die() {
        enemy.die();
    }

    //A nestled class to implement a timertask. Timertask to control the delay for hitboxes to be created.
    class CreateHitBoxTask extends TimerTask {
        public void run() {
            if (!enemy.isCreatureDying()) {
                createHitbox();
                timer.schedule(new RemoveHitBoxTask(), 1000);
            }
        }
    }

    //A nestled class to implement a timertask. Timertask to control the delay for hitboxes to be removed.
    class RemoveHitBoxTask extends TimerTask {
        public void run() {
            removeHitbox();
            ((Boss)enemy).setAttackInProgress1(false);
            ((Boss)enemy).setAttackInProgress2(false);
            timer.schedule(new AttackReadyTask(), 3000);
        }
    }

    //A nestled class to implement a timertask. Timertask to control the attack cooldown of enemies.
    class AttackReadyTask extends TimerTask {
        public void run() {
            attackReady = true;
        }
    }
}
