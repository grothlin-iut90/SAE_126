// PlayerCardHand.java
package model;

import boardifier.model.ContainerElement;
import boardifier.model.GameStageModel;
import boardifier.control.Controller;

import java.util.ArrayList;
import java.util.List;

public class PlayerCardHand extends ContainerElement {


    private Controller controller;

    public PlayerCardHand(int x, int y, GameStageModel gameStageModel){
        super("playercardhand", x, y, 1, 5, gameStageModel);
    }

    public void removeCardFromPlayerHand(Card card){
        removeElement(card);
        card.setVisible(false);
    }

    public void addCardToPlayerHand(Card card, int index) {
        addElement(card, 0, index); // Ajoute la carte à la main
        card.setVisible(true);
    }


    public List<Integer> getFreeCardSlots(PlayerCardHand playerCardHand) {
        this.container = playerCardHand;
        List<Integer> freeSlots = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            if (container.getElement(0, i) == null) {
                freeSlots.add(i);
            }
        }
        return freeSlots;
    }
}
