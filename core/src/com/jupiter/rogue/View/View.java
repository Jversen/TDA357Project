package com.jupiter.rogue.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.jupiter.rogue.Controller.Controller;
import com.jupiter.rogue.Controller.WorldController;
import com.jupiter.rogue.Model.Creatures.Hero;
import com.jupiter.rogue.Model.Creatures.RedDeath;
import com.jupiter.rogue.Model.Map.Map;
import com.jupiter.rogue.Utils.WorldConstants;

import static com.jupiter.rogue.Utils.WorldConstants.PPM;

/**
 * Created by oskar on 17/04/2015.
 */
@lombok.Data
public class View {

    private Texture img;
    private TiledMap tiledMap;
    private OrthographicCamera camera;
    private OrthographicCamera b2dCam;
    private TiledMapRenderer tiledMapRenderer;
    private float w;
    private float h;
    private Map map;
    private SpriteBatch batch;

    private Controller controller;

    private HeroView heroView;

    Box2DDebugRenderer debugRenderer;

    public View() {

        controller = Controller.getInstance();
        heroView = controller.getHeroController().getHeroView();

        debugRenderer = new Box2DDebugRenderer();
        w = Gdx.graphics.getWidth()*2;
        h = Gdx.graphics.getHeight()*2;
        camera  = new OrthographicCamera(); //Regular camera for level
        b2dCam = new OrthographicCamera();  //Box2D camera to scale up the box2D simulation
        map = Map.getInstance();
        batch = new SpriteBatch();
    }

    public void update() {

        tiledMapRenderer = map.getCurrentRoom().getTiledHandler().getRenderer();
        float posX = getCamPosX();
        float posY = getCamPosY();

        camera.setToOrtho(false, w, h);
        moveCamera(posX, posY);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();

        heroView.updateAnimation(Gdx.graphics.getDeltaTime(), camera.combined);

            for (int i = 0; i < map.getCurrentRoom().getEnemyControllers().size(); i++) {
                map.getCurrentRoom().getEnemyControllers().get(i).getEnemyView().
                        updateAnimation(Gdx.graphics.getDeltaTime(), camera.combined);
            }
        /*batch.setProjectionMatrix(camera.combined);
        batch.begin();
        //sprite.draw(batch);
        batch.draw(Hero.getInstance().getCurrentFrame().getTexture(), Hero.getInstance().getX(), Hero.getInstance().getY());
        batch.end();*/

        camera.setToOrtho(false, w / PPM, h / PPM);

        moveB2DCamera(posX, posY);

        debugRenderer.render(WorldConstants.CURRENT_WORLD, camera.combined);
    }

    private void moveCamera(float x, float y){
        camera.position.set(x, y, 0);
        camera.update();
    }

    private void moveB2DCamera(float x, float y) {
        camera.position.set(x/PPM, y/PPM, 0);
        camera.update();
    }

    public float getCamPosX() {
        float x = Hero.getInstance().getX() * PPM;
        if(x < 192) {
            x = 192;
        } else if(x>WorldConstants.WIDTH-192) {
            x = WorldConstants.WIDTH-192;
        }
        return x;
    }

    public float getCamPosY() {
        float y = Hero.getInstance().getY() * PPM;
        if(y < 90) {
            y = 90;
        }
        return y;
    }
}