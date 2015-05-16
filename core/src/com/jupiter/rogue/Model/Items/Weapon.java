package com.jupiter.rogue.Model.Items;

import javax.swing.*;

/**
 * Created by Johan on 16/04/15.
 */
public abstract class Weapon extends Item {
    protected int damage;

    //All the variables to determine the weapons hitbox.
    protected float hitBoxLength;
    protected float hitBoxHeight;
    protected float hitBoxX;
    protected float hitBoxY;
    protected float hitBoxTilt;

    //A JList to keep the hitbox values to be able to return all of them at once.
    protected JList<Float> hitBoxValues;
    protected DefaultListModel<Float> hitBoxModel;

    protected void initWeapon() {
        hitBoxModel = new DefaultListModel<>();
        hitBoxValues = new JList<>(hitBoxModel);
        addHitBoxValues();
    }

    private void addHitBoxValues() {
        hitBoxModel.addElement(hitBoxLength);
        hitBoxModel.addElement(hitBoxHeight);
        hitBoxModel.addElement(hitBoxX);
        hitBoxModel.addElement(hitBoxY);
        hitBoxModel.addElement(hitBoxTilt);
    }

    //returns the values of the shape that the weapons hitbox uses
    public JList<Float> getHitBoxValues() {
        return hitBoxValues;
    }
}
