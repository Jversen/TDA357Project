package com.jupiter.rogue.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.jupiter.rogue.Model.Creatures.Hero;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Johan on 2015-05-20.
 */
public class Hud extends Actor {

    private static Hud instance = null;
    Texture texture;
    Sprite sprite;
    float currentHP;
    float maxHP;

    private Hud(){

        texture = new Texture(Gdx.files.internal("badlogic.jpg"));
        sprite = new Sprite(texture, 0, 0, 100, 10);
        sprite.setPosition(20, Gdx.graphics.getHeight() - 20);
        sprite.setOrigin(0, 0);
    }

    public static Hud getInstance() {
        if(instance == null) {
            instance = new Hud();
        }
        return instance;
    }

    @Override
    public void draw(Batch batch, float alpha){

        sprite.draw(batch);
    }

    public void update(int hp){
        System.out.println("Health = " + hp);
        currentHP = (float)Hero.getInstance().getCurrentHealthPoints();
        maxHP = (float)Hero.getInstance().getMaxHealthPoints();

        sprite.setScale(currentHP / maxHP, 1);

        System.out.println("ekvation: " + Hero.getInstance().getCurrentHealthPoints() / Hero.getInstance().getMaxHealthPoints());
        System.out.println("currenthp: " + Hero.getInstance().getCurrentHealthPoints());
        System.out.println("maxHP: " + Hero.getInstance().getMaxHealthPoints());
    }
}
