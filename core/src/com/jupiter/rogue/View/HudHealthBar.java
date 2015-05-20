package com.jupiter.rogue.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by Johan on 2015-05-20.
 */
public class HudHealthBar extends Actor {
    Texture texture;
    public HudHealthBar(){
        texture = new Texture(Gdx.files.internal("badlogic.jpg"));
    }

    @Override
    public void draw(Batch batch, float alpha){
        batch.draw(texture,0,0);
    }
}
