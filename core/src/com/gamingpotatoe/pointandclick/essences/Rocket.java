package com.gamingpotatoe.pointandclick.essences;

import static com.gamingpotatoe.pointandclick.Globals.GRAVITY;
import static com.gamingpotatoe.pointandclick.Globals.MOVEMENT;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Rocket {
    Vector2 position, velocity;
    Texture rocket;
    Rectangle bounds;

    public Rocket(int x, int y) {
        position = new Vector2(x, y);
        velocity = new Vector2(0, 0);
        rocket = new Texture("textures/rocket.png");
        bounds = new Rectangle(x, y, rocket.getWidth(), rocket.getHeight());
    }
    public void update(float delta){
        if (position.y > 0){
            velocity.add(0, GRAVITY);
        }
        velocity.scl(delta);
        position.add((MOVEMENT * delta), velocity.y);
        if (position.y < 0){
            position.y = 0;
        }
        velocity.scl(1/delta);
        bounds.setPosition(position.x, position.y);

    }

    public Vector2 getPosition() {
        return position;
    }

    public Texture getRocket() {
        return rocket;
    }
    public void fly(){
        velocity.y = 250;
    }
    public Rectangle getBounds(){
        return bounds;
    }
    public void dispose(){
        rocket.dispose();
    }
}
