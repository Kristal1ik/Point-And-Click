package states;

import static com.gamingpotatoe.pointandclick.Globals.TUBE_COUNT;
import static com.gamingpotatoe.pointandclick.Globals.TUBE_SPACING;
import static com.gamingpotatoe.pointandclick.Globals.TUBE_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gamingpotatoe.pointandclick.essences.Rocket;
import com.gamingpotatoe.pointandclick.essences.Tube;

import java.util.ArrayList;

public class PlayState extends State{
    Rocket rocket;
    Texture bgTexture;
    ArrayList<Tube> tubes;
    public PlayState(GameStateManager gameStateManager) {
        super(gameStateManager);
        camera.setToOrtho(false, (float) Gdx.graphics.getWidth() / 2, (float) Gdx.graphics.getHeight() /2 );
        rocket = new Rocket(50, 300);
        bgTexture = new Texture("textures/start_bg.jpg");
        tubes = new ArrayList<>();

        for (int i = 0; i < TUBE_COUNT; i++){
            tubes.add(new Tube(i * (TUBE_SPACING + TUBE_WIDTH)));
        }
    }

    @Override
    void handleInput() {
        if (Gdx.input.justTouched()){
            rocket.fly();

        }    }

    @Override
    void update(float delta) {
        handleInput();
        rocket.update(delta);
        camera.position.x = rocket.getPosition().x + 80;

        for (int i = 0; i < tubes.size(); i++){

            Tube tube = tubes.get(i);
            if (camera.position.x - (camera.viewportWidth / 2) > tube.getPosTopTube().x + tube.getTopTube().getWidth()){
                tube.reposition(tube.getPosTopTube().x + ((TUBE_WIDTH + TUBE_SPACING) * TUBE_COUNT));
            }
            if (tube.collide(rocket.getBounds())){
                gameStateManager.set(new GameOver(gameStateManager));}
        }
        camera.update();

    }

    @Override
    void render(SpriteBatch batch) {
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(bgTexture, camera.position.x - (camera.viewportWidth / 2), 0);
        batch.draw(rocket.getRocket(), rocket.getPosition().x, rocket.getPosition().y);
        for (Tube tube : tubes) {
            batch.draw(tube.getTopTube(), tube.getPosBotTube().x, tube.getPosTopTube().y);
            batch.draw(tube.getBottomTube(), tube.getPosBotTube().x, tube.getPosBotTube().y);
        }
        batch.end();
    }

    @Override
    public void dispose() {
        bgTexture.dispose();
        rocket.dispose();
        for (Tube tube : tubes)
            tube.dispose();

    }
}
