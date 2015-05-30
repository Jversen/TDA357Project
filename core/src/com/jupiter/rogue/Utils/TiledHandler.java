package com.jupiter.rogue.Utils;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.jupiter.rogue.Controller.EnemyController;
import com.jupiter.rogue.Model.Creatures.Enemy;
import com.jupiter.rogue.Model.Factories.EnemyFactory;
import com.jupiter.rogue.Model.Factories.RedDeathFactory;
import com.jupiter.rogue.Model.Factories.WidowFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.jupiter.rogue.Utils.WorldConstants.*;

/**
 * Created by Johan on 06/05/15.
 */
@lombok.Data
public class TiledHandler {
    private TiledMapRenderer renderer;
    private TiledMap tiledMap;
    private TiledMapTileLayer foregroundLayer;
    private TiledMapTileLayer sensorLayer;
    private MapLayer enemySpawnLayer;
    private int[][] stairs;
    private float roomWidth;
    private float roomHeight;

    public TiledHandler(String path) {
        tiledMap = new TmxMapLoader().load(path);
        renderer = new OrthogonalTiledMapRenderer(tiledMap);

        foregroundLayer = (TiledMapTileLayer)tiledMap.getLayers().get(1);
        sensorLayer = (TiledMapTileLayer)tiledMap.getLayers().get(2);

        roomWidth = foregroundLayer.getWidth() * TILE_SIZE;
        roomHeight = foregroundLayer.getHeight() * TILE_SIZE;

        if (tiledMap.getLayers().getCount() >= 4){
            enemySpawnLayer = tiledMap.getLayers().get(3);
        }
    }

    public void initRoom() {
        initStairs();
        createTileBodies();
    }

     /* Create static bodies for every tile in layer 'layer'. Update later to support
    different kinds of layers. */
    public void createTileBodies() {
        BodyDef bodyDef = new BodyDef();
        int leftStair = 0;
        int rightStair = 0;

        for(int row = 2; row < foregroundLayer.getHeight()-2; row++) {
            for(int col = 2; col < foregroundLayer.getWidth()-2; col++) {
                TiledMapTileLayer.Cell cell = foregroundLayer.getCell(col, row);
                if(cell != null && cell.getTile() != null) {
                    if(leftStair(col, row)) {
                        stairs[col][row] = 1;
                        stairs[col][row-1] = 3;
                    }
                    if(rightStair(col, row)) {
                        stairs[col][row] = 2;
                        stairs[col][row-1] = 3;
                    }
                }
            }
        }

        // TODO remove:
        System.out.println("STAIRS:");
        for(int row = 2; row < foregroundLayer.getHeight()-2; row++) {
            System.out.println();
            for(int col = 2; col < foregroundLayer.getWidth()-2; col++) {
                System.out.print(" " + stairs[col][row]);
            }
        }

        float obstacleLength = 0;
        for (int row = 0; row < foregroundLayer.getHeight(); row++){
            for (int col = 0; col < foregroundLayer.getWidth(); col++){
                TiledMapTileLayer.Cell cell = foregroundLayer.getCell(col, row);

                if (cell != null && cell.getTile() != null && stairs [col][row] == 0) {
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

        createStairs();

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
                bodyDef.position.set((col + 0.5f) * TILE_SIZE / PPM,
                        (row + 0.5f) * TILE_SIZE / PPM);

                FixtureDef fixtureDef = new FixtureDef();
                fixtureDef.isSensor = true;


                Body body = WorldConstants.CURRENT_WORLD.createBody(bodyDef);

                WorldConstants.BODIES.add(body);
                body.setUserData("sensor");

                PolygonShape shape = new PolygonShape();
                shape.setAsBox(TILE_SIZE/2 / PPM, TILE_SIZE/2 / PPM);
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

    private void createStairs() {
        for(int row = 2; row < foregroundLayer.getHeight()-2; row++) {
            for(int col = 2; col < foregroundLayer.getWidth()-2; col++) {
                if(stairs[col][row] == 1) {
                    createLeftStair(col, row);
                } else if(stairs[col][row] == 2) {
                    createRightStair(col, row);
                }
            }
        }
    }

    private void createRightStair(int x, int y) {
        int startingX = x;
        int startingY = y;
        int stairLength = 0;
        boolean done = false;
        while(!done) {
            if(stairs[x][y] == 2) {
                stairLength++;
                stairs[x][y] = 0;
            } else {
                done = true;
            }
            x++;
            y++;
        }

        Vector2[] vertices = getRightStairVertices(stairLength);
        createStairObstacle(startingX, startingY, vertices, stairLength);
    }

    private Vector2[] getRightStairVertices(int stairLength) {
        Vector2[] vertices = new Vector2[3];
        vertices[0] = new Vector2(0, 0);
        vertices[1] = new Vector2(stairLength, 0);
        vertices[2] = new Vector2(stairLength, stairLength);
        return vertices;
    }

    private void createLeftStair(int x, int y) {
        int startingX = x;
        int startingY = y;
        int stairLength = 0;
        boolean done = false;
        while(!done) {
            if(stairs[x][y] == 1) {
                stairLength++;
                stairs[x][y] = 0;
            } else {
                done = true;
            }
            x--;
            y++;
        }
        Vector2[] vertices = getLeftStairVertices(stairLength);
        createStairObstacle(startingX-stairLength+1, startingY, vertices, stairLength);
    }
    private Vector2[] getLeftStairVertices(int stairLength) {
        Vector2[] vertices = new Vector2[3];
        vertices[0] = new Vector2(0, 0);
        vertices[1] = new Vector2(0, stairLength);
        vertices[2] = new Vector2(stairLength, 0);
        return vertices;
    }

    private boolean leftStair(int x, int y) {
        return foregroundLayer.getCell(x+1, y) == null && foregroundLayer.getCell(x+1, y+1) == null
                && foregroundLayer.getCell(x, y+1) == null && foregroundLayer.getCell(x,y-1) != null && foregroundLayer.getCell(x-1, y) != null;
    }

    private boolean rightStair(int x, int y) {
        return foregroundLayer.getCell(x-1, y) == null && foregroundLayer.getCell(x-1, y+1) == null
                && foregroundLayer.getCell(x, y+1) == null && foregroundLayer.getCell(x,y-1) != null &&  foregroundLayer.getCell(x+1, y) != null;
    }

    private void createStairObstacle(float x, float y, Vector2[] vertices, int stairLength) {
        BodyDef bodyDef = new BodyDef();

        bodyDef.type = BodyDef.BodyType.StaticBody;

                /*Set the position to the tile number plus half the tilesize to compensate
                for body/libgdx drawing differences. */

        bodyDef.position.set(x, y);
        FixtureDef fixtureDef = new FixtureDef();

        Body body = WorldConstants.CURRENT_WORLD.createBody(bodyDef);
        body.setUserData("room");
        WorldConstants.BODIES.add(body);

        PolygonShape shape = new PolygonShape();
        shape.set(vertices);
        fixtureDef.shape = shape;

        Fixture roomFixture = body.createFixture(fixtureDef);
        roomFixture.setFriction(0f);
        roomFixture.setUserData("obstacle");

        shape.dispose();
    }

    private void initStairs() {
        stairs = new int[foregroundLayer.getWidth()][foregroundLayer.getHeight()];
        for(int x = 0; x < foregroundLayer.getWidth(); x++) {
            for(int y = 0; y < foregroundLayer.getHeight(); y++) {
                stairs[x][y] = 0;
            }
        }
    }

    /* Creates fixtures on obstacles in horizontal chunks*/
    private void createObsFixture(int row, int col, float obstacleLength){
        BodyDef bodyDef = new BodyDef();

        float x = ((col - obstacleLength) + obstacleLength / 2 + 1f) * TILE_SIZE / PPM;
        float y = (row + 0.5f) * TILE_SIZE / PPM;
        bodyDef.type = BodyDef.BodyType.StaticBody;

                /*Set the position to the tile number plus half the tilesize to compensate
                for body/libgdx drawing differences. */

        bodyDef.position.set(x, y);
        FixtureDef fixtureDef = new FixtureDef();

        Body body = WorldConstants.CURRENT_WORLD.createBody(bodyDef);
        body.setUserData("room");
        WorldConstants.BODIES.add(body);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox((obstacleLength * TILE_SIZE)/ 2 / PPM, TILE_SIZE/2 / PPM);
        fixtureDef.shape = shape;

        Fixture roomFixture = body.createFixture(fixtureDef);
        roomFixture.setFriction(10f);
        roomFixture.setUserData("obstacle");

        shape.dispose();
    }

    //TODO expand this to fit more positions
    public void setHeroPosition(String entrance) {
        System.out.println("Entrance: " + entrance);

        String side = entrance.substring(0,1);
        int cell = Integer.parseInt(entrance.substring(1));

        for(Body body : WorldConstants.BODIES) {
            if(body.getUserData() != null && body.getUserData().equals("hero")) {
                float x = -1;
                float y = -1;
                System.out.println("Cell: " + cell);
                if(side.equals("l")) {
                    x = 50/PPM;
                    y = (((cell-1)*5)*TILE_SIZE+2*TILE_SIZE+17)/PPM;
                } else if(side.equals("r")) {
                    x = (((foregroundLayer.getWidth()-1)*TILE_SIZE)-15)/PPM;
                    y = (((cell-1)*5)*TILE_SIZE+2*TILE_SIZE+17)/PPM;
                } else if(side.equals("t")) {
                    x = (((cell-1)*5)*TILE_SIZE+2*TILE_SIZE+17)/PPM;
                    y = (((foregroundLayer.getHeight()-2)*TILE_SIZE))/PPM;
                } else if(side.equals("b")) {
                    x = (((cell-1)*5)*TILE_SIZE+15+2*TILE_SIZE+17)/PPM;
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
            if(body.getUserData() != null){
                if((body.getUserData().equals("room") || body.getUserData().equals("sensor") ||
                        body.getUserData().equals("enemy"))) {
                    //body.setUserData("dead");
                    WorldConstants.CURRENT_WORLD.destroyBody(body);
                }

            }
        }
    }
}
