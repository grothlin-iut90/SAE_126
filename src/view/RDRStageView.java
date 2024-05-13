package view;

import boardifier.control.ActionFactory;
import boardifier.control.ActionPlayer;
import boardifier.model.GameStageModel;
import boardifier.model.action.ActionList;
import boardifier.view.GameStageView;
import boardifier.control.Logger;
import boardifier.view.ClassicBoardLook;
import boardifier.view.ContainerLook;

import boardifier.view.TextLook;
import model.Pawn;
import model.RDRStageModel;

import java.io.Console;

public class RDRStageView extends GameStageView {
    public RDRStageView(String name, GameStageModel gameStageModel) {
        super(name, gameStageModel);
    }

    public void createLooks(){
        RDRStageModel model = (RDRStageModel)gameStageModel;
        addLook(new TextLook(model.getPlayerName()));
        // create a ClassicBoardLook (with borders and coordinates) for the main board.
        addLook(new ClassicBoardLook(2, 4, model.getBoard(), 1, 1, true));

        addLook(new PawnLook(model.getKingPawn()));

        // create looks for both pots
        addLook(new BluePawnPotLook(model.getBluePot()));
        addLook(new RedPawnPotLook(model.getRedPot()));
        // create looks for all pawns
        for(int i=0;i<26;i++) {
            addLook(new PawnLook(model.getBluePawns()[i]));
            addLook(new PawnLook(model.getRedPawns()[i]));
        }


        /* need to show the (color) number of pawn remaining in the lot, already done but in system.out.print need to be done with real view
        addLook(new TextLook(model.getBluePawnsRemaining()));
        System.out.println("nbr bleu:"+ model.getBluePawnsRemaining());
        System.out.println("nbr rouge:"+ model.getRedPawnsRemaining());
         */
        Logger.debug("finished creating game stage looks", this);
    }
}
