package com.gamingpotatoe.pointandclick.essences;

import static com.gamingpotatoe.pointandclick.Globals.FLUCTUATION;
import static com.gamingpotatoe.pointandclick.Globals.LOWEST_OPENING;
import static com.gamingpotatoe.pointandclick.Globals.TUBE_GAP;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;
public class Tube {
    Texture topTube, bottomTube;
    Vector2 posTopTube, posBotTube;
    Random rand;
    Rectangle boundsTop, boundsBot;


    public Texture getTopTube() {
        return topTube;
    }

    public Texture getBottomTube() {
        return bottomTube;
    }

    public Vector2 getPosTopTube() {
        return posTopTube;
    }

    public Vector2 getPosBotTube() {
        return posBotTube;
    }

    public Tube(float x){
        topTube = new Texture("textures/toptube.png");
        bottomTube = new Texture("textures/bottomtube.png");
        rand = new Random();

        posTopTube = new Vector2(x, rand.nextInt(FLUCTUATION) + TUBE_GAP + LOWEST_OPENING);
        posBotTube = new Vector2(x, posTopTube.y - TUBE_GAP - bottomTube.getHeight());
        boundsTop = new Rectangle(posTopTube.x, posTopTube.y, topTube.getWidth(), topTube.getHeight());
        boundsBot = new Rectangle(posBotTube.x, posBotTube.y, bottomTube.getWidth(), bottomTube.getHeight());

    }

    public void reposition(float x){
        posTopTube.set(x, rand.nextInt(FLUCTUATION) + TUBE_GAP + LOWEST_OPENING);
        posBotTube.set(x, posTopTube.y - TUBE_GAP - bottomTube.getHeight());
        boundsTop.setPosition(posTopTube.x, posTopTube.y);
        boundsBot.setPosition(posBotTube.x, posBotTube.y);
    }
    public boolean collide(Rectangle player){
        return player.overlaps(boundsTop) || player.overlaps(boundsBot);
    }
    public void dispose() {
        topTube.dispose();
        bottomTube.dispose();
    }

}