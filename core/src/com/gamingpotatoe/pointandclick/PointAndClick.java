package com.gamingpotatoe.pointandclick;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gamingpotatoe.pointandclick.minigames.FlappyRocket;
import com.gamingpotatoe.pointandclick.screens.MainScreen;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class PointAndClick extends Game {
    SpriteBatch batch;
    MainScreen mainScreen;
    FlappyRocket flappyRocket;
    public ShapeRenderer shape;
    public Stage stage;
    Skin skin;
    Dialog dialog;
    public Viewport viewport;
    @Override
    public void create() {

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        dialog = new Dialog("Oups", skin){
            @Override
            protected void result(Object object) {
                if ((Boolean) object) {
                    System.out.println("Pressed");
                    System.exit(-1);
                }
            }
        };
        dialog.text("Unable to find some files!");
        dialog.button("Exit", true);

        batch = new SpriteBatch();
        mainScreen = new MainScreen(this);
        flappyRocket = new FlappyRocket(this);
        shape = new ShapeRenderer();
        setScreen(flappyRocket);

    }

    @Override
    public void render() {
        stage.act();
        stage.draw();
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    public static ArrayList<String> readScene(int sceneNumber) throws IOException {
        ArrayList<String> data = new ArrayList<>();
        if(Gdx.app.getType() == Application.ApplicationType.Desktop) {
            @SuppressWarnings({"DefaultLocale", "NewApi"}) BufferedReader br = new BufferedReader(new FileReader(new File(Gdx.files.getLocalStoragePath() + "assets\\" + Gdx.files.internal(String.format("texts/%s/%d_scene.txt",Globals.lang, sceneNumber)).path()), StandardCharsets.UTF_8));
            String st = "";
            while((st = br.readLine()) != null){
                data.add(st);
            }
            br.close();
        }
        else{
        @SuppressWarnings("DefaultLocale") InputStream input = Gdx.files.internal(String.format("texts/%s/%d_scene.txt",Globals.lang, sceneNumber)).read();
        System.out.println(input);
        Scanner s = new Scanner(input);


        while (s.hasNextLine()) {
            data.add(s.nextLine());
        }

        s.close();
    }

        return data;}
;}