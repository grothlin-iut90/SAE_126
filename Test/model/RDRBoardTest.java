package model;

import boardifier.model.GameStageModel;
import boardifier.model.Model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Point;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RDRBoardTest {

    private RDRBoard rdrBoard;
    private GameStageModel gameStageModel;

    @BeforeEach
    void setUp() {
        Model model = new Model(); // Créez une instance de Model
        gameStageModel = new RDRStageModel("stageName", model); // Initialisez RDRStageModel avec les arguments requis
        rdrBoard = new RDRBoard(0, 0, gameStageModel);
    }

    @Test
    void getRows() {
        assertEquals(9, rdrBoard.getRows(), "The number of rows should be 9");
    }

    @Test
    void getCols() {
        assertEquals(9, rdrBoard.getCols(), "The number of columns should be 9");
    }

    @Test
    void computeValidCells_EmptyBoard() {
        List<Point> validCells = rdrBoard.computeValidCells(0);
        assertEquals(64, validCells.size(), "All cells should be valid on an empty board");
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                assertTrue(validCells.contains(new Point(j, i)), "Cell (" + j + ", " + i + ") should be valid");
            }
        }
    }

    @Test
    void computeValidCells_NonEmptyBoard() {
        // Placing a pawn to change the state of the board
        Pawn pawn = new Pawn(1, Pawn.PAWN_BLUE, gameStageModel);
        rdrBoard.addElement(pawn, 1, 1);

        List<Point> validCells = rdrBoard.computeValidCells(1);
        assertNotEquals(64, validCells.size(), "Not all cells should be valid when the board is not empty");

        // Add more assertions based on specific logic of computeValidCells
    }
}
