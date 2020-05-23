package com.mygdx.floppydemo.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.floppydemo.FlappyDemo;
import com.mygdx.floppydemo.properties.FileScore;
import com.mygdx.floppydemo.properties.Record;
import com.mygdx.floppydemo.sprites.Bird;
import com.mygdx.floppydemo.sprites.Tube;

public class PlayState extends State {
    public static final int TUBE_SPACING = 125; // растояние между сериями труб
    public static final int TUBE_COUNT = 5; // сколько труб подряд
    public static final String FONT_CHARACTERS =
            "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\/?-+=()*&.;,{}\"´`'<>";
    private static final int GROUND_Y_OFFSET = -30;

    private Bird bird;
    private Texture bg;
    private Texture ground;
    private Vector2 groundPos1, groundPos2;
    private Array<Rectangle> polyScore;
    private Array<Tube> tubes;
    private int count_of_score;
    private BitmapFont FontRed1;
    private BitmapFont number_of_score;
    private BitmapFont congrate_ab_score;

    private FileScore fileScore;
    private Record testrl;
    private boolean isHighScore;
    public PlayState(GameStateManager gsm) {
        super(gsm);

        fileScore = new FileScore();
        testrl = new Record();
        camera.setToOrtho(false, FlappyDemo.WIDTH/2, FlappyDemo.HEIGHT/2);
        FontRed1 = new BitmapFont();
        FontRed1.setColor(Color.RED);

        number_of_score = new BitmapFont();
        number_of_score.setColor(Color.RED);

        congrate_ab_score = new BitmapFont();
        congrate_ab_score.setColor(Color.GREEN);

        count_of_score = 0;

        bird = new Bird(50,300);
        bg = new Texture(Gdx.files.internal("bg.png"));
        ground = new Texture(Gdx.files.internal("ground.png"));
        groundPos1 = new Vector2(camera.position.x - camera.viewportWidth/2, GROUND_Y_OFFSET);
        groundPos2 = new Vector2(camera.position.x - camera.viewportWidth/2 + ground.getWidth(), GROUND_Y_OFFSET);

        tubes = new Array<Tube>();
        polyScore = new Array<Rectangle>();

        for (int i = 1; i < TUBE_COUNT; i++){
            tubes.add(new Tube(i*(TUBE_SPACING + Tube.TUBE_WIDTH)));
            polyScore.add(new Rectangle(tubes.get(i-1).getPosBottomTube().x+
                    tubes.get(i-1).getBottomTubeTexture().getWidth(),
                    tubes.get(i-1).getPosBottomTube().y +
                            tubes.get(i-1).getBottomTubeTexture().getHeight(),
                    0.00000001f, tubes.get(i-1).TUBE_GAP));
        }



    }

    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched())
            bird.jump();
    }

    @Override
    public void update(float dt) {

        handleInput();
        bird.update(dt);
        updateGround();
        camera.position.x = bird.getPosition().x + 80;

        for (int i = 0; i < tubes.size; i++){
            Tube tube = tubes.get(i);
            if(camera.position.x - camera.viewportWidth/2 > tube.getPosTopTube().x +
                    tube.getTopTubeTexture().getWidth()){
                tube.reposition(tube.getPosTopTube().x + (Tube.TUBE_WIDTH + TUBE_SPACING)* TUBE_COUNT);
            Rectangle poly = polyScore.get(i);
                  poly.setPosition(tubes.get(i).getPosBottomTube().x+
                                  tubes.get(i).getBottomTubeTexture().getWidth(),
                          tubes.get(i).getPosBottomTube().y +
                                  tubes.get(i).getBottomTubeTexture().getHeight());

            }
            if (tube.collides(bird.getBoundsBird())){ // конец игры, если ударился об трубу
                gsm.set(new GameOver(gsm));
            }

        }

        if(bird.getPosition().y <= ground.getHeight() + GROUND_Y_OFFSET ||
                bird.getPosition().y <= ground.getHeight()+ GROUND_Y_OFFSET) // конец игры, если дошёл до низа
            gsm.set(new GameOver(gsm));
        camera.update();


    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(camera.combined);
        sb.begin();

        sb.draw(bg, camera.position.x - camera.viewportWidth/2, 0);
        sb.draw(bird.getBirdTexture(), bird.getPosition().x, bird.getPosition().y);
        for(Tube tube: tubes){
            sb.draw(tube.getTopTubeTexture(), tube.getPosBottomTube().x, tube.getPosTopTube().y);
            sb.draw(tube.getBottomTubeTexture(), tube.getPosBottomTube().x, tube.getPosBottomTube().y);
        }
        sb.draw(ground, groundPos1.x, groundPos1.y);
        sb.draw(ground, groundPos2.x, groundPos2.y);
        FontRed1.draw(sb, "SCORE:", camera.position.x-100, 20);
        number_of_score.draw(sb, Integer.toString(count_of_score), camera.position.x -30, 20);
        for(int i = 0; i<polyScore.size; i++) {
            if (bird.getPosition().x >= polyScore.get(i).x && bird.getPosition().x <= polyScore.get(i).x+2f) {
                count_of_score += 1;
                number_of_score.draw(sb, Integer.toString(count_of_score),  camera.position.x-30, 20);

                }
            }
        //if(Double.toString(count_of_score).compareTo(highScore.getRecord())==0){
        /*
           if(Integer.compare(count_of_score, Integer.parseInt(fileScore.getRecord())) > 0){
               fileScore.writeFile("highScore = " + Integer.toString(count_of_score));
               congrate_ab_score.draw(sb, "NEW SCORE, BOIII!\n" + Integer.toString(count_of_score),
                       camera.position.x -100, 400);
        }
        */

            if(Integer.compare(count_of_score, Integer.parseInt(testrl.getRecord().getProperty("highScore"))) >= 0){
                testrl.insertRecord(Integer.toString(count_of_score));
                congrate_ab_score.draw(sb, "NEW SCORE, BOIII!\n" + Integer.toString(count_of_score),
                        camera.position.x -100, 400);
            }
        sb.end();
    }

    @Override
    public void dispose() {
        bg.dispose();
        bird.dispose();
        ground.dispose();
        for (Tube tube: tubes) {
            tube.dispose();
        }
    }

    public void updateGround(){
        if(camera.position.x - (camera.viewportWidth/2) > groundPos1.x + ground.getWidth())
            groundPos1.add(ground.getWidth() *2, 0);
        if(camera.position.x - (camera.viewportWidth/2) > groundPos2.x + ground.getWidth())
        groundPos2.add(ground.getWidth() *2, 0);
    }

}
