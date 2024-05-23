package model;

import boardifier.model.ContainerElement;
import boardifier.model.GameElement;
import boardifier.model.Model;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RDRStageModelTest {

    Model model = new Model(); // Créez une instance de Model
    RDRStageModel stageModel = new RDRStageModel("stageName", model); // Initialisez RDRStageModel avec les arguments requis

    @Test
    void getBoard() {
        assertNotNull(stageModel.getBoard());
    }

    @Test
    void setBoard() {
        RDRBoard newBoard = mock(RDRBoard.class);
        stageModel.setBoard(newBoard);
        assertEquals(newBoard, stageModel.getBoard());
    }

    @Test
    void getBluePawnsToPlay() {
        assertEquals(26, stageModel.getBluePawnsToPlay());
    }

    @Test
    void reduceBluePawnToPlay() {
        int initialCount = stageModel.getBluePawnsToPlay();
        stageModel.reduceBluePawnToPlay();
        assertEquals(initialCount - 1, stageModel.getBluePawnsToPlay());
    }

    @Test
    void getContainer() {
        // Création d'un objet simulé pour GameElement
        GameElement element = mock(GameElement.class);

        // Ajout de l'élément simulé au modèle
        stageModel.addElement(element);

        // Obtention du conteneur pour l'élément simulé
        ContainerElement container = stageModel.getContainer(element);

        // Vérifications
        assertNotNull(container);
        for(int i = 0; i < 8; i++){
            for(int k = 0; k < 8; k++){
                assertTrue(container.getElements(i,k).contains(element));
            }
        }
    }


    @Test
    void getNumberOfCard() {
        assertEquals(5, stageModel.getNumberOfCard());
    }

    @Test
    void getCardDeck() {
        assertNotNull(stageModel.getCardDeck());
    }

    @Test
    void getPlayerCardHand() {
        assertNotNull(stageModel.getPlayerCardHand(1));
        assertNotNull(stageModel.getPlayerCardHand(2));
    }

    @Test
    void getCards() {
        assertNotNull(stageModel.getCards(1));
        assertNotNull(stageModel.getCards(2));
    }

    @Test
    void getBluePot() {
        assertNotNull(stageModel.getBluePot());
    }

    @Test
    void getRedPawnsRemaining() {
        assertEquals(26, stageModel.getRedPawnsRemaining());
    }

    @Test
    void useHeroCard() {
        int initialBlueHeroCards = stageModel.getHeroCardPlayer1().length;
        stageModel.useHeroCard(0);
        assertEquals(initialBlueHeroCards - 1, stageModel.getHeroCardPlayer1().length);
    }

    @Test
    void computePartyResult() {
        // Configuration initiale du plateau de jeu
        RDRBoard board = new RDRBoard(9,9,stageModel);
        stageModel.setBoard(board);

        // Ajout de pions sur le plateau
        // Supposons que nous avons une disposition simple où le joueur bleu contrôle une zone de 3x3
        // et le joueur rouge contrôle une zone de 2x2
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                Pawn bluePawn = new Pawn(1, Pawn.PAWN_BLUE, stageModel);
                board.addElement(bluePawn, row, col);
                stageModel.addElement(bluePawn);
            }
        }
        for (int row = 0; row < 2; row++) {
            for (int col = 3; col < 5; col++) {
                Pawn redPawn = new Pawn(1, Pawn.PAWN_RED, stageModel);
                board.addElement(redPawn, row, col);
                stageModel.addElement(redPawn);
            }
        }

        // Calcul du résultat de la partie
        stageModel.computePartyResult();

        // Vérification du résultat
        // Le score du joueur bleu devrait être 9 (3*3) et celui du joueur rouge 4 (2*2)
        // Nous vérifions ici uniquement le message de sortie, car c'est probablement comment vous affichez le résultat
        // Vous devrez peut-être ajuster cette partie en fonction de la manière dont vous gérez les logs ou les sorties dans votre application
        verify(System.out).println("Blue Player Score: 9");
        verify(System.out).println("Red Player Score: 4");
        verify(System.out).println("Blue Player Wins!");
    }

}
