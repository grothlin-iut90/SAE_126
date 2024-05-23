package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RDRStageFactoryTest {

    private RDRStageModel stageModel;
    private RDRStageFactory factory;

    @BeforeEach
    void setUp() {
        stageModel = new RDRStageModel("test", null); // Remplacez null par une instance de Model si nécessaire
        factory = new RDRStageFactory(stageModel);
        factory.setup();
    }

    @Test
    void testSetup() {
        // Vérifiez si le nom du joueur 1 est correctement défini
        assertEquals("player1", stageModel.getPlayerName(1).getText());

        // Vérifiez si le nom du joueur 2 est correctement défini
        assertEquals("player2", stageModel.getPlayerName(2).getText());

        // Vérifiez si le nombre de cartes dans la main du joueur 1 est correct
        assertEquals(5, stageModel.getCards(1).length);

        // Vérifiez si le nombre de cartes dans la main du joueur 2 est correct
        assertEquals(5, stageModel.getCards(2).length);

        // Vérifiez si le nombre de pions bleus est correct
        assertEquals(26, stageModel.getBluePawns().length);

        // Vérifiez si le nombre de pions rouges est correct
        assertEquals(26, stageModel.getRedPawns().length);

        // Vérifiez si le nombre de cartes héroïques pour le joueur 1 est correct
        assertEquals(4, stageModel.getHeroCardPlayer1().length);

        // Vérifiez si le nombre de cartes héroïques pour le joueur 2 est correct
        assertEquals(4, stageModel.getHeroCardPlayer2().length);
    }
}
