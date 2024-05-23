package control;

import boardifier.model.Model;
import boardifier.model.Player;
import boardifier.view.View;
import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RDRControllerTest {

    private RDRController controller;
    private Model model;
    private View view;
    private RDRStageModel gameStage;
    private RDRBoard board;
    private CardDeck cardDeck;

    @BeforeEach
    void setUp() {
        model = mock(Model.class);
        view = mock(View.class);
        controller = new RDRController(model, view);
        controller.cardDeck = mock(CardDeck.class);

        gameStage = mock(RDRStageModel.class);
        board = mock(RDRBoard.class);

        when(model.getGameStage()).thenReturn(gameStage);
        when(gameStage.getBoard()).thenReturn(board);
    }

    @Test
    void stageLoop() {
        when(model.isEndStage()).thenReturn(false, true);
        doNothing().when(controller).playTurn();
        doNothing().when(controller).endOfTurn();
        doNothing().when(controller).update();

        controller.stageLoop();

        verify(controller, times(1)).playTurn();
        verify(controller, times(1)).endOfTurn();
        verify(controller, times(2)).update();
    }

    @Test
    void handleMoveKingAndUseHeroCard() {
        Player player = mock(Player.class);
        when(model.getCurrentPlayer()).thenReturn(player);
        when(player.getName()).thenReturn("Player 1");

        when(gameStage.getCards(anyInt())).thenReturn(new Card[]{
                new Card(1, 1, gameStage),
                new Card( 2, 2, gameStage),
                new Card(3, 3, gameStage),
                new Card(4, 1, gameStage),
                new Card(5, 2, gameStage)
        });

        controller.consoleIn = new BufferedReader(new InputStreamReader(new ByteArrayInputStream("1".getBytes())));
        boolean result = controller.handleMoveKingAndUseHeroCard(player, gameStage);

        assertTrue(result);
    }

    @Test
    void useHeroCard() {
        when(gameStage.canUseHeroCard(anyInt())).thenReturn(true);
        Pawn opponentPawn = mock(Pawn.class);
        when(board.isEmptyAt(anyInt(), anyInt())).thenReturn(false);
        when(board.getElements(anyInt(), anyInt())).thenReturn(List.of(opponentPawn));
        when(opponentPawn.getPlayerId()).thenReturn(1);

        boolean result = controller.useHeroCard(0, "N", 2);

        assertTrue(result);
        verify(board).removeElement(opponentPawn);
    }

    @Test
    void endOfTurn() {
        Player player = mock(Player.class);
        when(model.getCurrentPlayer()).thenReturn(player);
        when(player.getName()).thenReturn("Player 1");

        controller.endOfTurn();

        verify(model).setNextPlayer();
    }

    @Test
    void drawAndAddCard() {
        PlayerCardHand playerHand = mock(PlayerCardHand.class);
        Card card = new Card(1, 1, gameStage);

        when(controller.cardDeck.drawCard()).thenReturn(card);

        controller.drawAndAddCard(playerHand);

        verify(playerHand).addCardToPlayerHand(card, 1);
    }

    @Test
    void placePawn() {
        Pawn pawn = mock(Pawn.class);

        controller.placePawn(pawn, 3, 3);

        verify(board).addElement(pawn, 3, 3);
    }

    @Test
    void afficherPos() {
        Pawn kingPawn = mock(Pawn.class);
        when(gameStage.getKingPawn()).thenReturn(kingPawn);
        when(kingPawn.getPosition()).thenReturn(new int[]{4, 4});

        controller.AfficherPos();

        // This method uses System.out, so to fully test it, we'd need to capture the output stream
    }

    @Test
    void getMoveFromDirection() {
        Pawn kingPawn = mock(Pawn.class);
        when(gameStage.getKingPawn()).thenReturn(kingPawn);
        when(kingPawn.getPosition()).thenReturn(new int[]{4, 4});

        int[] newPosition = controller.getMoveFromDirection("N", 2);

        assertArrayEquals(new int[]{2, 4}, newPosition);
    }

    @Test
    void handleMoveKingAndPlacePawn() {
        Player player = mock(Player.class);
        when(model.getCurrentPlayer()).thenReturn(player);
        when(player.getName()).thenReturn("Player 1");

        when(gameStage.getCards(anyInt())).thenReturn(new Card[]{
                new Card(1, 1, gameStage),
                new Card( 2, 2, gameStage),
                new Card(3, 3, gameStage),
                new Card(4, 1, gameStage),
                new Card(5, 2, gameStage)
        });

        controller.consoleIn = new BufferedReader(new InputStreamReader(new ByteArrayInputStream("1".getBytes())));
        boolean result = controller.handleMoveKingAndPlacePawn(player, gameStage);

        assertTrue(result);
    }

    @Test
    void handleDrawMoveCard() {
        PlayerCardHand playerHand = mock(PlayerCardHand.class);
        when(gameStage.getPlayerCardHand(anyInt())).thenReturn(playerHand);
        when(gameStage.getCards(anyInt())).thenReturn(new Card[4]);

        controller.handleDrawMoveCard(gameStage);

        verify(controller.cardDeck).drawCard();
    }

    @Test
    void MoveKingPawn() {
        when(board.isEmptyAt(anyInt(), anyInt())).thenReturn(true);
        when(board.canReachCell(anyInt(), anyInt())).thenReturn(true);

        boolean result = controller.MoveKingPawn("N", 2);

        assertTrue(result);
    }

    @Test
    void initializeRedPawn() {
        when(gameStage.getRedPawnsToPlay()).thenReturn(3);
        Pawn redPawn = mock(Pawn.class);
        when(gameStage.getRedPawns()).thenReturn(new Pawn[]{redPawn});

        controller.initializeRedPawn();

        verify(board).addElement(redPawn, 4, 5);
    }

    @Test
    void initializeBluePawn() {
        when(gameStage.getBluePawnsToPlay()).thenReturn(3);
        Pawn bluePawn = mock(Pawn.class);
        when(gameStage.getBluePawns()).thenReturn(new Pawn[]{bluePawn, bluePawn, bluePawn});

        controller.initializeBluePawn();

        verify(board).addElement(bluePawn, 3, 5);
        verify(board).addElement(bluePawn, 2, 5);
        verify(board).addElement(bluePawn, 1, 5);
    }
}
