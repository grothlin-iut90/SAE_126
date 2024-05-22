// CardDeck.java
package model;

import boardifier.model.ContainerElement;
import boardifier.model.GameStageModel;
import java.util.ArrayList;
import java.util.Collections;

public class CardDeck extends ContainerElement {

    private ArrayList<Card> cards; // Liste des cartes dans le deck
    private int numberCardsInDeck;

    public CardDeck(int x, int y, GameStageModel gameStageModel){
        super("cardDeck", x, y, 1, 1, gameStageModel);
        int numberCardsInDeck = 24;
        cards = new ArrayList<>();
        // initializeDeck();
    }

    private void initializeDeck() {
        for (int i = 0; i < numberCardsInDeck; i++) {
            //cards.add(new Card((i % 8), 1, gameStageModel)); // Initialisation simple pour l'exemple
            cards.add(new Card((i % 8), (i % 3) + 1, gameStageModel)); // Initialisation simple pour l'exemple
            this.addElement(cards.get(i), 0, i);
        }
        Collections.shuffle(cards); // Mélange le deck
    }

    private void shuffleDeck(){
        Collections.shuffle(cards);
    }

    public void addCard(Card card){
        cards.addFirst(card);
    }

    public Card drawCard() {
        if(numberCardsInDeck == 0){
            shuffleDeck();
            numberCardsInDeck = cards.size();
        }
        if (!cards.isEmpty()) {
            numberCardsInDeck--;
            return cards.remove(numberCardsInDeck); // Retire et renvoie la dernière carte
        } else {
            throw new IllegalStateException("No more cards available");
        }
    }

    public int getNumberCardsLeft(){
        System.out.println(numberCardsInDeck + ", " + cards.size() + "checkDeckSize");
        return numberCardsInDeck;
    }

    public int getNumberCardsTotal(){
        return cards.size();
    }

    public ArrayList<Card> getCards(){
        return cards;
    }
    public void setCards(ArrayList<Card> cards){
        cards = cards;
    }
}
