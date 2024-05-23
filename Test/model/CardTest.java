package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {

    @Test
    void getDirection() {
        Card card = new Card(Card.N, 3, null);
        assertEquals(Card.N, card.getDirection(), "Direction should be N (0)");

        card = new Card(Card.SE, 2, null);
        assertEquals(Card.SE, card.getDirection(), "Direction should be SE (6)");
    }

    @Test
    void getStringDirection() {
        Card card = new Card(Card.N, 3, null);
        assertEquals("N", card.getStringDirection(), "Direction string should be 'N'");

        card = new Card(Card.W, 2, null);
        assertEquals("W", card.getStringDirection(), "Direction string should be 'W'");

        card = new Card(Card.S, 5, null);
        assertEquals("S", card.getStringDirection(), "Direction string should be 'S'");

        card = new Card(Card.E, 1, null);
        assertEquals("E", card.getStringDirection(), "Direction string should be 'E'");

        card = new Card(Card.NW, 4, null);
        assertEquals("NW", card.getStringDirection(), "Direction string should be 'NW'");

        card = new Card(Card.SW, 3, null);
        assertEquals("SW", card.getStringDirection(), "Direction string should be 'SW'");

        card = new Card(Card.SE, 2, null);
        assertEquals("SE", card.getStringDirection(), "Direction string should be 'SE'");

        card = new Card(Card.NE, 6, null);
        assertEquals("NE", card.getStringDirection(), "Direction string should be 'NE'");
    }

    @Test
    void getCardSteps() {
        Card card = new Card(Card.N, 1, null);
        assertEquals(1, card.getCardSteps(), "Card steps should be 1");

        card = new Card(Card.SE, 5, null);
        assertEquals(3, card.getCardSteps(), "Card steps should be 3");
    }
}
