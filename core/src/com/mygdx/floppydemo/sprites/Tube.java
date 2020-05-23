package com.mygdx.floppydemo.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class Tube {
    public static final int TUBE_WIDTH = 100; // нужна для того, чтобы изначальное растояние между птицой и трубами

    public static final int FLUCTUATION = 150; // колебания в высоте прохода
    public static final int  TUBE_GAP = 100; // радиус прохода
    public static final int  LOWEST_OPENING = 70; // тоже отвечает в высоте прохода

    private Texture topTubeTexture;

    public Texture getTopTubeTexture() {
        return topTubeTexture;
    }

    public Texture getBottomTubeTexture() {
        return bottomTubeTexture;
    }

    public Vector2 getPosTopTube() {
        return posTopTube;
    }

    public Vector2 getPosBottomTube() {
        return posBottomTube;
    }

    private Texture bottomTubeTexture;
    private Vector2 posTopTube, posBottomTube;
    private Random random;
    private Rectangle boundsTop, boundsBottom;

    public Tube(float x){
        topTubeTexture = new Texture(Gdx.files.internal("toptube.png"));
        bottomTubeTexture =  new Texture(Gdx.files.internal("bottomtube.png"));

        random = new Random();
        posTopTube = new Vector2(x, random.nextInt(FLUCTUATION) + TUBE_GAP + LOWEST_OPENING );
        posBottomTube = new Vector2(x, posTopTube.y - TUBE_GAP - bottomTubeTexture.getHeight());

        boundsTop = new Rectangle(posTopTube.x, posTopTube.y, topTubeTexture.getWidth(), topTubeTexture.getHeight());
        boundsBottom = new Rectangle(posBottomTube.x, posBottomTube.y, bottomTubeTexture.getWidth(), bottomTubeTexture.getHeight());


    }

    public void reposition(float x){
        posTopTube.set(x, random.nextInt(FLUCTUATION) + TUBE_GAP + LOWEST_OPENING);
        posBottomTube.set(x, posTopTube.y - TUBE_GAP - bottomTubeTexture.getHeight());
        boundsTop.setPosition(posTopTube.x, posTopTube.y);
        boundsBottom.setPosition(posBottomTube.x, posBottomTube.y);
    }

    public boolean collides(Rectangle player){
        return player.overlaps(boundsTop) || player.overlaps(boundsBottom);
    }

    public void dispose() {
        topTubeTexture.dispose();
        bottomTubeTexture.dispose();
    }
}
