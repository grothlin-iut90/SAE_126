package model;

import boardifier.model.ElementTypes;
import boardifier.model.GameElement;
import boardifier.model.GameStageModel;

/**
 * A basic pawn element, with only 2 fixed parameters : number and color
 * There are no setters because the state of a Hole pawn is fixed.
 */
public class Card extends GameElement {
    private int direction;
    public static int N = 0;
    public static int W = 1;
    public static int S = 2;
    public static int E = 3;
    public static int NW = 4;
    public static int SW = 5;
    public static int SE = 6;
    public static int NE = 7;


    public Card(int direction, GameStageModel gameStageModel) {
        super(gameStageModel);
        // registering element types defined especially for this game
        ElementTypes.register("card",60);
        type = ElementTypes.getType("card");
        this.direction = direction;
    }

    public int getDirection() {
        return direction;
    }

}
