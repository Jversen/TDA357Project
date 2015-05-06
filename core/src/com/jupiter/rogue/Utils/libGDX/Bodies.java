package com.jupiter.rogue.Utils.libGDX;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import lombok.Data;

import java.util.ArrayList;

/**
 * Created by Johan on 05/05/15.
 */
@Data
public class Bodies {
    public static Body HERO_BODY;
    public static BodyDef HERO_BODY_DEF;
    public static Fixture HERO_FIXTURE;
}
