package com.jupiter.rogue.Model.Creatures;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.jupiter.rogue.Model.Enums.MovementState;
import com.jupiter.rogue.Model.Items.MeleeWeapon;
import com.jupiter.rogue.Model.Items.RangedWeapon;

/**
 * Created by Johan on 16/04/15.
 */
public class Hero extends Creature {

    private static Hero instance = null;

    private MeleeWeapon meleeWeapon;
    private RangedWeapon rangedWeapon;

    private MovementState movementState = MovementState.JUMPING;
    private Texture texture = new Texture(Gdx.files.internal("Data//pixHeroAtlas.png"));
    private TextureAtlas atlas = new TextureAtlas("Data//pixHeroAtlas.atlas");



    TextureRegion                   currentFrame;
    TextureRegion[]                 walkFrames;
    float stateTime;

    //float xPos;
   // float yPos;

    private Hero (float xPos, float yPos) {

        setPosition(xPos, yPos);

        scale = 3f;
        this.maxHealthPoints = 100;
        this.currentHealthPoints = maxHealthPoints;
        this.movementSpeed = 125;

        frameCols=7;
        frameRows=1;

        setSpriteSheet(texture);
        sprite = new Sprite();

        TextureRegion[][] tmp = TextureRegion.split(spriteSheet,
                spriteSheet.getWidth()/frameCols, spriteSheet.getHeight()/frameRows);
        walkFrames = new TextureRegion[frameCols * frameRows];
        
        int index = 0;
        for (int i = 0; i < frameRows; i++) {
            for (int j = 0; j < frameCols; j++) {
                    walkFrames[index++] = tmp[i][j];
            }
        }
        animation = new Animation(1/15f, atlas.getRegions());
        //animation = new Animation(0.025f, walkFrames);      // #11
        SpriteBatch spriteBatch = new SpriteBatch();                // #12
        stateTime = 0f;



        setBounds(xPos, yPos, this.texture.getWidth(), this.texture.getHeight());

        //TODO finish rest of stats
    }
    public Sprite updateAnimation(float deltaTime){

        stateTime += deltaTime;           // #15
        currentFrame = animation.getKeyFrame(stateTime, true);  // #16
        sprite = new Sprite(currentFrame);
        sprite.setPosition(position.getXPos(), position.getYPos());
        sprite.scale(scale);
        return sprite;
    }

    public static Hero getInstance() {
        if(instance == null) {
            instance = new Hero(100,100);
        }
        return instance;
    }
}
