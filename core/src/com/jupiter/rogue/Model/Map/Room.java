package com.jupiter.rogue.Model.Map;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

import java.util.ArrayList;

/**
 * Created by hilden on 2015-04-16.
 */

public class Room {

    private TiledMap tiledMap;
    private OrthographicCamera camera;
    private TiledMapRenderer tiledMapRenderer;

    public Room() {
        tiledMap = new TmxMapLoader().load("Rooms/BoxRoom.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        TiledMapTileLayer layer = (TiledMapTileLayer)tiledMap.getLayers().get(0);

        BodyDef testFloorBodyDef = new BodyDef();
        testFloorBodyDef.type = BodyType.StaticBody;
        testFloorBodyDef.position.set(0, 0);

        World world = WorldConstants.getInstance().getWorld();

        Body testFloorBody = world.createBody(testFloorBodyDef);
        PolygonShape floorShape = new PolygonShape();
        floorShape.setAsBox(200, 20);

        testFloorBody.createFixture(floorShape, 0.0f);
        floorShape.dispose();

    }

    public TiledMapRenderer getTiledMapRenderer() {
        return tiledMapRenderer;
    }


}
