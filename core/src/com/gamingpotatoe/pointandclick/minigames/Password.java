package com.gamingpotatoe.pointandclick.minigames;

import static com.gamingpotatoe.pointandclick.Globals.controlPanelIsPickedUp;
import static com.gamingpotatoe.pointandclick.Globals.count;
import static com.gamingpotatoe.pointandclick.Globals.safeIsPickedUp;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.gamingpotatoe.pointandclick.PointAndClick;
import com.gamingpotatoe.pointandclick.screens.FirstScene;

import java.io.IOException;
import java.util.Objects;

public class Password implements Screen {
    final PointAndClick game;
    FitViewport viewport;
    OrthographicCamera camera;
    SpriteBatch batch;
    Texture bgTexture, rightTexture, leftTexture, oneTexture, twoTexture, threeTexture, ruleTexture;
    Sprite leftSprite, rightSprite;
    int correct, current;
    int[] password = new int[3];
    Stage stage;
    BitmapFont font;
    Sound ordinarySound, correctSound;
    String nowDirection, userDirection;

    public Password(PointAndClick game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        ordinarySound = Gdx.audio.newSound(Gdx.files.internal("sounds/ordinary_sound.mp3"));
        correctSound = Gdx.audio.newSound(Gdx.files.internal("sounds/correct_sound.mp3"));
        batch = new SpriteBatch();
        bgTexture = new Texture("textures/password_1.png");
        rightTexture = new Texture("textures/right.png");
        leftTexture = new Texture("textures/left.png");
        oneTexture = new Texture("textures/password_2.png");
        twoTexture = new Texture("textures/password_3.png");
        threeTexture = new Texture("textures/password_4.png");
        ruleTexture = new Texture("textures/rule2.png");


        rightSprite = new Sprite(rightTexture);
        rightSprite.setPosition((float) Gdx.graphics.getWidth() / 3, (float) (Gdx.graphics.getHeight() / 2.5));

        leftSprite = new Sprite(leftTexture);
        leftSprite.setPosition((float) Gdx.graphics.getWidth() / 5, (float) (Gdx.graphics.getHeight() / 2.5));

        current = 1;
        password[0] = 15;
        password[1] = 3;
        password[2] = 7;

        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.characters = FreeTypeFontGenerator.DEFAULT_CHARS + "1234567890йцукенгшщзхъфывапролджэячсмитьбюЙЦУКЕНГШЩЗХЪФЫВАПРОЛДЖЭЯЧСМИТЬБЮёЁ";
        parameter.size = 70;
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/pixel.ttf"));
        font = generator.generateFont(parameter);
        generator.dispose();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        if (correct == 0) {
            batch.draw(bgTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        } else if (correct == 1) {
            batch.draw(oneTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        } else if (correct == 2) {
            batch.draw(twoTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        } else if (correct == 3) {
            batch.draw(threeTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }
        rightSprite.draw(batch);
        leftSprite.draw(batch);
        if (correct < 3) {
            if (correct % 2 == 0) {
                nowDirection = "right";
            } else {
                nowDirection = "left";
            }
            if (Gdx.input.justTouched()) {
                int x1 = Gdx.input.getX();
                int y1 = Gdx.input.getY();
                Vector3 input = new Vector3(x1, y1, 0);
                camera.unproject(input);
                if ((leftSprite.getBoundingRectangle().contains(input.x, input.y))) {
                    ordinarySound.play(1.0f);

                    if (current - 1 < 1) {
                        current = 60;
                    } else {
                        current -= 1;
                    }
                    userDirection = "left";
                } else if ((rightSprite.getBoundingRectangle().contains(input.x, input.y))) {
                    ordinarySound.play(1.0f);

                    if (current + 1 >= 60) {
                        current = 1;
                    } else {
                        current += 1;
                    }
                    userDirection = "right";
                }
                System.out.println(userDirection + " " + nowDirection);
                if (current == password[correct]) {
                    System.out.println(correct);
                    if (Objects.equals(userDirection, nowDirection)) {
                        correct += 1;
                        correctSound.play(1.0f);
                    } else {
                        System.out.println("lll");
                        correct = 0;
                    }
                }

                if (correct > 0) {
                    if (!(Objects.equals(userDirection, nowDirection))) {
                        correct = 0;
                    }
                }
            }
        }
        else {
            try {
                game.setScreen(new FirstScene(game, 11));
                safeIsPickedUp = true;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        font.draw(batch, Integer.toString(current), (float) (Gdx.graphics.getWidth() * 0.26), (float) (Gdx.graphics.getHeight() / 2));
        batch.draw(ruleTexture, ((float) Gdx.graphics.getWidth() / 2 - (float) ruleTexture.getWidth() /2), Gdx.graphics.getHeight()-ruleTexture.getHeight());
        batch.end();
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
        leftTexture.dispose();
        rightTexture.dispose();
        oneTexture.dispose();
        twoTexture.dispose();
        threeTexture.dispose();
        ordinarySound.dispose();
        correctSound.dispose();
    }
}
