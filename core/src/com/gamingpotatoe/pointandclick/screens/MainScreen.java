package com.gamingpotatoe.pointandclick.screens;

import static com.gamingpotatoe.pointandclick.Globals.SCR_WIDTH;
import static com.gamingpotatoe.pointandclick.Globals.SCR_HEIGHT;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.gamingpotatoe.pointandclick.PointAndClick;

import java.io.IOException;

public class MainScreen implements Screen {
    final PointAndClick game;
    OrthographicCamera camera;
    Texture bgTexture;
    SpriteBatch batch;
    FitViewport viewport;
    Stage stage;


    public MainScreen(final PointAndClick game) {
        this.game = game;
        bgTexture = new Texture("textures/start_bg.jpg");
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);
        Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        TextButton playButton = new TextButton("PLAY", skin);
        playButton.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                try {
                    game.setScreen(new FirstScene(game, 0));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        table.add(playButton).expand().center().width(200).height(100);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        batch.setProjectionMatrix(camera.combined);
        camera.update();
        batch.begin();
        if (Gdx.input.justTouched()){
            try {
                game.setScreen(new FirstScene(game, 0));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
        batch.draw(bgTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();
        stage.act();
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
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
        bgTexture.dispose();
        batch.dispose();
    }
}
