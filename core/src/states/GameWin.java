package states;

import static com.gamingpotatoe.pointandclick.Globals.passedTubes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.ScreenUtils;
import com.gamingpotatoe.pointandclick.PointAndClick;
import com.gamingpotatoe.pointandclick.minigames.FlappyRocket;

public class GameWin extends State {

    Texture bgTexture;
    Texture gameOver;
    BitmapFont font;
    final PointAndClick game;

    public GameWin(GameStateManager gsm) {
        super(gsm);
        this.game = new PointAndClick();

        camera.setToOrtho(false, (float) Gdx.graphics.getWidth() / 2, (float) Gdx.graphics.getHeight() / 2);
        bgTexture = new Texture("textures/start_bg.jpg");
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.characters = FreeTypeFontGenerator.DEFAULT_CHARS + "1234567890йцукенгшщзхъфывапролджэячсмитьбюЙЦУКЕНГШЩЗХЪФЫВАПРОЛДЖЭЯЧСМИТЬБЮёЁ";
        parameter.size = 20;
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/pixel.ttf"));
        font = generator.generateFont(parameter);
        generator.dispose();
    }

    @Override
    public void handleInput() {


    }

    @Override
    public void update(float dt) {
        handleInput();

    }

    @Override
    public void render(SpriteBatch batch) {
        batch.setProjectionMatrix(camera.combined);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        ScreenUtils.clear(0, 0, 0, 1);

        batch.draw(bgTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        font.draw(batch, "Поздравляю!\nВы прошли игру и вернулись домой.", (int)(camera.position.x- camera.position.x / 3), (int)(camera.position.y));
        font.draw(batch, Integer.toString(passedTubes), (int)(camera.position.x- camera.position.x / 4), (int)(camera.position.y * 1.5));
//        if (Gdx.input.justTouched()){
//            game.setScreen((Screen) new PointAndClick());
//        }
        batch.end();

    }

    @Override
    public void dispose() {
        bgTexture.dispose();
        gameOver.dispose();


    }
}