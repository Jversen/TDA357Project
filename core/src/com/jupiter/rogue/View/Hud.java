package com.jupiter.rogue.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
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
    int heroHp;

    private Hud(){

        texture = new Texture(Gdx.files.internal("badlogic.jpg"));
        heroHp = Hero.getInstance().getCurrentHealthPoints();
    }

    public static Hud getInstance() {
        if(instance == null) {
            instance = new Hud();
        }
        return instance;
    }

    @Override
    public void draw(Batch batch, float alpha){
        batch.draw(texture,heroHp,0);
    }

    public void update(int hp){
        System.out.println("Health = " + hp);
        this.heroHp = hp;
    }
}
