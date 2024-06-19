package states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public abstract class State {
    OrthographicCamera camera;
    Vector2 mouse;
    GameStateManager gameStateManager;

    public State(GameStateManager gameStateManager) {
        this.gameStateManager = gameStateManager;
        camera = new OrthographicCamera();
        mouse = new Vector2();
    }
    abstract void handleInput();
    abstract void update(float delta);
    abstract void render(SpriteBatch batch);
    abstract void dispose();
}
