// PlayerCardHand.java
package model;

import boardifier.model.ContainerElement;
import boardifier.model.GameStageModel;
import boardifier.control.Controller;

import java.util.ArrayList;
import java.util.List;

public class PlayerCardHand extends ContainerElement {

    private Controller controller;
    private int numberCardsInPlayerHand;
    private boolean[] availableStlots;

    public PlayerCardHand(int x, int y, GameStageModel gameStageModel){
        super("playercardhand", x, y, 1, 5, gameStageModel);
        numberCardsInPlayerHand = 0;
        availableStlots = new boolean[5];
        for(int i = 0; i < 5; i++){
            availableStlots[i] = false;
        }
    }

    public Card removeCardFromPlayerHand(Card card, int numberCardPlayed){
        removeElement(card);
        card.setVisible(false);
        availableStlots[numberCardPlayed] = true;
        numberCardsInPlayerHand--;
        return card;
    }

    public void addCardToPlayerHand(Card card) {
        for(int i = 0; i < 5; i++){
            if(availableStlots[i]){
                addElement(card, 0, i); // Ajoute la carte à la main
                card.setVisible(true);
                System.out.println(card);
                break;
            }
        }
    }

    public int getIndexAvailableSpace(){
        for(int i = 0; i < 5; i++){
            if(availableStlots[i]){
                numberCardsInPlayerHand++;
                return i;
            }
        }
        return -1;
    }

    public boolean canDrawCard(){
        return numberCardsInPlayerHand < 5;
    }

    /*
    public List<Integer> getFreeCardSlots() {
        List<Integer> freeSlots = new ArrayList<>();
        for (int i = 0; i < sta; i++) {
            if (getElementAt(0, i) == null) {
                freeSlots.add(i);
            }
        }
        return freeSlots;
    }
    */
}
