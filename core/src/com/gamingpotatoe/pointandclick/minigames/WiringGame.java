package com.gamingpotatoe.pointandclick.minigames;


import static com.gamingpotatoe.pointandclick.Globals.boardIsPickedUp;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.gamingpotatoe.pointandclick.PointAndClick;
import com.gamingpotatoe.pointandclick.essences.Point;
import com.gamingpotatoe.pointandclick.screens.FirstScene;

import space.earlygrey.shapedrawer.ShapeDrawer;



import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;


public class WiringGame implements Screen {
    final PointAndClick game;
    FitViewport viewport;
    OrthographicCamera camera;
    SpriteBatch batch;
    ShapeDrawer drawer;
    ArrayList<int[]> redCords;
    ArrayList<int[]> blueCords;
    Texture bgTexture, redTexture, blueTexture;
    int x11, x12, y11, y12, x21, x22, y21, y22;
    boolean redStart, blueStart, redFinish, blueFinish, lasts = false;

    Sprite redSprite, blueSprite, redSpriteStart, blueSpriteStart, redSpriteFinish, blueSpriteFinish;
    int radius;
    Stage stage;
    Skin skin;
    Dialog errorDialog, corrDialog;
    TextButton playButton;

    public WiringGame(final PointAndClick game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);

        batch = new SpriteBatch();
        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
//        bgTexture = new Texture("textures/wiring_game_bg.jpg");
        bgTexture = new Texture("textures/wg_blur_bg.jpg");
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        TextureRegion region = new TextureRegion(bgTexture);
        drawer = new ShapeDrawer(batch, region);
        drawer.setDefaultLineWidth(5);

        redCords = new ArrayList<int[]>();
        blueCords = new ArrayList<int[]>();
        // red
        x11 = y11 = 20;
        x12 = y12 = 500;
        int[] cordsRed = new int[2];
        cordsRed[0] = x11;
        cordsRed[1] = y11;
        redCords.add(cordsRed);
        // blue
        x21 = y21 = 200;
        x22 = y22 = 700;
        int[] cordsBlue = new int[2];
        cordsBlue[0] = x21;
        cordsBlue[1] = y21;
        blueCords.add(cordsBlue);

        radius = (int) (Gdx.graphics.getHeight() * 0.05);
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        errorDialog = new Dialog("", skin) {
            @Override
            protected void result(Object object) {
                if ((Boolean) object) {
                    game.setScreen(new WiringGame(game));
                }
            }
        };
        errorDialog.text("A short circuit has been detected!");
        errorDialog.button("Try again", true);

        corrDialog = new Dialog("", skin) {
            @Override
            protected void result(Object object) {
                if ((Boolean) object) {
                    try {
                        game.setScreen(new FirstScene(game, 11));
                        boardIsPickedUp = true;
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        };
        corrDialog.text("Exactly!");
        corrDialog.button("Ok", true);

        playButton = new TextButton("Check!", skin);
        playButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if ((redFinish) && (blueFinish) && !(lasts)) {
                    addLast(redCords, blueCords);
                    for (int i = 0; i < redCords.size(); i++) {
                        System.out.println(Arrays.toString(redCords.get(i)));
                    }
                    for (int i = 0; i < blueCords.size(); i++) {
                        System.out.println(Arrays.toString(blueCords.get(i)));
                    }

                    for (int i = 1; i < redCords.size(); i++) {
                        ArrayList<int[]> red = new ArrayList<>();
                        red.add(redCords.get(i - 1));
                        red.add(redCords.get(i));
                        for (int j = 1; j < blueCords.size(); j++) {
                            ArrayList<int[]> blue = new ArrayList<>();
                            blue.add(blueCords.get(j - 1));
                            blue.add(blueCords.get(j));
                            if (intersect(red.get(0), red.get(1), blue.get(0), blue.get(1))){
                                System.out.println("intersection");
                                errorDialog.show(stage);
                            }
                            else{
                                corrDialog.show(stage);
                            }
                        }
                    }
                }
            }
        });

        table.add(playButton).expand().bottom().right().width(200).height(100);
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
        batch.draw(bgTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // textures
        redTexture = new Texture("textures/red.png");
        blueTexture = new Texture("textures/blue.png");

        // start sprites
        redSpriteStart = new Sprite(redTexture);
        redSpriteStart.setSize(radius, radius);
        redSpriteStart.setPosition(redCords.get(0)[0], redCords.get(0)[1]);
        redSpriteStart.draw(batch);
        blueSpriteStart = new Sprite(blueTexture);
        blueSpriteStart.setPosition(blueCords.get(0)[0], blueCords.get(0)[1]);
        blueSpriteStart.setSize(radius, radius);
        blueSpriteStart.draw(batch);

        // finish sprites
        redSpriteFinish = new Sprite(redTexture);
        redSpriteFinish.setSize(radius, radius);
        redSpriteFinish.setPosition(x12, y12);
        redSpriteFinish.draw(batch);
        blueSpriteFinish = new Sprite(blueTexture);
        blueSpriteFinish.setSize(radius, radius);
        blueSpriteFinish.setPosition(x22, y22);
        blueSpriteFinish.draw(batch);

        // ordinary sprites
        redSprite = new Sprite(redTexture);
        redSprite.setSize(radius, radius);
        blueSprite = new Sprite(blueTexture);
        blueSprite.setSize(radius, radius);


        for (int i = 1; i < redCords.size(); i++) {
            redSprite.setPosition(redCords.get(i)[0], redCords.get(i)[1]);
            redSprite.draw(batch);
            drawer.setColor(Color.RED);
            drawer.line(redCords.get(i - 1)[0] + ((float) radius / 2), redCords.get(i - 1)[1] + ((float) radius / 2), redCords.get(i)[0] + ((float) radius / 2), redCords.get(i)[1] + ((float) radius / 2));
        }

        for (int i = 1; i < blueCords.size(); i++) {
            blueSprite.setPosition(blueCords.get(i)[0], blueCords.get(i)[1]);
            blueSprite.draw(batch);
            drawer.setColor(Color.BLUE);
            drawer.line(blueCords.get(i - 1)[0] + ((float) radius / 2), blueCords.get(i - 1)[1] + ((float) radius / 2), blueCords.get(i)[0] + ((float) radius / 2), blueCords.get(i)[1] + ((float) radius / 2));

        }


        if (Gdx.input.justTouched()) {
            int x1 = Gdx.input.getX();
            int y1 = Gdx.input.getY();
            Vector3 input = new Vector3(x1, y1, 0);
            camera.unproject(input);

            if ((redSpriteFinish.getBoundingRectangle().contains(input.x, input.y)) && (redStart)) {
                redFinish = true;
                redStart = false;
                System.out.println("Hooray red");
            }
            if ((blueSpriteFinish.getBoundingRectangle().contains(input.x, input.y)) && (blueStart)) {
                blueFinish = true;
                blueStart = false;
                System.out.println("Hooray blue");
            }

            if (redStart && !(redFinish)) {
                int[] cords = new int[2];
                cords[0] = x1 - (radius / 2);
                cords[1] = y1 - (radius / 2);
                redCords.add(cords);
            }

            if (blueStart && !(blueFinish)) {
                int[] cords = new int[2];
                cords[0] = x1 - (radius / 2);
                cords[1] = y1 - (radius / 2);
                blueCords.add(cords);
            }

            if ((redSpriteStart.getBoundingRectangle().contains(input.x, input.y)) && !(blueStart)) {
                redStart = true;
                System.out.println("red");
            }
            if ((blueSpriteStart.getBoundingRectangle().contains(input.x, input.y)) && !(redStart)) {
                blueStart = true;
                System.out.println("blue");
            }

        }
        // special cases
        if (redFinish && !(redStart)) {
            drawer.setColor(Color.RED);
            drawer.line(redCords.get(redCords.size() - 1)[0] + ((float) radius / 2), redCords.get(redCords.size() - 1)[1] + ((float) radius / 2), redSpriteFinish.getX() + ((float) radius / 2), redSpriteFinish.getY() + ((float) radius / 2));

        }
        if (blueFinish && !(blueStart)) {
            drawer.setColor(Color.BLUE);
            drawer.line(blueCords.get(blueCords.size() - 1)[0] + ((float) radius / 2), blueCords.get(blueCords.size() - 1)[1] + ((float) radius / 2), blueSpriteFinish.getX() + ((float) radius / 2), blueSpriteFinish.getY() + ((float) radius / 2));

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
        batch.dispose();
        bgTexture.dispose();
        redTexture.dispose();
        blueTexture.dispose();
        stage.dispose();
    }

    private void addLast(ArrayList<int[]> redCords, ArrayList<int[]> blueCords) {
        int[] cords1 = new int[2];
        cords1[0] = x12;
        cords1[1] = y12;
        int[] cords2 = new int[2];
        cords2[0] = x22;
        cords2[1] = y22;

        redCords.add(cords1);
        blueCords.add(cords2);
        lasts = true;
    }

    int det(int[] a, int[] b) {
        return a[0] * b[1] - a[1] - b[0];
    }

    boolean hasIntersection(ArrayList<int[]> line1, ArrayList<int[]> line2) {
        int[] xdiff = new int[2];
        xdiff[0] = line1.get(0)[0] - line1.get(1)[0];
        xdiff[1] = line2.get(0)[0] - line2.get(1)[0];

        int[] ydiff = new int[2];
        ydiff[0] = line1.get(0)[1] - line1.get(1)[1];
        ydiff[1] = line2.get(0)[1] - line2.get(1)[1];

        int div = det(xdiff, ydiff);
        if (div == 0) {
            return false;
        }
        int[] d = new int[2];
        d[0] = det(line1.get(0), line1.get(1));
        d[1] = det(line2.get(0), line2.get(1));
        float x = det(d, xdiff) / (float) div;
        float y = det(d, ydiff) / (float) div;
        return ((Math.min(line1.get(0)[0], line1.get(1)[0])) <= x) && (x <= (Math.max(line1.get(0)[0], line1.get(1)[0]))) && ((Math.min(line1.get(0)[1], line1.get(1)[1])) <= y) && (y <= (Math.max(line1.get(0)[1], line1.get(1)[1])));
    }

    public static int orientation(Point p, Point q, Point r) {
        double val = (q.getY() - p.getY()) * (r.getX() - q.getX())
                - (q.getX() - p.getX()) * (r.getY() - q.getY());

        if (val == 0.0)
            return 0; // colinear
        return (val > 0) ? 1 : 2; // clock or counterclock wise
    }

    public static boolean intersect(int[] p1, int[] q1, int[] p2, int[] q2) {
        Point pp1 = new Point(p1[0], p1[1]);
        Point qq1 = new Point(q1[0], q1[1]);
        Point pp2 = new Point(p2[0], p2[1]);
        Point qq2 = new Point(q2[0], q2[1]);

        int o1 = orientation(pp1, qq1, pp2);
        int o2 = orientation(pp1, qq1, qq2);
        int o3 = orientation(pp2, qq2, pp1);
        int o4 = orientation(pp2, qq2, qq1);

        if (o1 != o2 && o3 != o4)
            return true;

        return false;
    }

}
