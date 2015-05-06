package com.jupiter.rogue.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.jupiter.rogue.Controller.AIController;
import com.jupiter.rogue.Controller.WorldController;
import com.jupiter.rogue.Model.Creatures.Hero;
import com.jupiter.rogue.Model.Creatures.RedDeath;
import com.jupiter.rogue.Model.Map.Map;
import static com.jupiter.rogue.Model.Map.WorldConstants.PPM;

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
    private HeroView heroView;
    Box2DDebugRenderer debugRenderer;
    WorldController wc;
    World world;


    public View() {
        wc = new WorldController();
        heroView = new HeroView();
        debugRenderer = new Box2DDebugRenderer();
        w = Gdx.graphics.getWidth();
        h = Gdx.graphics.getHeight();
        camera  = new OrthographicCamera(); //Regular camera for level
        b2dCam = new OrthographicCamera();  //Box2D camera to scale up the box2D simulation
        map = Map.getInstance();
        batch = new SpriteBatch();
    }

    public void update() {

        tiledMapRenderer = map.getCurrentRoom().getTiledMapRenderer();

        camera.setToOrtho(false, w, h);
        moveCamera();

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();

        heroView.updateAnimation(Gdx.graphics.getDeltaTime(), camera.combined);

        /*batch.setProjectionMatrix(camera.combined);
        batch.begin();
        //sprite.draw(batch);
        batch.draw(Hero.getInstance().getCurrentFrame().getTexture(), Hero.getInstance().getX(), Hero.getInstance().getY());
        batch.end();*/

        camera.setToOrtho(false, w / PPM, h / PPM);

        moveB2DCamera();

        debugRenderer.render(wc.getWorld(), camera.combined);

        AIController.redDeath1.render(camera.combined);
       AIController.redDeath2.render(camera.combined);


    }

    private void moveCamera(){
        camera.position.set(Hero.getInstance().getX()*PPM, Hero.getInstance().getY()*PPM, 0);
        camera.update();
    }

    private void moveB2DCamera() {
        camera.position.set(Hero.getInstance().getX(), Hero.getInstance().getY(), 0);
        camera.update();
    }
}