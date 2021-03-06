package com.hatchrun.game.model.entities;


/**
 * A model representing a game object
 */
public class ObstacleModel extends EntityModel {

   /* private static int width = 292;
    private static int heigth = 266;

*/

    public enum Colour {PINK, BLUE, YELLOW}


    private Colour colour;

    /**
     * Constructs a game element in the given position
     *
     * @param lane The current lane of the element
     * @param x X coordinate
     * @param y Y coordinate
     */
    public ObstacleModel(ElementLane lane, float x, float y, Colour c) {
        super(lane, x, y);
        this.colour = c;
        super.setHeigth(239);

    }


    /**
     * Returns hatch's current colour
     * @return hatch's colour
     */
    public Colour getColour() {
        return colour;
    }


}
