package com.jupiter.rogue.Utils;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import java.util.Arrays;

import static com.jupiter.rogue.Utils.WorldConstants.BODIES;
import static com.jupiter.rogue.Utils.WorldConstants.PPM;

/**
 * Created by Johan on 06/05/15.
 */
public class TiledHandler {
    private TiledMapRenderer renderer;
    private TiledMap tiledMap;
    private int tileSize;
    private TiledMapTileLayer foregroundLayer;
    private TiledMapTileLayer sensorLayer;

    public TiledHandler(String path) {
        tiledMap = new TmxMapLoader().load(path);
        renderer = new OrthogonalTiledMapRenderer(tiledMap);

        foregroundLayer = (TiledMapTileLayer)tiledMap.getLayers().get(1);
        sensorLayer = (TiledMapTileLayer)tiledMap.getLayers().get(2);

        tileSize = (int) foregroundLayer.getTileWidth();

        WorldConstants.WIDTH = foregroundLayer.getWidth() * tileSize;
        WorldConstants.HEIGHT = foregroundLayer.getHeight() * tileSize;
    }

    public void initRoom() {
        createTileBodies();
    }

     /* Create static bodies for every tile in layer 'layer'. Update later to support
    different kinds of layers. */
    public void createTileBodies() {
        BodyDef bodyDef = new BodyDef();
        float obstacleLength = 0;

        for (int row = 0; row < foregroundLayer.getHeight(); row++){
            for (int col = 0; col < foregroundLayer.getWidth(); col++){
                TiledMapTileLayer.Cell cell = foregroundLayer.getCell(col, row);

                if (cell != null && cell.getTile() != null) {
                    obstacleLength++;
                    if (col >= foregroundLayer.getWidth() - 1 && obstacleLength > 0){
                        createObsFixture(row, col, obstacleLength);
                        obstacleLength = 0;
                        continue;
                    }
                } else if (obstacleLength > 0){
                    createObsFixture(row, col - 1, obstacleLength);
                    obstacleLength = 0;
                }

            }
        }

        //TODO reuse code from above
        for (int row = 0; row < sensorLayer.getHeight(); row++){
            for (int col = 0; col < sensorLayer.getWidth(); col++){

                TiledMapTileLayer.Cell cell = sensorLayer.getCell(col, row);

                if (cell == null || cell.getTile() == null){
                    continue;
                }


                bodyDef.type = BodyDef.BodyType.StaticBody;

                /*Set the position to the tile number plus half the tilesize to compensate
                for body/libgdx drawing differences. */
                bodyDef.position.set((col + 0.5f) * tileSize / PPM,
                        (row + 0.5f) * tileSize / PPM);

                FixtureDef fixtureDef = new FixtureDef();
                fixtureDef.isSensor = true;


                Body body = WorldConstants.CURRENT_WORLD.createBody(bodyDef);

                WorldConstants.BODIES.add(body);
                body.setUserData("sensor");

                PolygonShape shape = new PolygonShape();
                shape.setAsBox(tileSize/2 / PPM, tileSize/2 / PPM);
                fixtureDef.shape = shape;

                //TODO create a better positioning
                String side = "n";
                int cellNr = -1;

                if(col == 0) {
                    side = "l";

                    //body.createFixture(fixtureDef).setUserData("leftDoor"); //
                } else if(col == sensorLayer.getWidth()-1) {
                    side = "r";
                    //body.createFixture(fixtureDef).setUserData("rightDoor");; //naming the roomfixture room
                }

                if(row == 0) {
                    side = "b";
                } else if(row == sensorLayer.getHeight()-1) {
                    System.out.println("t: " + (sensorLayer.getHeight()-1));
                    side = "t";
                }

                if(side.equals("r") || side.equals("l")) {
                    cellNr = (row-2)/5 + 1;
                }

                if(side.equals("t") || side.equals("b")) {
                    cellNr = (col-2)/5 + 1;
                }

                System.out.println(side + cellNr);
                body.createFixture(fixtureDef).setUserData(side + cellNr);

                shape.dispose();
            }
        }
    }

    /* Creates fixtures on obstacles in horizontal chunks*/
    private void createObsFixture(int row, int col, float obstacleLength){
        BodyDef bodyDef = new BodyDef();

        float x = ((col - obstacleLength) + obstacleLength / 2 + 1f) * tileSize / PPM;
        float y = (row + 0.5f) * tileSize / PPM;
        bodyDef.type = BodyDef.BodyType.StaticBody;

                /*Set the position to the tile number plus half the tilesize to compensate
                for body/libgdx drawing differences. */

        bodyDef.position.set(x, y);
        FixtureDef fixtureDef = new FixtureDef();

        Body body = WorldConstants.CURRENT_WORLD.createBody(bodyDef);
        body.setUserData("room");
        WorldConstants.BODIES.add(body);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox((obstacleLength * tileSize)/ 2 / PPM, tileSize/2 / PPM);
        fixtureDef.shape = shape;

        body.createFixture(fixtureDef).setUserData("obstacle");

        shape.dispose();


    }

    //TODO expand this to fit more positions
    public void setHeroPosition(String entrance) {

        String side = entrance.substring(0,1);
        int cell = Integer.parseInt(entrance.substring(1));

        for(Body body : WorldConstants.BODIES) {
            if(body.getUserData() != null && body.getUserData().equals("hero")) {
                float x = -1;
                float y = -1;
                float PPM = WorldConstants.PPM;
                System.out.println("Cell: " + cell);
                if(side.equals("l")) {
                    x = 50/PPM;
                    y = ((cell)*32+15)/PPM;
                } else if(side.equals("r")) {
                    x = (((foregroundLayer.getWidth()-1)*32)-15)/WorldConstants.PPM;
                    y = ((cell)*32+15)/PPM;
                } else if(side.equals("t")) {
                    x = ((cell+3)*32+15)/PPM;
                    y = (((foregroundLayer.getHeight()-2)*32))/WorldConstants.PPM;
                } else if(side.equals("b")) {
                    x = ((cell+3)*32+15)/PPM;
                    y = 82/PPM;
                }

                body.setTransform(new Vector2(x, y),0);
                break;
            }
        }
    }

    public TiledMapRenderer getRenderer() {
        return renderer;
    }

    public void destroy() {
        for(Body body : BODIES) {
            if(body.getUserData() != null && (body.getUserData().equals("room") || body.getUserData().equals("sensor"))) {
                WorldConstants.CURRENT_WORLD.destroyBody(body);
            }
        }
    }
}
