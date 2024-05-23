package model;

import boardifier.model.GameElement;
import boardifier.model.GameStageModel;

public class CardBack extends GameElement {
    private CardDeck deck;
    public CardBack(CardDeck deck, GameStageModel gameStageModel){
        super(gameStageModel);
        this.deck = deck;
        setVisible(true);
    }

    public CardDeck getDeck() {
        return deck;
    }

    public void setDeck(CardDeck deck) {
        this.deck = deck;
    }
}