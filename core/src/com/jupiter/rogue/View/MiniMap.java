package com.jupiter.rogue.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.jupiter.rogue.Model.Creatures.Hero;
import com.jupiter.rogue.Model.Map.Map;
import com.jupiter.rogue.Model.Map.Room;

/**
 * Created by Johan on 27/05/15.
 */
public class MiniMap extends Actor{
    private static MiniMap instance = null;

    private Texture activeRoom;
    private Texture activeCell;
    private Texture bossRoom;
    private Texture activeBossRoom;
    private Texture inactiveRoom;
    private Texture emptySpace;

    Texture[][] mapSection;

    private MiniMap() {
        initMiniMap();
    }

    public static MiniMap getInstance() {
        if(instance == null) {
            instance = new MiniMap();
        }
        return instance;
    }

    private void initMiniMap() {
        this.activeRoom = new Texture(Gdx.files.internal("Data/hud/MiniMap/activeroom.png"));
        this.activeCell = new Texture(Gdx.files.internal("Data/hud/MiniMap/activecell.png"));
        this.bossRoom = new Texture(Gdx.files.internal("Data/hud/MiniMap/bossroom.png"));
        this.activeBossRoom = new Texture(Gdx.files.internal("Data/hud/MiniMap/activebossroom.png"));
        this.inactiveRoom = new Texture(Gdx.files.internal("Data/hud/MiniMap/inactiveroom.png"));
        this.emptySpace = new Texture(Gdx.files.internal("Data/hud/MiniMap/emptyspace.png"));
        this.mapSection = new Texture[41][21];
    }

    @Override
    public void draw(Batch batch, float alpha) {
        updateMiniMap();
        for(int y = 0; y < 21; y++) {
            for(int x = 0; x < 41; x++) {
                batch.draw(mapSection[x][y], 140 + x, 150 + y);
            }
        }
    }

    public void updateMiniMap() {
        int[][] map = Map.getInstance().getRoomMap();
        int roomX = Map.getInstance().getCurrentRoomX();
        int roomY = Map.getInstance().getCurrentRoomY();
        float heroRoomX = Hero.getInstance().getX();
        float heroRoomY = Hero.getInstance().getY();

        int heroCellX = getHeroCellX(heroRoomX);
        int heroCellY = getHeroCellY(heroRoomY);

        int centerX = roomX+heroCellX-1;
        int centerY = roomY+heroCellY-1;

        int mX;
        int mY = 0;

        for(int y = centerY-10; y <= centerY+10; y++) {
            mX = 0;
            for(int x = centerX-20; x <= centerX+20; x++) {
                if(isWithinLimits(x, y) && map[x][y] != -1) {
                    int roomNumber = map[x][y];
                    if(isActiveRoom(x, y, map) && !isBossRoom(roomNumber)) {
                        if(isActiveCell(x, y, roomX, roomY, heroCellX, heroCellY)) {
                            mapSection[mX][mY] = activeCell;
                        } else {
                            mapSection[mX][mY] = activeRoom;
                        }
                    } else if(isBossRoom(roomNumber)) {
                        if(isActiveCell(x, y, roomX, roomY, heroCellX, heroCellY)) {
                            mapSection[mX][mY] = activeBossRoom;
                        } else {
                            mapSection[mX][mY] = bossRoom;
                        }
                    } else if(isVisited(roomNumber)){
                        mapSection[mX][mY] = inactiveRoom;
                    } else {
                        mapSection[mX][mY] = emptySpace;
                    }

                } else {
                    mapSection[mX][mY] = emptySpace;
                }
                mX++;
            }
            mY++;
        }

    }

    private boolean isVisited(int roomNumber) {
        return Map.getInstance().getRooms().get(roomNumber).getVisited();
    }

    private boolean isBossRoom(int roomNumber) {
        return Map.getInstance().getRooms().get(roomNumber).getBossRoom();
    }

    private boolean isActiveCell(int x, int y, int roomX, int roomY, int heroCellX, int heroCellY) {
        return x == roomX+heroCellX-1 && y == roomY+heroCellY-1;
    }

    private boolean isActiveRoom(int x, int y, int[][] map) {
        return isWithinLimits(x,y) && Map.getInstance().getCurrentRoomNbr() == map[x][y];
    }

    private boolean isWithinLimits(int x, int y) {
        return x >= 0 && x < 100 && y >= 0 && y < 100;
    }

    private int getHeroCellX(float heroX) {
        return (int)(heroX-2)/5+1; // lol
    }

    private int getHeroCellY(float heroY) {
        return (int)(heroY-2)/5+1;
    }
}
