package com.gamingpotatoe.pointandclick.screens;

import static com.gamingpotatoe.pointandclick.Globals.boardIsPickedUp;
import static com.gamingpotatoe.pointandclick.Globals.controlPanelIsPickedUp;
import static com.gamingpotatoe.pointandclick.Globals.safeIsPickedUp;
import static com.gamingpotatoe.pointandclick.Globals.tapeIsPickedUp;
import static com.gamingpotatoe.pointandclick.Globals.count;
import static com.gamingpotatoe.pointandclick.PointAndClick.readScene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.gamingpotatoe.pointandclick.PointAndClick;
import com.gamingpotatoe.pointandclick.minigames.FlappyRocket;
import com.gamingpotatoe.pointandclick.minigames.Password;
import com.gamingpotatoe.pointandclick.minigames.WiringGame;
import com.badlogic.gdx.audio.Sound;


import java.io.IOException;
import java.util.ArrayList;


public class FirstScene implements Screen {
    final PointAndClick game;
    OrthographicCamera camera;
    Texture bgTexture, rectTexture, tapeTexture, brokenBoardTexture, brokenControlPanelTexture, safeTexture;
    SpriteBatch batch;
    FitViewport viewport;
    ArrayList<String> data = new ArrayList<>();
    BitmapFont font = new BitmapFont();
    Sprite tapeSprite, brokenBoardSprite, brokenControlPanelSprite, safeSprite;

    private final Stage stage;

    Skin skin;
    String string = "";
    boolean tryPick, tryPick2;
    Sound repairSound;

    public FirstScene(final PointAndClick game, int n) throws IOException {
        this.game = game;
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        count = n;
        if (count > 3) {
            bgTexture = new Texture("textures/first_scene.jpg");
        } else {
            bgTexture = new Texture("textures/start_bg.jpg");
        }
        rectTexture = new Texture("textures/rectangle.jpg");
        tapeTexture = new Texture("textures/tape.jpg");
        brokenBoardTexture = new Texture("textures/onboard_comp_bw.jpg");
        brokenControlPanelTexture = new Texture("textures/control_panel_bw.png");
        safeTexture = new Texture("textures/safe.jpg");

        repairSound = Gdx.audio.newSound(Gdx.files.internal("sounds/repair_sound.mp3"));

        batch = new SpriteBatch();
        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
        data = readScene(1);


        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.characters = FreeTypeFontGenerator.DEFAULT_CHARS + "йцукенгшщзхъфывапролджэячсмитьбюЙЦУКЕНГШЩЗХЪФЫВАПРОЛДЖЭЯЧСМИТЬБЮёЁ";
        parameter.size = 40;
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/pixel.ttf"));
        font = generator.generateFont(parameter);
        generator.dispose();

        tapeSprite = new Sprite(tapeTexture);
        tapeSprite.setSize((float) Gdx.graphics.getHeight() / 27, (float) Gdx.graphics.getHeight() / 27);
        tapeSprite.setPosition(((float) (Gdx.graphics.getWidth() * 0.65)),
                (float) (Gdx.graphics.getHeight() * 0.2));

        brokenBoardSprite = new Sprite(brokenBoardTexture);
        brokenBoardSprite.setSize((float) (Gdx.graphics.getWidth() / 4.5), (float) (Gdx.graphics.getHeight() / 2.5));
        brokenBoardSprite.setPosition(((float) (Gdx.graphics.getWidth() * 0.16)),
                (float) (Gdx.graphics.getHeight() * 0.08));

        brokenControlPanelSprite = new Sprite(brokenControlPanelTexture);
        brokenControlPanelSprite.setSize((float) (Gdx.graphics.getWidth() * 0.09), (float) (Gdx.graphics.getHeight() * 0.3));
        brokenControlPanelSprite.setPosition(((float) (Gdx.graphics.getWidth() * 0.485)),
                (float) (Gdx.graphics.getHeight() * 0.2));

        safeSprite = new Sprite(safeTexture);
        safeSprite.setSize((float) (Gdx.graphics.getWidth() * 0.08), (float) (Gdx.graphics.getHeight() * 0.1));
        safeSprite.setPosition(((float) (Gdx.graphics.getWidth() * 0.3)),
                (float) (Gdx.graphics.getHeight() * 0.435));

        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

//        }
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        camera.update();
        ScreenUtils.clear(0, 0, 0, 1);
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        if (count == 4) {
            bgTexture = new Texture("textures/first_scene.jpg");
        }


        batch.draw(bgTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        if (count > 3) {
            if (!(tapeIsPickedUp)) {
                tapeSprite.draw(batch);
            }
            if (!(boardIsPickedUp)) {
                brokenBoardSprite.draw(batch);
            }
            if (!(controlPanelIsPickedUp)) {
                brokenControlPanelSprite.draw(batch);
            }
            if (!(safeIsPickedUp)) {
                safeSprite.draw(batch);
            }
        }
        batch.draw(rectTexture, 0, 0, Gdx.graphics.getWidth(), (int) (Gdx.graphics.getHeight() * 0.15));



        if (Gdx.input.justTouched()) {
            System.out.println(count);
//            if (count == 3) {
//                System.out.println("fgbhnjm");
//                bgTexture = new Texture("textures/first_scene.jpg");
//            }
            count += 1;
            int x1 = Gdx.input.getX();
            int y1 = Gdx.input.getY();
            Vector3 input = new Vector3(x1, y1, 0);
            camera.unproject(input);
            if (count > 10) {
                if ((tapeSprite.getBoundingRectangle().contains(input.x, input.y))) {
                    tapeIsPickedUp = true;
                    string = "Синяя изолента! Теперь я могу починить бортовой компьютер.";
                }
                if ((safeSprite.getBoundingRectangle().contains(input.x, input.y)) && !(safeIsPickedUp)) {
                    game.setScreen(new Password(game));

                }
                if ((brokenBoardSprite.getBoundingRectangle().contains(input.x, input.y))) {
                    if (tapeIsPickedUp && !(boardIsPickedUp)) {
                        game.setScreen(new WiringGame(game));
                        boardIsPickedUp = true;
                    }
                    if (!tapeIsPickedUp) {
                        tryPick = true;
                        string = "Кажется, мне нужна синяя изолента для этого действия.";
                    }
                }
                if ((brokenControlPanelSprite.getBoundingRectangle().contains(input.x, input.y))) {
                    if (!safeIsPickedUp) {
                        tryPick2 = true;
                        string = "Кажется, мне нужен рычаг из сейфа для этого действия.";
                    }
                    if (safeIsPickedUp){
                        controlPanelIsPickedUp = true;
                        repairSound.play(1.0f);
                        System.out.println("nnnn");
                    }
                }
            }

//            if (controlPanelIsPickedUp && boardIsPickedUp){
//                game.setScreen(new FlappyRocket(game));
//            }
        }
        if (safeIsPickedUp  &&!(controlPanelIsPickedUp)) {
            string = "Рычаг! Теперь я могу починить пульт управления.";
        }
        if (!(safeIsPickedUp) && tryPick2 && !(controlPanelIsPickedUp)){
            font.draw(batch, string, 20, 100);

        }
        if (tryPick && !(boardIsPickedUp) && !(tapeIsPickedUp)){
            font.draw(batch, string, 20, 100);
        }
        if (tapeIsPickedUp && !(boardIsPickedUp)) {
            font.draw(batch, string, 20, 100);
        }
        if (safeIsPickedUp && !(controlPanelIsPickedUp)) {
            font.draw(batch, string, 20, 100);
        }
        if (tryPick2 && !(safeIsPickedUp) && !(controlPanelIsPickedUp)){
            font.draw(batch, string, 20, 100);

        }
        if (count < 10) {
            System.out.println(data.get(count));
            font.draw(batch, data.get(count), 20, 100);
        }

        if (controlPanelIsPickedUp && boardIsPickedUp){
            font.draw(batch, "Все починил, теперь можно лететь домой.", 20, 100);
            if (Gdx.input.justTouched()){
                game.setScreen(new FlappyRocket(game));
            }
        }
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
        rectTexture.dispose();
        tapeTexture.dispose();
        brokenBoardTexture.dispose();
        bgTexture.dispose();
        safeTexture.dispose();
        brokenControlPanelTexture.dispose();
        stage.dispose();
        batch.dispose();
        font.dispose();
    }


}

