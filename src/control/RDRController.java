package control;

import boardifier.control.ActionFactory;
import boardifier.control.ActionPlayer;
import boardifier.control.Controller;
import boardifier.model.GameElement;
import boardifier.model.ContainerElement;
import boardifier.model.Model;
import boardifier.model.Player;
import boardifier.model.action.ActionList;
import boardifier.view.TextLook;
import boardifier.view.View;
import model.Pawn;
import model.RDRBoard;
import model.RDRStageModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class RDRController extends Controller {

    BufferedReader consoleIn;
    boolean firstPlayer;

    public RDRController(Model model, View view) {
        super(model, view);
        firstPlayer = true;
    }

    /**
     * Defines what to do within the single stage of the single party
     * It is pretty straight forward to write :
     */
    public void stageLoop() {
        consoleIn = new BufferedReader(new InputStreamReader(System.in));
        update();
        //Test de la fonction de déplacement sur le pion déplacable (Pion Roi);
        //analyseAndMove(xy);
        analyseAndMove("N", 3);
        AfficherPos();
        update();
        analyseAndMove("N", 1);

        //use update() to update all the changes and also in the view
        update();

        //bloquer la boucle en mettant en comm les fonctions
        /*
        while(! model.isEndStage()) {
            //playTurn();
            //endOfTurn();
            update();
        }
        //endGame();
         */

    }

    /*
    private void playTurn() {
        // get the new player
        Player p = model.getCurrentPlayer();
        if (p.getType() == Player.COMPUTER) {
            System.out.println("COMPUTER PLAYS");
            RDRDecider decider = new RDRDecider(model,this);
            ActionPlayer play = new ActionPlayer(model, this, decider, null);
            play.start();
        }
        else {
            boolean ok = false;
            while (!ok) {
                System.out.print(p.getName()+ " > ");
                try {
                    String line = consoleIn.readLine();
                    if (line.length() == 3) {
                        //ok = analyseAndPlay(line);
                    }
                    if (!ok) {
                        System.out.println("incorrect instruction. retry !");
                    }
                }
                catch(IOException e) {}
            }
        }
    }

    public void endOfTurn() {

        model.setNextPlayer();
        // get the new player to display its name
        Player p = model.getCurrentPlayer();
        RDRStageModel stageModel = (RDRStageModel) model.getGameStage();
        stageModel.getPlayerName().setText(p.getName());
    }
     */

    /*
    * as the name says, it analyse and play the String line (11 or 23...)
    */
    private boolean analyseAndMove(String direction, int move) {
        RDRStageModel gameStage = (RDRStageModel) model.getGameStage();

        // Get the new row and column from getMoveFromDirection
        int[] newPosition = getMoveFromDirection(direction, move);
        if (newPosition == null) {
            return false;
        }
        int row = newPosition[0];
        int col = newPosition[1];

        // check if the pawn is still in its pot
        ContainerElement pot = null;
        GameElement pawn = gameStage.getKingPawn();

        // compute valid cells for the chosen pawn
        if (!gameStage.getBoard().canReachCell(row, col)) return false;

        ActionList actions = ActionFactory.generatePutInContainer(model, pawn, "RDRBoard", row, col);
        actions.setDoEndOfTurn(true); // after playing this action list, it will be the end of turn for current player.
        ActionPlayer play = new ActionPlayer(model, this, actions);
        play.start();
        return true;
    }


    public boolean placePawn(){
        RDRStageModel gameStage = (RDRStageModel) model.getGameStage();
        GameElement pawn = gameStage.getKingPawn();
        int colPawn = (int) pawn.getX();
        int rowPawn = (int) pawn.getY();
        System.out.println("Col : " + colPawn + ", Row : " + rowPawn);
        return true;
    }

    public void AfficherPos(){
        RDRStageModel gameStage = (RDRStageModel) model.getGameStage();
        GameElement pawn = gameStage.getKingPawn();
        int[] currentPosition = ((Pawn) pawn).getPosition();
        int currentRow = currentPosition[0];
        int currentCol = currentPosition[1];
        System.out.println("CurrentRow : " + currentRow + ", CurrentCol : " + currentCol);
    }

    public int[] getMoveFromDirection(String direction, int move) {
        // Get the current position of the KingPawn
        AfficherPos();
        RDRStageModel gameStage = (RDRStageModel) model.getGameStage();
        GameElement pawn = gameStage.getKingPawn();
        int[] currentPosition = ((Pawn) pawn).getPosition();
        int currentRow = currentPosition[0];
        int currentCol = currentPosition[1];

        int newRow = currentRow;
        int newCol = currentCol;

        switch (direction) {
            case "N":
                newRow -= move;
                break;
            case "NW":
                newRow -= move;
                newCol -= move;
                break;
            case "NE":
                newRow -= move;
                newCol += move;
                break;
            case "S":
                newRow += move;
                break;
            case "SE":
                newRow += move;
                newCol += move;
                break;
            case "SW":
                newRow += move;
                newCol -= move;
                break;
            case "W":
                newCol -= move;
                break;
            case "E":
                newCol += move;
                break;
            default:
                System.out.println("Invalid direction");
                return null;
        }

        // Check if the new position is within the board limits
        if (newRow >= 0 && newRow <= 8 && newCol >= 0 && newCol <= 8) {
            return new int[] { newRow, newCol };
        } else {
            System.out.println("New position is out of bounds");
            return null;
        }
    }

}
