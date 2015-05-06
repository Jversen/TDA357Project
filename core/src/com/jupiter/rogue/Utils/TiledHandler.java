package com.jupiter.rogue.Utils;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import static com.jupiter.rogue.Utils.WorldConstants.BODIES;
import static com.jupiter.rogue.Utils.WorldConstants.PPM;

/**
 * Created by Johan on 06/05/15.
 */
public class TiledHandler {
    private TiledMapRenderer renderer;
    private TiledMap tiledMap;
    private float tileSize;
    private TiledMapTileLayer foregroundLayer;
    private TiledMapTileLayer sensorLayer;

    public TiledHandler(String path) {
        tiledMap = new TmxMapLoader().load(path);
        renderer = new OrthogonalTiledMapRenderer(tiledMap);

        foregroundLayer = (TiledMapTileLayer)tiledMap.getLayers().get(1);
        sensorLayer = (TiledMapTileLayer)tiledMap.getLayers().get(2);

        tileSize = foregroundLayer.getTileWidth();
    }

    public void initRoom() {
        createTileBodies();
    }

     /* Create static bodies for every tile in layer 'layer'. Update later to support
    different kinds of layers. */
    public void createTileBodies() {
        BodyDef bodyDef = new BodyDef();

        for (int row = 0; row < foregroundLayer.getHeight(); row++){
            for (int col = 0; col < foregroundLayer.getWidth(); col++){

                TiledMapTileLayer.Cell cell = foregroundLayer.getCell(col, row);

                if (cell == null || cell.getTile() == null){
                    continue;
                }

                bodyDef.type = BodyDef.BodyType.StaticBody;

                /*Set the position to the tile number plus half the tilesize to compensate
                for body/libgdx drawing differences. */
                bodyDef.position.set((col + 0.5f) * tileSize / PPM,
                        (row + 0.5f) * tileSize / PPM);

                FixtureDef fixtureDef = new FixtureDef();

                Body body = WorldConstants.CURRENT_WORLD.createBody(bodyDef);
                body.setUserData("room");
                WorldConstants.BODIES.add(body);

                PolygonShape shape = new PolygonShape();
                shape.setAsBox(tileSize/2 / PPM, tileSize/2 / PPM);
                fixtureDef.shape = shape;

                body.createFixture(fixtureDef).setUserData("room"); //naming the roomfixture room.

                shape.dispose();
            }
        }

        for (int row = 0; row < sensorLayer.getHeight(); row++){
            for (int col = 0; col < sensorLayer.getWidth(); col++){

                TiledMapTileLayer.Cell cell = sensorLayer.getCell(col, row);

                if (cell == null || cell.getTile() == null){
                    continue;
                }

                System.out.println("Row: " + row + "Col: " + col);

                bodyDef.type = BodyDef.BodyType.StaticBody;

                /*Set the position to the tile number plus half the tilesize to compensate
                for body/libgdx drawing differences. */
                bodyDef.position.set((col + 0.5f) * tileSize / PPM,
                        (row + 0.5f) * tileSize / PPM);

                FixtureDef fixtureDef = new FixtureDef();
                fixtureDef.isSensor = true;


                Body body = WorldConstants.CURRENT_WORLD.createBody(bodyDef);

                WorldConstants.BODIES.add(body);

                PolygonShape shape = new PolygonShape();
                shape.setAsBox(tileSize/2 / PPM, tileSize/2 / PPM);
                fixtureDef.shape = shape;

                //TODO create a better positioning
                if(col == 0) {
                    body.createFixture(fixtureDef).setUserData("leftDoor"); //
                } else if(col == sensorLayer.getWidth()-1) {
                    body.createFixture(fixtureDef).setUserData("rightDoor");; //naming the roomfixture room
                }

                shape.dispose();
            }
        }
    }

    public void setHeroPosition(String position) {
        for(Body body : WorldConstants.BODIES) {
            if(body.getUserData() != null && body.getUserData().equals("hero")) {
                if(position.equals("left")) {
                    System.out.println("Position: " + body.getPosition().x + body.getPosition().y);
                    body.setTransform(new Vector2(200/WorldConstants.PPM, 50/PPM),0);
                    System.out.println("Position: " + body.getPosition().x + body.getPosition().y);
                } else if(position.equals("right")) {
                    body.setTransform(new Vector2(100, 100), 0);
                }
            }
        }
    }

    public TiledMapRenderer getRenderer() {
        return renderer;
    }

    public void destroy() {
        for(Body body : BODIES) {
            if(body.getUserData() != null && body.getUserData().equals("room")) {
                WorldConstants.CURRENT_WORLD.destroyBody(body);
            }
        }
    }
}
