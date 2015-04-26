package com.jupiter.rogue.Model.Map;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import static com.jupiter.rogue.Model.Map.WorldConstants.PPM;


import java.util.ArrayList;

/**
 * Created by hilden on 2015-04-16.
 */

public class Room {

    private TiledMap tiledMap;
    private World world;
    private TiledMapRenderer tiledMapRenderer;
    private float tileSize;
    private TiledMapTileLayer layer;

    public Room() {
        world = WorldHolder.getInstance().getWorld();
        tiledMap = new TmxMapLoader().load("Rooms/BoxRoom2.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        layer = (TiledMapTileLayer)tiledMap.getLayers().get(0);

        tileSize = layer.getTileWidth();
        System.out.println(tileSize);

        createTileBodies(this);


    }

    /* Create static bodies for every tile in layer 'layer'. Update later to support
    different kinds of layers. */
    private void createTileBodies(Room room){

        BodyDef bodyDef = new BodyDef();
        TiledMapTileLayer layer = room.layer;

        for (int row = 0; row < layer.getHeight(); row++){
            for (int col = 0; col < layer.getWidth(); col++){

                Cell cell = layer.getCell(col, row);

                if (cell == null || cell.getTile() == null){
                    continue;
                }

                bodyDef.type = BodyType.StaticBody;

                /*Set the position to the tile number plus half the tilesize to compensate
                for body/libgdx drawing differences. */
                bodyDef.position.set((col + 0.5f) * tileSize / PPM,
                        (row + 0.5f) * tileSize / PPM);

                FixtureDef fixtureDef = new FixtureDef();



                Body body = world.createBody(bodyDef);

                PolygonShape shape = new PolygonShape();
                shape.setAsBox(tileSize/2 / PPM, tileSize/2 / PPM);
                fixtureDef.shape = shape;

                body.createFixture(fixtureDef);

                shape.dispose();
            }
        }
    }
    public TiledMapRenderer getTiledMapRenderer() {
        return tiledMapRenderer;
    }


}
