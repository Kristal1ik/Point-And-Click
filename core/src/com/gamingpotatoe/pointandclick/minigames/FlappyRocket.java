package com.gamingpotatoe.pointandclick.minigames;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.gamingpotatoe.pointandclick.PointAndClick;

import states.GameStateManager;
import states.MenuState;

public class FlappyRocket implements Screen {
    final PointAndClick game;
    SpriteBatch batch;
    GameStateManager gameStateManager;

    public FlappyRocket(PointAndClick game) {
        this.game = game;
        batch = new SpriteBatch();
        gameStateManager = new GameStateManager();
        Gdx.gl.glClearColor(1, 0,0,1);
        gameStateManager.push(new MenuState(gameStateManager));
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        gameStateManager.update(Gdx.graphics.getDeltaTime());
        gameStateManager.render(batch);
    }

    @Override
    public void resize(int width, int height) {

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

    }
}
