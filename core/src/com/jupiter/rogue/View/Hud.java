package com.jupiter.rogue.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.jupiter.rogue.Model.Creatures.Hero;
import com.jupiter.rogue.Model.Map.Map;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Johan on 2015-05-20.
 */
public class Hud extends Actor {

    private static Hud instance = null;
    private Texture texture;
    private Sprite healthBar;
    private BitmapFont font;
    float currentHP;
    float maxHP;
    int startGfxWidth;
    int startGfxHeight;
    String level;

    private Hud(){

        //startGfxWidth = 320;    //A hack. Set this to the actual game resolution later.
       // startGfxHeight = 180;

        font = new BitmapFont();    //Creates new font
        font.setColor(Color.WHITE);
        font.setScale(0.5f);

        texture = new Texture(Gdx.files.internal("Data/HUD/healthBar.png"));
        healthBar = new Sprite(texture, 0, 0, 25, 3);
        healthBar.setPosition(20, 170);
        healthBar.setOrigin(0, 0);
    }

    public static Hud getInstance() {
        if(instance == null) {
            instance = new Hud();
        }
        return instance;
    }

    @Override
    public void draw(Batch batch, float alpha){

        healthBar.draw(batch);
        /* Just some number for show. Should discuss this design of showing current room.*/
        level = ("Room: " + Map.getInstance().getCurrentRoomNbr());

        font.draw(batch, level, 250, 170);
    }

    public void updateHealthBar(){

        currentHP = (float)Hero.getInstance().getCurrentHealthPoints();
        maxHP = (float)Hero.getInstance().getMaxHealthPoints();

        healthBar.setScale(currentHP / maxHP, 1);

    }
}
