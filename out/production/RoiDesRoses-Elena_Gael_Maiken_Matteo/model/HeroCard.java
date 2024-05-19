package model;

import boardifier.model.ElementTypes;
import boardifier.model.GameElement;
import boardifier.model.GameStageModel;
import boardifier.view.ElementLook;
import view.HeroCardLook;

public class HeroCard extends GameElement {
    private int number;
    private int color;

    public static int HERO_CARD_BLUE = 0;
    public static int HERO_CARD_RED = 1;


    public HeroCard(int number, int color, GameStageModel gameStageModel){
        super(gameStageModel);
        ElementTypes.register("herocard", 70);
        type = ElementTypes.getType("herocard");
        this.color = color;
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public int getColor() {
        return color;
    }
}
