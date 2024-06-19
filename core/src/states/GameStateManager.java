package states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Stack;

public class GameStateManager {
    Stack<State> states;

    public GameStateManager() {
        states = new Stack<State>();
    }

    public void set(State state){
        states.pop();
        states.push(state);
    }
    public void update(float delta){
        states.peek().update(delta);
    }
    public void render(SpriteBatch batch){
        states.peek().render(batch);
    }
    public void push(State state){
        states.push(state);
    }
    public void pop(){
        states.pop().dispose();
    }

}
