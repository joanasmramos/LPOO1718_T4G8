package com.hatchrun.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.hatchrun.game.HatchRun;
import com.hatchrun.game.model.GameModel;
import com.hatchrun.game.model.entities.EntityModel;
import com.hatchrun.game.model.entities.HatchModel;
import com.hatchrun.game.view.GameView;
import com.hatchrun.game.view.entities.HatchView;

public class ChooseHatchView extends ScreenAdapter {

    private HatchRun game;
    private Texture t;
    private TextButton playButton;
    private Stage stage;
    private FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/knewave-outline.ttf"));
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
    private TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
    private BitmapFont buttonFont;
    InputMultiplexer multiplexer = new InputMultiplexer();


    public ChooseHatchView(HatchRun game){
        this.game = game;
        this.stage = new Stage();
        initButtons();
        initMultiplexer();
        Gdx.input.setInputProcessor(multiplexer);
    }



    @Override
    public void dispose(){
        stage.dispose();
    }


    @Override
    public void render(float delta) {

        game.getBatch().begin();

        t = game.getAssetManager().get("choosemenu.png", Texture.class);
        game.getBatch().draw(t,0,0);

        (new HatchView(game,new HatchModel(EntityModel.ElementLane.MIDDLE,0,0),true)).draw(
                game.getBatch(), Gdx.graphics.getWidth()/2-290/2,Gdx.graphics.getHeight()/3);


        game.getBatch().end();

        this.stage.act();
        this.stage.draw();
    }


    private void addButtonsListeners(){
        playButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameView(game));
            }
        });
    }

    private void initButtons(){
        parameter.size = 150;
        parameter.color = Color.WHITE;
        buttonFont = generator.generateFont(parameter);
        style.font = buttonFont;

        playButton = new TextButton("Play",style);
        playButton.setPosition(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/5, Align.center);

        this.stage.addActor(playButton);

        addButtonsListeners();
    }



    private void initMultiplexer() {
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(new GestureDetector(new GestureDetector.GestureAdapter() {
            @Override
            public boolean fling(float velocityX, float velocityY, int button) {


                if (velocityX > 0) {

                    if (GameModel.getHatchOrderIndex() > 0)
                        GameModel.setHatchOrderIndex(GameModel.getHatchOrderIndex() - 1);
                    else GameModel.setHatchOrderIndex(GameModel.getHatchOrder().size()-1);


                } else if (velocityX < 0){

                    if (GameModel.getHatchOrderIndex() < GameModel.getHatchOrder().size()-1)
                        GameModel.setHatchOrderIndex(GameModel.getHatchOrderIndex() +1);
                    else GameModel.setHatchOrderIndex(0);
                }
                return true;
            }

        }));
    }

}