package model;

import boardifier.model.GameStageModel;
import boardifier.model.Model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerCardHandTest {

    private PlayerCardHand playerCardHand;
    private GameStageModel gameStageModel;
    private Card card;

    @BeforeEach
    void setUp() {
        Model model = new Model(); // Créez une instance de Model
        gameStageModel = new RDRStageModel("stageName", model); // Initialisez RDRStageModel avec les arguments requis
        playerCardHand = new PlayerCardHand(0, 0, gameStageModel);
        card = new Card(Card.N, 1, gameStageModel); // Initialisez une carte pour les tests
    }

    @Test
    void addCardToPlayerHand() {
        playerCardHand.addCardToPlayerHand(card, 0);
        assertEquals(card, playerCardHand.getElement(0, 0), "The card should be in the player's hand at position (0, 0)");
    }

    @Test
    void removeCardFromPlayerHand() {
        playerCardHand.addCardToPlayerHand(card, 0);
        playerCardHand.removeCardFromPlayerHand(card);
        assertNull(playerCardHand.getElement(0, 0), "The card should not be in the player's hand at position (0, 0)");
        assertFalse(card.isVisible(), "The card should be invisible after being removed from the player's hand");
    }
}
