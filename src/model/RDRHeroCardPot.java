package model;

import boardifier.model.ContainerElement;
import boardifier.model.GameStageModel;

public class RDRHeroCardPot extends ContainerElement {
    public RDRHeroCardPot(int x, int y, GameStageModel gameStageModel) {
        // call the super-constructor to create a 2x13 grid, named "pawnpot", and in x,y in space
        super("herocardpot", x, y, 1, 4, gameStageModel);
    }
}