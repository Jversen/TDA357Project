package com.jupiter.rogue.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.jupiter.rogue.Model.Creatures.Hero;
import com.jupiter.rogue.Utils.Enums.MovementState;

/**
 * Created by hilden on 2015-05-04.
 */
public class HeroView extends CreatureView {

    //singleton-instance of HeroView
    private static HeroView instance = null;

    private Animation fallingAnimation;
    private String spritesheetPathFalling;
    private String atlasFilePathFalling;
    private Float animationSpeedFalling;

    private Animation jumpingAnimation;
    private String spritesheetPathJumping;
    private String atlasFilePathJumping;
    private Float animationSpeedJumping;
    private Float jumpingAnimationTime;

    private Animation attackAnimation;
    private String spritesheetPathAttack;
    private String atlasFilePathAttack;
    private Float animationSpeedAttack;

    private Animation rangedAttackAnimation;
    private String spritesheetPathRangedAttack;
    private String atlasFilePathRangedAttack;
    private Float animationSpeedRangedAttack;


    private HeroView() {
        creature = Hero.getInstance();

        spritesheetPathRun = "Data//HeroAnimations//HeroRunning//HeroRunningRight.png";
        atlasFilePathRun = "Data//HeroAnimations//HeroRunning//HeroRunningRight.atlas";
        animationSpeedRun = 1/13f;

        spritesheetPathIdle = "Data//HeroAnimations//HeroIdle//HeroIdle.png";
        atlasFilePathIdle = "Data//HeroAnimations//HeroIdle//HeroIdle.atlas";
        animationSpeedIdle = 1f;

        spritesheetPathFalling = "Data//HeroAnimations//HeroFalling//HeroFalling.png";
        atlasFilePathFalling = "Data//HeroAnimations//HeroFalling//HeroFalling.atlas";
        animationSpeedFalling = 1f;

        spritesheetPathJumping = "Data//HeroAnimations//HeroJumping//HeroJumping.png";
        atlasFilePathJumping = "Data//HeroAnimations//HeroJumping//HeroJumping.atlas";
        animationSpeedJumping = 1/15f;

        spritesheetPathAttack = "Data//HeroAnimations//HeroAttack//heroAttack.png";
        atlasFilePathAttack = "Data//HeroAnimations//HeroAttack//heroAttack.atlas";
        animationSpeedAttack= 1/13f;

        spritesheetPathRangedAttack = "Data//HeroAnimations//HeroRanged//ranged.png";
        atlasFilePathRangedAttack = "Data//HeroAnimations//HeroRanged//ranged.atlas";
        animationSpeedRangedAttack= 1/30f;

        sprite = new Sprite();
        spriteBatch = new SpriteBatch();
        initAnimations();
    }

    public static HeroView getInstance() {
        if (instance == null) {
            instance = new HeroView();
        }
        return instance;
    }

    @Override
    public void initAnimations() {
        super.initAnimations();

        spriteSheet = new Texture(Gdx.files.internal(spritesheetPathFalling));
        atlas = new TextureAtlas(Gdx.files.internal(atlasFilePathFalling));
        fallingAnimation = new Animation(animationSpeedFalling, atlas.getRegions());

        spriteSheet = new Texture(Gdx.files.internal(spritesheetPathJumping));
        atlas = new TextureAtlas(Gdx.files.internal(atlasFilePathJumping));
        jumpingAnimation = new Animation(animationSpeedJumping, atlas.getRegions());

        spriteSheet = new Texture(Gdx.files.internal(spritesheetPathAttack));
        atlas = new TextureAtlas(Gdx.files.internal(atlasFilePathAttack));
        attackAnimation = new Animation(animationSpeedAttack, atlas.getRegions());

        spriteSheet = new Texture(Gdx.files.internal(spritesheetPathRangedAttack));
        atlas = new TextureAtlas(Gdx.files.internal(atlasFilePathRangedAttack));
        rangedAttackAnimation = new Animation(animationSpeedRangedAttack, atlas.getRegions());

        jumpingAnimationTime = jumpingAnimation.getAnimationDuration() - jumpingAnimation.getFrameDuration();  //Sets the animation time to the animation duration minus one frame
                                                                                                               //because first frame runs twice for some reason.
    }

    @Override
    protected Animation getCurrentAnimation() {
        if (creature.isAttackInProgress()) {
            if (((Hero)creature).isMeleeCurrentWeapon()) {
                return attackAnimation;
            } else {
                return rangedAttackAnimation;
            }
        } else {
            if (creature.getMovementState() == MovementState.WALKING) {
                return runningAnimation;
            } else if (creature.getMovementState() == MovementState.FALLING) {
                return fallingAnimation;
            } else if (creature.getMovementState() == MovementState.JUMPING) {
                if (stateTime > jumpingAnimationTime) {      //A check to see if the jumping animation has played, if so, performMove on to FALLING.
                    creature.setMovementState(MovementState.FALLING);
                }
                return jumpingAnimation;
            } else {
                return idleAnimation;
            }
        }
    }
}