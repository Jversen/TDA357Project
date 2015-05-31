package com.jupiter.rogue.Utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.jupiter.rogue.Model.Factories.EnemyFactory;
import com.jupiter.rogue.Model.Factories.WidowFactory;
import com.jupiter.rogue.Model.Items.*;

import java.util.*;

/**
 * Created by Johan on 2015-04-19.
 */
@lombok.Data
public final class WorldConstants {

    public static final float PPM = 32f; //Pixels per meter conversion unit
    public static final float HERO_START_XPOS = 128;
    public static final float HERO_START_YPOS = 80;
    public static final float GRAVITY = -10f;
    public static final Vector2 GRAVITY_VECTOR = new Vector2(0, GRAVITY);
    public static World CURRENT_WORLD = new World(GRAVITY_VECTOR, true);
    public static int TILE_SIZE = 32; //The tilesize of the tmx maps
    public static Array<Body> BODIES = new Array<>();
    public static ArrayList<Joint> JOINTS = new ArrayList<>();

    /*
    update if more of these types are added.
     */
    public static final List<String> ENEMYTYPES =
            new ArrayList<>(Arrays.asList("widow", "redDeath")); //The different enemies implemented.

    public static final List<String> CHESTTYPES =
            new ArrayList<>(Arrays.asList("weapon", "ring"));

    public static final List<Weapon> WEAPONS = new ArrayList<>(Arrays.asList(
            new BlackDagger(), new Boomstick()));

    public static final List<Ring> RINGS = new ArrayList<>(Arrays.asList(
            new SpeedRing(), new StrengthRing()));

}
