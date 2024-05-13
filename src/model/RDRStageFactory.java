package model;

import boardifier.control.ActionFactory;
import boardifier.model.ContainerElement;
import boardifier.model.GameStageModel;
import boardifier.model.StageElementsFactory;
import boardifier.model.TextElement;
import boardifier.model.action.ActionList;
import boardifier.model.action.GameAction;
import boardifier.model.action.PutInContainerAction;

public class RDRStageFactory extends StageElementsFactory{
    private RDRStageModel stageModel;

    public RDRStageFactory(GameStageModel gameStageModel) {
        super(gameStageModel);
        stageModel = (RDRStageModel) gameStageModel;
    }

    public void setup(){
        // create the text that displays the player name and put it in 0,0 in the virtual space
        TextElement text = new TextElement(stageModel.getCurrentPlayerName(), stageModel);
        text.setLocation(0,0);
        stageModel.setPlayerName(text);

        // create the board, in 0,1 in the virtual space
        RDRBoard board = new RDRBoard(0, 1, stageModel);
        // assign the board to the game stage model
        stageModel.setBoard(board);

        //create the king pawn
        Pawn kingPawn = new Pawn(Pawn.PAWN_YELLOW, stageModel);
        stageModel.setKingPawn(kingPawn);
        board.addElement(kingPawn, 4, 4);


        //create the black pot in 18,0 in the virtual space
        RDRPawnPot bluePot = new RDRPawnPot(40,10, stageModel);
        // assign the black pot to the game stage model
        stageModel.setBluePot(bluePot);
        //create the black pot in 25,0 in the virtual space
        RDRPawnPot redPot = new RDRPawnPot(40,2, stageModel);
        // assign the red pot to the game stage model
        stageModel.setRedPot(redPot);


        /* create the pawns
            NB: their coordinates are by default 0,0 but since they are put
            within the pots, their real coordinates will be computed by the view
         */

        Pawn[] bluePawns = new Pawn[26];
        for(int i=0;i<26;i++) {
            bluePawns[i] = new Pawn(Pawn.PAWN_BLUE, stageModel);
        }
        // assign the black pawns to the game stage model
        stageModel.setBluePawns(bluePawns);
        Pawn[] redPawns = new Pawn[26];
        for(int i=0;i<26;i++) {
            redPawns[i] = new Pawn(Pawn.PAWN_RED, stageModel);
        }
        // assign the black pawns to the game stage model
        stageModel.setRedPawns(redPawns);


        // finally put the pawns to their pot
        for (int i=0;i<26;i++) {
            bluePot.addElement(bluePawns[i], 0,i);
            redPot.addElement(redPawns[i], 0,i);
        }
    }
}
