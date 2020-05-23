package com.mygdx.floppydemo.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Bird {
    private static final int MOVEMENT = 100;
    private static final  int GRAVITY = -15;
    private Vector2 position;
    private Vector2 velocity;
    private Rectangle boundsBird;
    private Animation birdAnimation;
    private Texture textureAnimation;
    private Sound flap;

    public Bird(int x, int y){
        position = new Vector2(x, y);
        velocity = new Vector2(0,0);
        textureAnimation = new Texture(Gdx.files.internal(("birdanimation.png")));
        birdAnimation = new Animation(new TextureRegion(textureAnimation), 3, 0.5f);
        boundsBird = new Rectangle(x, y, textureAnimation.getWidth()/3, textureAnimation.getHeight());
        flap = Gdx.audio.newSound(Gdx.files.internal("sfx_wing.ogg"));
        flap.setVolume(1, 0.01f);

    }

    public Vector2 getPosition() {
        return position;
    }

    public TextureRegion getBirdTexture() {
        return birdAnimation.getFrame();
    }

    public void update(float dt){
        birdAnimation.update(dt);
        if(position.y > 0)
            velocity.add(0, GRAVITY);
        velocity.scl(dt);
        position.add(MOVEMENT*dt, velocity.y);
        if(position.y < 0)
            position.y = 0;
        velocity.scl(1/dt);

        boundsBird.setPosition(getPosition().x, getPosition().y);

    }

    public void jump(){
        velocity.y = 250;
        flap.play();
    }

    public Rectangle getBoundsBird(){
        return boundsBird;
    }

    public void dispose() {
        textureAnimation.dispose();
        flap.dispose();
    }
}
