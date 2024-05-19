package model;

import boardifier.model.ContainerElement;
import boardifier.model.GameStageModel;
import boardifier.control.Controller;

public class PlayerCardHand extends ContainerElement {

    private Controller controller;
    public PlayerCardHand (int x, int y, GameStageModel gameStageModel){
        super("playercardhand", x, y, 1,5, gameStageModel);
    }

    public void removeCardFromPlayerHand(Card card){
        removeElement(card);
        card.setVisible(false);
    }
}
