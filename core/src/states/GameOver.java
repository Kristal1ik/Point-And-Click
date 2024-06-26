package states;

import static com.gamingpotatoe.pointandclick.Globals.passedTubes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameOver extends State {

    Texture bgTexture;
    Texture gameOver;
    BitmapFont font;

    public GameOver(GameStateManager gsm) {
        super(gsm);

        camera.setToOrtho(false, (float) Gdx.graphics.getWidth() / 2, (float) Gdx.graphics.getHeight() / 2);
        bgTexture = new Texture("textures/start_bg.jpg");
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.characters = FreeTypeFontGenerator.DEFAULT_CHARS + "1234567890йцукенгшщзхъфывапролджэячсмитьбюЙЦУКЕНГШЩЗХЪФЫВАПРОЛДЖЭЯЧСМИТЬБЮёЁ";
        parameter.size = 40;
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/pixel.ttf"));
        font = generator.generateFont(parameter);
        generator.dispose();
    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()){
            gameStateManager.set(new PlayState(gameStateManager));
        }

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
        System.out.println(camera.position.x);
        font.draw(batch, "Game over", (int)(camera.position.x- camera.position.x / 4), (int)(camera.position.y * 1.5));
        font.draw(batch, Integer.toString(passedTubes), (int)(camera.position.x- camera.position.x / 4), camera.position.y);

        batch.end();

    }

    @Override
    public void dispose() {
        bgTexture.dispose();
        gameOver.dispose();
        font.dispose();

    }
}