package model;

import boardifier.model.ElementTypes;
import boardifier.model.GameElement;
import boardifier.model.GameStageModel;

/**
 * A basic pawn element, with only 2 fixed parameters : number and color
 * There are no setters because the state of a Hole pawn is fixed.
 */
public class Card extends GameElement {
    private final int direction;
    public static int N = 0;
    public static int W = 1;
    public static int S = 2;
    public static int E = 3;
    public static int NW = 4;
    public static int SW = 5;
    public static int SE = 6;
    public static int NE = 7;

    private final int cardStep;


    public Card(int direction, int cardStep, GameStageModel gameStageModel) {
        super(gameStageModel);
        // registering element types defined especially for this game
        ElementTypes.register("card",60);
        type = ElementTypes.getType("card");
        this.direction = direction;
        this.cardStep = cardStep;
    }

    public int getDirection() {
        return this.direction;
    }

    public String getStringDirection() {
        switch (getDirection()) {
            case 0:
                return "N";
            case 1:
                return "W";
            case 2:
                return "S";
            case 3:
                return "E";
            case 4:
                return "NW";
            case 5:
                return "SW";
            case 6:
                return "SE";
            case 7:
                return "NE";
            default:
                return "?";
        }
    }


    public int getCardSteps() {
        return this.cardStep;
    }

}
