package com.hatchrun.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.hatchrun.game.HatchRun;
import com.hatchrun.game.Utilities.Facebook;

/**
 * A class representing the main menu view, a screen adapter
 */
public class MainMenuView extends ScreenAdapter {
    private HatchRun game;
    private TextButton playButton;
    private TextButton fbButton;
    private Stage stage;
    private FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/knewave-outline.ttf"));
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
    private TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
    private BitmapFont buttonFont;


    /**
     * Main Menu View contructor
     * @param game
     */
    public MainMenuView(HatchRun game){
        this.game = game;
        this.stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        initButtons();
    }

    /**
     * Disposes the stage
     */
    @Override
    public void dispose(){
        stage.dispose();
    }

    /**
     * Updates according to time
     * @param delta Time since last update
     */
    @Override
    public void render(float delta) {

        game.getBatch().begin();

        Texture t = game.getAssetManager().get("mainmenu.png", Texture.class);
        game.getBatch().draw(t, 0, 0,Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        game.getBatch().end();
        this.stage.act();
        this.stage.draw();
    }

    /**
     * Adds the main menu's buttons listeners
     */
    private void addButtonsListeners(){
        playButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new ChooseHatchView(game));
            }
        });


        fbButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Facebook fb = new Facebook();
                fb.login();
            }
        });

    }

    /**
     * Initialises buttons
     */
    private void initButtons(){
        parameter.size = 100;
        buttonFont = generator.generateFont(parameter);
        style.font = buttonFont;

        playButton = new TextButton("Play",style);
        playButton.setPosition(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/4, Align.center);

        stage.addActor(playButton);

        parameter.size = 80;
        buttonFont = generator.generateFont(parameter);
        style.font = buttonFont;
        fbButton = new TextButton("Connect to facebook",style);
        fbButton.setPosition(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/7, Align.center);

        this.stage.addActor(fbButton);

        addButtonsListeners();
    }

}
