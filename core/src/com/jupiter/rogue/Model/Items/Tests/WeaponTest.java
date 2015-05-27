package com.jupiter.rogue.Model.Items.Tests;

import com.jupiter.rogue.Model.Items.BlackDagger;
import com.jupiter.rogue.Model.Items.Weapon;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Oskar on 2015-05-27.
 */
public class WeaponTest {



    @Test
    public void testInitWeapon() throws Exception {
        Weapon weapon = new BlackDagger();

        assertEquals(Double.valueOf(weapon.getHitBoxModel().get(0)), Double.valueOf(weapon.getHitBoxLength()));
        assertEquals(Double.valueOf(weapon.getHitBoxModel().get(1)), Double.valueOf(weapon.getHitBoxHeight()));
        assertEquals(Double.valueOf(weapon.getHitBoxModel().get(2)), Double.valueOf(weapon.getHitBoxX()));
        assertEquals(Double.valueOf(weapon.getHitBoxModel().get(3)), Double.valueOf(weapon.getHitBoxY()));
        assertEquals(Double.valueOf(weapon.getHitBoxModel().get(4)), Double.valueOf(weapon.getHitBoxTilt()));
    }
}