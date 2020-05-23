package com.mygdx.floppydemo.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.floppydemo.FlappyDemo;

public class GameOver extends State {

    private Texture background;
    private Texture gaveOver;
    private SpriteBatch sb;


    public GameOver(GameStateManager gsm) {
        super(gsm);
        camera.setToOrtho(false, FlappyDemo.WIDTH/2, FlappyDemo.HEIGHT/2);
        background = new Texture(Gdx.files.internal("bg.png"));
        gaveOver = new Texture(Gdx.files.internal("gameover.png"));
    }

    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched()){
            gsm.set(new PlayState(gsm));
        }

    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        sb.draw(background, 0, 0);
        sb.draw(gaveOver, camera.position.x - gaveOver.getWidth()/2, camera.position.y);
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        gaveOver.dispose();
    }
}
