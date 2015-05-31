package com.jupiter.rogue.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.jupiter.rogue.Model.Creatures.Hero;


/**
 * Created by Oskar on 2015-05-31.
 */
public class AttributeMenu extends Actor {

    private static AttributeMenu instance = null;
    private Texture texture;
    private Sprite background1;
    private Sprite background2;
    private BitmapFont strengthFont;
    private BitmapFont agilityFont;
    private BitmapFont intellectFont;
    private BitmapFont meleeWeaponTitleFont;
    private BitmapFont rangedWeaponTitleFont;
    private BitmapFont meleeWeaponFont;
    private BitmapFont rangedWeaponFont;
    private BitmapFont ring1TitleFont;
    private BitmapFont ring1Font;
    private BitmapFont ring2TitleFont;
    private BitmapFont ring2Font;
    int strength;
    int agility;
    int intellect;
    String heroStrength;
    String heroAgility;
    String heroIntellect;
    String meleeWeapon;
    String rangedWeapon;
    String ring1;
    String ring2;



    private AttributeMenu(){

        texture = new Texture(Gdx.files.internal("Data/HUD/AttributesMenubg1.png"));
        background1 = new Sprite(texture, 0, 0, 66, 116);
        background1.setPosition(50, 30);
        background1.setOrigin(0, 0);
        background1.setScale(1, 1);

        texture = new Texture(Gdx.files.internal("Data/HUD/AttributesMenubg2.png"));
        background2 = new Sprite(texture, 0, 0, 60, 110);
        background2.setPosition(53, 33);
        background2.setOrigin(0, 0);
        background2.setScale(1, 1);

        strengthFont = new BitmapFont();
        strengthFont.setColor(Color.WHITE);
        strengthFont.setScale(0.35f);

        agilityFont = new BitmapFont();
        agilityFont.setColor(Color.WHITE);
        agilityFont.setScale(0.35f);

        intellectFont = new BitmapFont();
        intellectFont.setColor(Color.WHITE);
        intellectFont.setScale(0.35f);

        meleeWeaponTitleFont = new BitmapFont();
        meleeWeaponTitleFont.setColor(Color.WHITE);
        meleeWeaponTitleFont.setScale(0.35f);

        meleeWeaponFont = new BitmapFont();
        meleeWeaponFont.setColor(Color.WHITE);
        meleeWeaponFont.setScale(0.35f);

        rangedWeaponTitleFont = new BitmapFont();
        rangedWeaponTitleFont.setColor(Color.WHITE);
        rangedWeaponTitleFont.setScale(0.35f);

        rangedWeaponFont = new BitmapFont();
        rangedWeaponFont.setColor(Color.WHITE);
        rangedWeaponFont.setScale(0.35f);

        ring1TitleFont = new BitmapFont();
        ring1TitleFont.setColor(Color.WHITE);
        ring1TitleFont.setScale(0.35f);

        ring1Font = new BitmapFont();
        ring1Font.setColor(Color.WHITE);
        ring1Font.setScale(0.35f);

        ring2TitleFont = new BitmapFont();
        ring2TitleFont.setColor(Color.WHITE);
        ring2TitleFont.setScale(0.35f);

        ring2Font = new BitmapFont();
        ring2Font.setColor(Color.WHITE);
        ring2Font.setScale(0.35f);

    }

    public static AttributeMenu getInstance() {
        if(instance == null) {
            instance = new AttributeMenu();
        }
        return instance;
    }

    @Override
    public void draw(Batch batch, float alpha){

        updateAttributeMenu();

        background1.draw(batch);
        background2.draw(batch);
        strengthFont.draw(batch, heroStrength, 55, 140);
        agilityFont.draw(batch, heroAgility, 55, 130);
        intellectFont.draw(batch, heroIntellect, 55, 120);
        meleeWeaponTitleFont.draw(batch, "Melee Weapon", 55, 105);
        meleeWeaponFont.draw(batch, meleeWeapon, 55, 95);
        rangedWeaponTitleFont.draw(batch, "Ranged Weapon", 55, 80);
        rangedWeaponFont.draw(batch, rangedWeapon, 55, 70);
        ring1TitleFont.draw(batch, "Left Ring", 55, 55);
        ring1Font.draw(batch, ring1, 55, 45);
        ring2TitleFont.draw(batch, "Right Ring", 85, 55);
        ring2Font.draw(batch, ring2, 85, 45);

    }

    public void updateAttributeMenu(){
        strength = Hero.getInstance().getStrength();
        agility = Hero.getInstance().getAgility();
        intellect = Hero.getInstance().getIntellect();

        meleeWeapon = Hero.getInstance().getMeleeWeapon().getWeaponName();
        if(Hero.getInstance().getRangedWeapon() != null) {
            rangedWeapon = Hero.getInstance().getRangedWeapon().getWeaponName();
        }else{
            rangedWeapon = "Empty";
        }

        if(Hero.getInstance().getRingLeft() != null) {
            ring1 = Hero.getInstance().getRingLeft().toString();
        }else{
            ring1 = "Empty";
        }
        if(Hero.getInstance().getRingRight() != null){
            ring2 = Hero.getInstance().getRingRight().toString();
        }else{
            ring2 = "Empty";
        }


        heroStrength = ("Strength: " + strength);
        heroAgility = ("Agility: " + agility);
        heroIntellect = ("Intellect: " + intellect);
    }

}
