package com.jupiter.rogue.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.jupiter.rogue.Utils.ObservableScreen;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;


/**
 * Created by Johan on 30/05/15.
 */
public class MainMenuScreen implements ObservableScreen {
    private Skin skin;
    private Stage stage;
    private SpriteBatch batch;
    private Table table;
    private TextButton playButton;
    private TextButton exitButton;
    private PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    public MainMenuScreen() {
        create();
    }

    private void create() {
        batch = new SpriteBatch();
        stage = new Stage();
        skin = new Skin();
        table = new Table();

        Pixmap pixmap = new Pixmap(100, 100, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.RED);
        pixmap.fill();

        skin.add("button", new Texture(pixmap));

        BitmapFont bfont=new BitmapFont();
        bfont.scale((float) 0.1);
        skin.add("default",bfont);

        // Configure a TextButtonStyle and name it "default". Skin resources are stored by type, so this doesn't overwrite the font.
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("button", Color.DARK_GRAY);
        textButtonStyle.down = skin.newDrawable("button", Color.DARK_GRAY);
        textButtonStyle.checked = skin.newDrawable("button", Color.MAROON);
        textButtonStyle.over = skin.newDrawable("button", Color.LIGHT_GRAY);

        textButtonStyle.font = skin.getFont("default");

        skin.add("default", textButtonStyle);

        playButton = new TextButton("Play rogue",textButtonStyle);
        exitButton = new TextButton("Exit game", textButtonStyle);

        stage.addActor(playButton);
        stage.addActor(exitButton);

        playButton.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                playButton.setText("Loading..");
                pcs.firePropertyChange("setGameScreen", 0, 1);
            }
        });

        exitButton.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                pcs.firePropertyChange("exitGame", 0, 1);
            }
        });
    }


    @Override
    public void show() {
        table.add(playButton).size(80,30).padBottom(10).row();
        table.add(exitButton).size(80,30).padBottom(10).row();
        table.setFillParent(true);
        stage.addActor(table);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        //TODO fix
        float buttonWidth = Gdx.graphics.getWidth()/5;
        float buttonHeight = Gdx.graphics.getHeight()/5;
        //playButton.setSize(buttonWidth, buttonHeight);
        //exitButton.setSize(buttonWidth, buttonHeight);
        //playButton.setPosition(Gdx.graphics.getWidth()/2-buttonWidth/2, Gdx.graphics.getHeight()/2);
        //exitButton.setPosition();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }

    @Override
    public void addObserver(PropertyChangeListener observer) {
        this.pcs.addPropertyChangeListener(observer);
    }

    @Override
    public void removeObserver(PropertyChangeListener observer) {
        this.pcs.addPropertyChangeListener(observer);
    }
}
