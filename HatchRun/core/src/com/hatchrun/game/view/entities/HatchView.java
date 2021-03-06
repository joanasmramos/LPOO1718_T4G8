package com.hatchrun.game.view.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.hatchrun.game.HatchRun;
import com.hatchrun.game.model.GameModel;
import com.hatchrun.game.model.entities.HatchModel;

/**
 * Hatch view
 */
public class HatchView extends EntityView {

    private HatchModel model;
    private Texture texture;
    private Texture textureShield;
    private Sprite s;
    private TextureAtlas hatchAtlas;
    private Animation<TextureRegion> animation;
    private float stateTime = 0;

    /**
     * Constructs a hatch view
     * @param game Game
     * @param model Hatch's model
     */
    public HatchView(HatchRun game, HatchModel model) {
        super(game);
        this.model = model;
        createAtlas();
        animation = new Animation<TextureRegion>(1 / 3f, hatchAtlas.getRegions());
        textureShield = game.getAssetManager().get("shieldeffect.png", Texture.class);
    }

    /**
     * Creates an atlas
     */
    private void createAtlas() {
        switch (GameModel.getInstance().getHatchOrder().get(GameModel.getInstance().getHatchOrderIndex())) {
            case BLUE:
                hatchAtlas = new TextureAtlas(Gdx.files.internal("bluehatch.atlas"));
                break;
            case PURPLE:
                hatchAtlas = new TextureAtlas(Gdx.files.internal("purplehatch.atlas"));
                break;
            case YELLOW:
                hatchAtlas = new TextureAtlas(Gdx.files.internal("yellowhatch.atlas"));
                break;
        }
    }

    /**
     * Creates sprite
     * @param game Game
     * @return Sprite created
     */
    @Override
    public Sprite createSprite(HatchRun game) {

        switch (GameModel.getInstance().getHatchOrder().get(GameModel.getInstance().getHatchOrderIndex())) {
            case BLUE:
                texture = new Texture("blue.png");
                break;
            case PURPLE:
                texture = new Texture("purple.png");
                break;
            case YELLOW:
                texture = new Texture("yellow.png");
        }

        s = new Sprite(texture);
        s.setCenter(Gdx.graphics.getWidth() / 2, texture.getHeight() / 2 + 100);

        return s;
    }

    /**
     * Draws hatch
     * @param batch Batch
     * @param x x coordinate
     * @param y y coordinate
     */
    @Override
    public void draw(SpriteBatch batch, float x, float y) {
        stateTime += Gdx.graphics.getDeltaTime();
        s.setX(x);
        s.setY(y);
        if (model.getCurrentState() == HatchModel.HatchState.STILL) s.draw(batch);

        else batch.draw(animation.getKeyFrame(stateTime, true), x, y);


        if (GameModel.getInstance().getHatch().isShielded()) {

            Sprite s = new Sprite(textureShield);
            s.setY(y - 25);
            s.setX(x - 35);
            s.draw(batch);

        }

    }

    /**
     * Sets the boolean still
     * @param still True if hatch is still, false otherwise
     */
    public void setStill(boolean still){
        if(still) GameModel.getInstance().getHatch().setCurrentState(HatchModel.HatchState.STILL);
        else GameModel.getInstance().getHatch().setCurrentState(HatchModel.HatchState.RUNNING);
    }

}
