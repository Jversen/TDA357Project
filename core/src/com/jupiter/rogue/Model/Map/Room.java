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
import com.jupiter.rogue.Model.Creatures.Enemy;
import lombok.Data;

import static com.jupiter.rogue.Model.Map.WorldConstants.PPM;


import java.util.ArrayList;

/**
 * Created by hilden on 2015-04-16.
 */
@Data
public class Room {
    public ArrayList<Enemy> enemies = new ArrayList<>();
    public ArrayList<Door> doors = new ArrayList<>();

    //TODO move all these someplace else
    private ArrayList<Body> bodies = new ArrayList<>();

    private TiledMap tiledMap;
    private World world;
    private TiledMapRenderer tiledMapRenderer;
    private float tileSize;
    private TiledMapTileLayer foregroundLayer;
    private TiledMapTileLayer sensorLayer;

    public Room() {
        world = WorldHolder.getInstance().getWorld();
        tiledMap = new TmxMapLoader().load("Rooms/RoomSwitchingTest2.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        foregroundLayer = (TiledMapTileLayer)tiledMap.getLayers().get(1);
        sensorLayer = (TiledMapTileLayer)tiledMap.getLayers().get(2);

        tileSize = foregroundLayer.getTileWidth();
        System.out.println(tileSize);

    }

    public ArrayList<Body> getBodies() {
        return bodies;
    }

    /* Create static bodies for every tile in layer 'layer'. Update later to support
    different kinds of layers. */
    public void createTileBodies(){

        BodyDef bodyDef = new BodyDef();
/*
        for (int row = 0; row < layer.getHeight(); row++) {

            //reset count
            int tileSize = 0;

            for (int col = 0; col < layer.getWidth(); col++) {



                Cell cell = layer.getCell(col, row);

                if (cell == null || cell.getTile() == null){
                    continue;
                }

                cell.


            }
        }
*/

        for (int row = 0; row < foregroundLayer.getHeight(); row++){
            for (int col = 0; col < foregroundLayer.getWidth(); col++){

                Cell cell = foregroundLayer.getCell(col, row);

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
                bodies.add(body);

                PolygonShape shape = new PolygonShape();
                shape.setAsBox(tileSize/2 / PPM, tileSize/2 / PPM);
                fixtureDef.shape = shape;

                body.createFixture(fixtureDef).setUserData("room"); //naming the roomfixture room.

                shape.dispose();
            }
        }
        //rows and cols are labeled wrong
        for (int row = 0; row < sensorLayer.getHeight(); row++){
            for (int col = 0; col < sensorLayer.getWidth(); col++){

                Cell cell = sensorLayer.getCell(col, row);

                if (cell == null || cell.getTile() == null){
                    continue;
                }

                System.out.println("Row: " + row + "Col: " + col);

                bodyDef.type = BodyType.StaticBody;

                /*Set the position to the tile number plus half the tilesize to compensate
                for body/libgdx drawing differences. */
                bodyDef.position.set((col + 0.5f) * tileSize / PPM,
                        (row + 0.5f) * tileSize / PPM);

                FixtureDef fixtureDef = new FixtureDef();
                fixtureDef.isSensor = true;



                Body body = world.createBody(bodyDef);

                bodies.add(body);

                PolygonShape shape = new PolygonShape();
                shape.setAsBox(tileSize/2 / PPM, tileSize/2 / PPM);
                fixtureDef.shape = shape;

                //TODO create a better positioning
                if(col == 0) {
                    body.createFixture(fixtureDef).setUserData("leftDoor"); //
                    System.out.println("leftDoor");
                } else if(col == sensorLayer.getWidth()-1) {
                    body.createFixture(fixtureDef).setUserData("rightDoor"); //naming the roomfixture room
                    System.out.println("rightDoor");
                }
                System.out.println("sensorCreated");

                shape.dispose();
            }
        }
    }
    public TiledMapRenderer getTiledMapRenderer() {
        return tiledMapRenderer;
    }


    public void initRoom() {
        createTileBodies();
    }
}
