package model;

import boardifier.model.GameStageModel;
import boardifier.model.Model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HeroCardTest {

    private HeroCard heroCardBlue;
    private HeroCard heroCardRed;
    private GameStageModel gameStageModel;

    @BeforeEach
    void setUp() {
        Model model = new Model(); // Créez une instance de Model
        gameStageModel = new RDRStageModel("stageName", model); // Initialisez RDRStageModel avec les arguments requis
        heroCardBlue = new HeroCard(5, HeroCard.HERO_CARD_BLUE, gameStageModel);
        heroCardRed = new HeroCard(3, HeroCard.HERO_CARD_RED, gameStageModel);
    }

    @Test
    void getNumber() {
        assertEquals(5, heroCardBlue.getNumber(), "HeroCard number should be 5");
        assertEquals(3, heroCardRed.getNumber(), "HeroCard number should be 3");
    }

    @Test
    void getColor() {
        assertEquals(HeroCard.HERO_CARD_BLUE, heroCardBlue.getColor(), "HeroCard color should be HERO_CARD_BLUE");
        assertEquals(HeroCard.HERO_CARD_RED, heroCardRed.getColor(), "HeroCard color should be HERO_CARD_RED");
    }
}
