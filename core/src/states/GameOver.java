package states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameOver extends State {

    Texture bgTexture;
    Texture gameOver;

    public GameOver(GameStateManager gsm) {
        super(gsm);
        camera.setToOrtho(false, (float) Gdx.graphics.getWidth() / 2, (float) Gdx.graphics.getHeight() / 2);
        bgTexture = new Texture("textures/start_bg.jpg");
        gameOver = new Texture("gameover.png");
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
        batch.draw(bgTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(gameOver, camera.position.x - (float) gameOver.getWidth() / 2, camera.position.y);
        batch.end();

    }

    @Override
    public void dispose() {
        bgTexture.dispose();
        gameOver.dispose();


    }
}