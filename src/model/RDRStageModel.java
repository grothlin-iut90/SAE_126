package model;

import boardifier.control.ActionFactory;
import boardifier.model.*;
import boardifier.model.action.ActionList;

public class RDRStageModel extends GameStageModel{

    // define stage state variables
    private int bluePawnsToPlay;
    private int redPawnsToPlay;

    // define stage game elements
    private RDRBoard board;
    private RDRPawnPot bluePot;
    private RDRPawnPot redPot;
    private Pawn[] bluePawns;
    private Pawn[] redPawns;
    private Pawn kingPawn;
    private TextElement playerName;
    // Uncomment next line if the example with a main container is used. see end of HoleStageFactory and HoleStageView
    //private ContainerElement mainContainer;

    public RDRStageModel(String name, Model model) {
        super(name, model);
        bluePawnsToPlay = 26;
        redPawnsToPlay = 26;
        setupCallbacks();
    }

    //Uncomment this 2 methods if example with a main container is used
    /*
    public ContainerElement getMainContainer() {
        return mainContainer;
    }

    public void setMainContainer(ContainerElement mainContainer) {
        this.mainContainer = mainContainer;
        addContainer(mainContainer);
    }
     */

    public RDRBoard getBoard() {
        return board;
    }
    public void setBoard(RDRBoard board) {
        this.board = board;
        addContainer(board);
    }

    public ContainerElement getContainer(GameElement element) {
        for (ContainerElement container : getContainers()) {
            if (container.contains(element)) {
                return container;
            }
        }
        return null;
    }

    public RDRPawnPot getBluePot() {
        return bluePot;
    }
    public void setBluePot(RDRPawnPot bluePot) {

        this.bluePot = bluePot;
        addContainer(bluePot);
    }

    public RDRPawnPot getRedPot() {
        return redPot;
    }
    public void setRedPot(RDRPawnPot redPot) {
        this.redPot = redPot;
        addContainer(redPot);
    }

    public Pawn[] getBluePawns() {
        return bluePawns;
    }

    public int getBluePawnsRemaining() {
        return bluePawns.length;
    }


    public void setBluePawns(Pawn[] bluePawns) {
        this.bluePawns = bluePawns;
        for(int i=0;i<bluePawns.length;i++) {
            addElement(bluePawns[i]);
        }
    }

    public Pawn[] getRedPawns() {
        return redPawns;
    }

    public int getRedPawnsRemaining(){
        return redPawns.length;
    }
    public void setRedPawns(Pawn[] redPawns) {
        this.redPawns = redPawns;
        for(int i=0;i<redPawns.length;i++) {
            addElement(redPawns[i]);
        }
    }

    public Pawn getKingPawn() {
        return kingPawn;
    }
    public void setKingPawn(Pawn kingPawn) {
        this.kingPawn = kingPawn;
        addElement(kingPawn);
    }

    public TextElement getPlayerName() {
        return playerName;
    }
    public void setPlayerName(TextElement playerName) {
        this.playerName = playerName;
        addElement(playerName);
    }

    private void setupCallbacks() {
        /* function that check if both players can't play and if true then use computePartyResult to check the winner

        onPutInContainer( (element, gridDest, rowDest, colDest) -> {
            // just check when pawns are put in 3x3 board
            if (gridDest != board) return;
            Pawn p = (Pawn) element;
            if (p.getColor() == 0) {
                blackPawnsToPlay--;
            }
            else {
                redPawnsToPlay--;
            }
            if ((blackPawnsToPlay == 0) && (redPawnsToPlay == 0)) {
                computePartyResult();
            }
        });
         */
    }

    // function to know the result (need to change everything)
    private void computePartyResult() {
        /*
        int idWinner = -1;
        // get the empty cell, which should be in 2D at [0,0], [0,2], [1,1], [2,0] or [2,2]
        // i.e. or in 1D at index 0, 2, 4, 6 or 8
        int i = 0;
        int nbBlue = 0;
        int nbRed = 0;
        int countBlue = 0;
        int countRed = 0;
        Pawn p = null;
        int row, col;
        for (i = 0; i < 9; i+=2) {
            if (board.isEmptyAt(i / 3, i % 3)) break;
        }
        // get the 4 adjacent cells (if they exist) starting by the upper one
        row = (i / 3) - 1;
        col = i % 3;
        for (int j = 0; j < 4; j++) {
            // skip invalid cells
            if ((row >= 0) && (row <= 2) && (col >= 0) && (col <= 2)) {
                p = (Pawn) (board.getElement(row, col));
                if (p.getColor() == Pawn.PAWN_BLUE) {
                    nbBlue++;
                    countBlue += p.getNumber();
                } else {
                    nbRed++;
                    countRed += p.getNumber();
                }
            }
            // change row & col to set them at the correct value for the next iteration
            if ((j==0) || (j==2)) {
                row++;
                col--;
            }
            else if (j==1) {
                col += 2;
            }
        }

        // decide whose winning
        if (nbBlue < nbRed) {
            idWinner = 0;
        }
        else if (nbBlue > nbRed) {
            idWinner = 1;
        }
        else {
            if (countBlue < countRed) {
                idWinner = 0;
            }
            else if (countBlue > countRed) {
                idWinner = 1;
            }
        }
        System.out.println("nb blue: "+nbBlue+", nb red: "+nbRed+", count blue: "+countBlue+", count red: "+countRed+", winner is player "+idWinner);
        // set the winner
        model.setIdWinner(idWinner);
        // stop de the game
        model.stopStage();
         */
    }


    @Override
    public StageElementsFactory getDefaultElementFactory() {
        return new RDRStageFactory(this);
    }
}
