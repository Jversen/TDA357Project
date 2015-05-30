package com.jupiter.rogue.Model.Creatures.Tests;

import com.jupiter.rogue.Model.Creatures.Hero;
import com.jupiter.rogue.Model.Items.BlackDagger;
import com.jupiter.rogue.Model.Items.DoubleBarreled;
import com.jupiter.rogue.Model.Items.Ring;
import com.jupiter.rogue.Model.Items.Weapon;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Oskar on 2015-05-27.
 */
public class HeroTest {

    Hero hero = Hero.getInstance();

    @Test
    public void testIncreaseExperience() throws Exception {
        hero.setLevel(1);
        hero.setExperiencePoints(0);
        hero.setExperienceRoof(100);
        hero.increaseExperience(50);
        assertEquals(hero.getExperiencePoints(), 50);

        hero.setExperiencePoints(0);
        hero.setStatPoints(0);
        hero.increaseExperience(100);
        assertEquals(hero.getLevel(), 2);
        assertEquals(hero.getStatPoints(), 1);
        assertEquals(hero.getExperiencePoints(), 0);
        assertEquals(hero.getExperienceRoof(), 150);
    }

    @Test
    public void testIncreaseStats() throws Exception {
        hero.setStatPoints(5);
        hero.increaseStats("strength");
        hero.increaseStats("strength");
        hero.increaseStats("intellect");
        hero.increaseStats("agility");
        assertEquals(hero.getStrength(), 3);
        assertEquals(hero.getIntellect(), 2);
        assertEquals(hero.getAgility(), 2);
        assertEquals(hero.getStatPoints(), 1);

    }

    @Test
    public void testGetCurrentWeapon() throws Exception {
        hero.setMeleeCurrentWeapon(true);
        assertEquals(hero.getCurrentWeapon(), hero.getMeleeWeapon());

        hero.setMeleeCurrentWeapon(false);
        hero.setRangedWeapon(new DoubleBarreled());
        assertEquals(hero.getCurrentWeapon(), hero.getRangedWeapon());

        hero.setRangedWeapon(null);
        assertEquals(hero.getCurrentWeapon(), hero.getMeleeWeapon());
    }

    @Test
    public void testSwapWeapon() throws Exception {
        hero.setMeleeCurrentWeapon(true);
        hero.setRangedWeapon(new DoubleBarreled());
        hero.swapWeapon();
        assertEquals(hero.getCurrentWeapon(), hero.getRangedWeapon());
        hero.swapWeapon();
        assertEquals(hero.getCurrentWeapon(), hero.getMeleeWeapon());

        hero.setMeleeCurrentWeapon(true);
        hero.setRangedWeapon(null);
        hero.swapWeapon();
        assertEquals(hero.getCurrentWeapon(), hero.getMeleeWeapon());
    }

    @Test
    public void testPickUpItem() throws Exception {
        Weapon meleeWeapon = new BlackDagger();
        Weapon rangeWeapon = new DoubleBarreled();
        Ring ring1 = new Ring();
        Ring ring2 = new Ring();
        hero.setMeleeWeapon(null);
        hero.setRangedWeapon(null);
        hero.setRingRight(null);
        hero.setRingLeft(null);

        hero.pickUpItem(meleeWeapon);
        assertEquals(hero.getMeleeWeapon(), meleeWeapon);

        hero.pickUpItem(rangeWeapon);
        assertEquals(hero.getRangedWeapon(), rangeWeapon);

        hero.pickUpItem(ring1);
        assertEquals(hero.getRingRight(), ring1);

        hero.pickUpItem(ring2);
        assertEquals(hero.getRingLeft(), ring2);

    }
}