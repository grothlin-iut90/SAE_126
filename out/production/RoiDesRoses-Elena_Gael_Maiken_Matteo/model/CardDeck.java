// CardDeck.java
package model;

import boardifier.model.ContainerElement;
import boardifier.model.GameStageModel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CardDeck extends ContainerElement {

    private List<Card> cards; // Liste des cartes dans le deck
    private int drawnCards;

    public CardDeck(int x, int y, GameStageModel gameStageModel){
        super("cardDeck", x, y, 1, 1, gameStageModel);
        initializeDeck();
    }

    private void initializeDeck() {
        cards = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            cards.add(new Card(i, (i % 13) + 1, gameStageModel)); // Initialisation simple pour l'exemple
        }
        Collections.shuffle(cards); // Mélange le deck
    }

    public Card drawCard() {
        if (!cards.isEmpty()) {
            return cards.remove(cards.size() - 1); // Retire et renvoie la dernière carte
        } else {
            throw new IllegalStateException("No more cards available");
        }
    }

}
