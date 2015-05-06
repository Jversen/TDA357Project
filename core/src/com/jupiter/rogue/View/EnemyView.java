package com.jupiter.rogue.View;

/**
 * Created by hilden on 2015-05-04.
 */
public class EnemyView {                          //************ ADD VARIABLE IN ROOM TO TRACK THE NBR Of AND WHAT TYPE OF ENEMIES IT HOLDS. READ FROM ROOM WHAT ENEMIES ARE IN CURRENT ROOM.
                                                  // MAKE SPRITES OF THOSE ENEMIES. UPDATE THOSE SPRITES *******************
/*
    private Sprite sprite;
    private Texture spriteSheet;
    private TextureAtlas atlas;
    private TextureRegion currentFrame;
    private Animation animation;
    private SpriteBatch spriteBatch;

    private Animation enemyAnimation1;
    private Enemy enemy1;

    private float stateTime;



    public EnemyView() {
        sprite = new Sprite();
        spriteBatch = new SpriteBatch();
        initAnimations();
    }

    private void updateCurrentEnemies() {} //Updates what animations are used in the current room.

    //Initiation of all the enemies animations
    private void initAnimations() {

        //stateTime = 0f;

        spriteSheet = new Texture(Gdx.files.internal(""));
        atlas = new TextureAtlas("");
        enemyAnimation1 = new Animation(1/10f, atlas.getRegions());
    }

    //Returns the current animation depending on the input enemies movementState.
    private Animation getCurrentAnimation() {

        if (enemy.getMovementState() == MovementState.WALKING) {
            return ;
        } else {
            return ;
        }
    }

    public void updateAnimation(float deltaTime){

        animation = getCurrentAnimation();
        stateTime += deltaTime;
        currentFrame = animation.getKeyFrame(stateTime, true);
        sprite.setRegion(currentFrame);
        sprite.setPosition(enemy.getX() * PPM, enemy.getY() * PPM);

        /* Draws the current frame of the hero animation, at position x,y of it's body
        scaled to the PPM, its origin offset (for scaling and rotating) at half the body
        (A bit unsure about that, got it right by experimenting),
        full size of the textureregion, and finally scaled to PPM and rotated to match
        the rotation of the body.
         */ /*
        spriteBatch.begin();
        spriteBatch.draw(currentFrame,
                hero.getX() * PPM,
                hero.getY() * PPM,
                0.5f,
                0.5f,
                1f, 1f,
                getDirValue() * PPM, PPM, 0);

        spriteBatch.end();
    }

    //Returns an int value of the enum Direction. -1 for left, 1 for right.
    //Added to help set the spritescale to negative if walking left.
    //This is also where you set the SCALE of the sprites.
    private int getDirValue() {
        if (hero.getDirection() == Direction.LEFT) {
            return -1;
        } else {
            return 1;
        }
    } */
}