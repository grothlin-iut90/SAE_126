package model;

import boardifier.model.ContainerElement;
import boardifier.model.GameStageModel;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CardDeck extends ContainerElement {

    private List<int[]> possiblePairs;

    public CardDeck(int x, int y, GameStageModel gameStageModel){
        super("cardDeck", x, y, 1, 1, gameStageModel);
        initializePossiblePairs();
    }

    private void initializePossiblePairs() {
        possiblePairs = new ArrayList<>();
        for (int direction = 0; direction <= 7; direction++) {
            for (int value = 1; value <= 3; value++) {
                possiblePairs.add(new int[]{direction, value});
            }
        }
        Collections.shuffle(possiblePairs); // Mélange la liste pour avoir un ordre aléatoire
    }

    public int[] pickFromDeck() {
        if (!possiblePairs.isEmpty()) {
            return possiblePairs.remove(possiblePairs.size() - 1); // Retire et renvoie le dernier élément
        } else {
            throw new IllegalStateException("No more pairs available");
        }
    }

    public static void shuffleCardDeck(ArrayList<Card> cards){
        Card temp;
        int random;
        int length = cards.size();
        int max = length - 1;
        int min = 0;
        int range = max - min + 1;
        ArrayList<Integer> randomizedIndices = new ArrayList<Integer>();
        for(int i = 0; i < length; i++){
            random = (int) (Math.random() * range) + min;
            while(randomizedIndices.contains(random)) {
                random = (int) (Math.random() * range) + min;
            }
            temp = cards.get(i);
            cards.set(i, cards.get(random));
            cards.set(random, temp);
            randomizedIndices.add(random);
        }
    }
}
