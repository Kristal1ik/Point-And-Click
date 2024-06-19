package states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.gamingpotatoe.pointandclick.minigames.FlappyRocket;

import java.awt.Font;
import java.util.ArrayList;
import java.util.Arrays;

public class MenuState extends State {
    Texture bgTexture, playButtonTexture;
    TextButton playButton;
    Skin skin;
    Stage stage;
    BitmapFont font = new BitmapFont();

    public MenuState(GameStateManager gameStateManager) {
        super(gameStateManager);
        camera.setToOrtho(false, (float) Gdx.graphics.getWidth() / 2, (float) Gdx.graphics.getHeight() / 2);
        bgTexture = new Texture("textures/start_bg.jpg");
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);
        playButton = new TextButton("Start!", skin);
        playButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

            }
        });

        table.add(playButton).expand().center().width(200).height(100);

        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.characters = FreeTypeFontGenerator.DEFAULT_CHARS + "йцукенгшщзхъфывапролджэячсмитьбюЙЦУКЕНГШЩЗХЪФЫВАПРОЛДЖЭЯЧСМИТЬБЮёЁ";
        parameter.size = 10;
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/pixel.ttf"));
        font = generator.generateFont(parameter);
        generator.dispose();

    }

    @Override
    void handleInput() {
        if (Gdx.input.justTouched()) {
            gameStateManager.set(new PlayState(gameStateManager));
        }
    }

    @Override
    void update(float delta) {
        handleInput();
    }

    @Override
    void render(SpriteBatch batch) {
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(bgTexture, 0, 0);
        font.draw(batch, "Ты починил космический корабль.\nДля того, чтобы добраться до дома, тебе нужно преодолеть 10 препятствий.", (int)(Gdx.graphics.getWidth() * 0.01), (int)(Gdx.graphics.getHeight() * 0.1));

        batch.end();
        stage.act();
        stage.draw();

    }

    @Override
    void dispose() {
        bgTexture.dispose();
        playButtonTexture.dispose();
    }
}
