package model;

import boardifier.model.ContainerElement;
import boardifier.model.GameStageModel;
import boardifier.model.Model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PawnTest {

    private Pawn bluePawn;
    private Pawn redPawn;
    private Pawn yellowPawn;
    private GameStageModel gameStageModel;

    @BeforeEach
    void setUp() {
        Model model = new Model(); // Créez une instance de Model
        gameStageModel = new RDRStageModel("stageName", model); // Initialisez RDRStageModel avec les arguments requis
        bluePawn = new Pawn(1, Pawn.PAWN_BLUE, gameStageModel);
        redPawn = new Pawn(2, Pawn.PAWN_RED, gameStageModel);
        yellowPawn = new Pawn(3, Pawn.PAWN_YELLOW, gameStageModel);
    }

    @Test
    void getColor() {
        assertEquals(Pawn.PAWN_BLUE, bluePawn.getColor(), "The color of bluePawn should be PAWN_BLUE");
        assertEquals(Pawn.PAWN_RED, redPawn.getColor(), "The color of redPawn should be PAWN_RED");
        assertEquals(Pawn.PAWN_YELLOW, yellowPawn.getColor(), "The color of yellowPawn should be PAWN_YELLOW");
    }

    @Test
    void getNumber() {
        assertEquals(1, bluePawn.getNumber(), "The number of bluePawn should be 1");
        assertEquals(2, redPawn.getNumber(), "The number of redPawn should be 2");
        assertEquals(3, yellowPawn.getNumber(), "The number of yellowPawn should be 3");
    }

    @Test
    void getPosition() {
        // Assuming RDRStageModel and ContainerElement are set up correctly
        // Here we just check that the method returns null since no containers are set in this simple test
        assertNull(bluePawn.getPosition(), "The position of bluePawn should be null");
    }

    @Test
    void getPlayerId() {
        assertEquals(0, bluePawn.getPlayerId(), "The player ID for bluePawn should be 0");
        assertEquals(1, redPawn.getPlayerId(), "The player ID for redPawn should be 1");
        assertEquals(2, yellowPawn.getPlayerId(), "The player ID for yellowPawn should be 2");
    }
}
