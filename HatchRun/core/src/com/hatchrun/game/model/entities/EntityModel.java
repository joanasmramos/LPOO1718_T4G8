package com.hatchrun.game.model.entities;

import com.hatchrun.game.model.GameModel;

import java.util.ArrayList;

/**
 * A model representing a game element which has a certain lane
 */
public  class EntityModel {

    private  int width = 200;
    private  int heigth = 200;

    public enum ElementLane {LEFT, MIDDLE, RIGHT};

    private ElementLane lane;
    private float x;
    private float y;

    /**
     * Constructs a game element in the given position
     * @param lane The current lane of the element
     * @param x X coordinate
     * @param y Y coordinate
     */
    EntityModel(ElementLane lane, float x, float y) {
        this.lane = lane;
        this.x = x;
        this.y = y;
    }


    EntityModel(ElementLane lane, float x, float y, int width, int heigth) {
        this.lane = lane;
        this.x = x;
        this.y = y;
        this.width = width;
        this.heigth = heigth;
    }

    /**
     * Returns the element's lane
     * @return Element's lane
     */
    public ElementLane getLane() {
        return lane;
    }
    /**
     * Sets the element's lane to the given lane
     * @param lane Given lane
     */
    public void setLane(ElementLane lane) {
        this.lane = lane;
    }

    /**
     * Returns the game element's x coordinate in meters
     * @return X coordinate
     */
    public float getX() {
        return x;
    }

    /**
     * Sets the game element's x coordinate
     * @param x X coordinate in meters
     */
    public void setX(float x) {
        this.x = x;
    }

    /**
     * Returns the game element's y coordinate in meters
     * @return Y coordinate
     */
    public float getY() {
        return y;
    }

    /**
     * Sets the game element's y coordinate
     * @param y Y coordinate in meters
     */
    public void setY(float y) {
        this.y = y;
    }


    public  int getWidth() {
        return width;
    }

    public  int getHeight() {
        return heigth;
    }


}
