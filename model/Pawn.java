package model;

import boardifier.model.ContainerElement;
import boardifier.model.ElementTypes;
import boardifier.model.GameElement;
import boardifier.model.GameStageModel;


/**
 * A basic pawn element, with only 2 fixed parameters : number and color
 * There are no setters because the state of a Hole pawn is fixed.
 */
public class Pawn extends GameElement {
    private int color;
    private int number;
    public static int PAWN_BLUE = 0;
    public static int PAWN_RED = 1;
    public static int PAWN_YELLOW = 2;

    public Pawn(int number, int color, GameStageModel gameStageModel) {
        super(gameStageModel);
        // registering element types defined especially for this game
        ElementTypes.register("pawn",50);
        type = ElementTypes.getType("pawn");
        this.color = color;
        this.number = number;
    }

    public int getColor() {
        return color;
    }

    public int getNumber(){
        return number;
    }

    public int[] getPosition() {
        RDRStageModel model = (RDRStageModel) gameStageModel;
        ContainerElement container = model.getContainer(this);
        if (container != null) {
            return container.getElementCell(this);
        }
        return null;
    }
}
