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
import model.*;

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
        //Test de la fonction de déplacement sur le pion déplacable (Pion Roi);
        //analyseAndMove(xy);
        //use update() to update all the changes and also in the view
        update();

        while(! model.isEndStage()) {
            playTurn();
            endOfTurn();
            update();
        }
        endGame();


    }


    private void playTurn() {
        // get the new player
        Player p = model.getCurrentPlayer();
        if (p.getType() == Player.COMPUTER) {
            System.out.println("COMPUTER PLAYS");
            RDRDecider decider = new RDRDecider(model, this);
            ActionPlayer play = new ActionPlayer(model, this, decider, null);
            play.start();
        } else {
            boolean ok = false;
            RDRStageModel gameStage = (RDRStageModel) model.getGameStage(); // Access the RDRStageModel instance
            RDRBoard board = gameStage.getBoard();
            while (!ok) {
                System.out.print("1. Move the King and place a Pawn | 2. Move the King and Use Hero Card | 3. Draw a Move Card > ");
                try {
                    String input = consoleIn.readLine();
                    int playmode = Integer.parseInt(input);
                    switch (playmode) {
                        case 1:
                            // 1ère action, déplace le roi et ne doit pas le ramener sur un pion ou en dehors du plateau et lorsque la carte peut être jouée, déposer un pion
                            System.out.print(p.getName() + " > ");
                            String reader = consoleIn.readLine();
                            int NumberCardPlayed = -1;

                            // Vérifier l'entrée utilisateur et redemander tant qu'elle n'est pas valide
                            while (true) {
                                try {
                                    NumberCardPlayed = Integer.parseInt(reader) - 1;
                                    int cardsLength = gameStage.getCards(model.getIdPlayer()).length;
                                    if (NumberCardPlayed >= 0 && NumberCardPlayed < cardsLength && gameStage.getCards(model.getIdPlayer())[NumberCardPlayed].isVisible()) {
                                        break; // sortie de la boucle si l'entrée est valide et la carte est visible
                                    } else {
                                        System.out.println("Please enter a valid card number between 0 and " + (cardsLength - 1) + " and ensure the card is visible.");
                                    }
                                } catch (NumberFormatException e) {
                                    System.out.println("Invalid input. Please enter a number.");
                                }
                                // Demander à nouveau une entrée utilisateur
                                System.out.print(p.getName() + " > ");
                                reader = consoleIn.readLine();
                            }

                            String Direction = gameStage.getCards(model.getIdPlayer())[NumberCardPlayed].getStringDirection();
                            int Move = gameStage.getCards(model.getIdPlayer())[NumberCardPlayed].getCardSteps();
                            ok = analyseAndMove(Direction, Move);
                            if (ok) {
                                // Retirer la carte de la main du joueur
                                Card cardToPlay = gameStage.getCards(model.getIdPlayer())[NumberCardPlayed];
                                PlayerCardHand playerHand = gameStage.getPlayerCardHand(model.getIdPlayer());
                                playerHand.removeCardFromPlayerHand(cardToPlay);

                                System.out.println("Card removed from hand. \nID player : " + model.getIdPlayer() + "\n Card removed : " + NumberCardPlayed);
                            } else {
                                System.out.println("incorrect instruction. retry !");
                            }
                            break;
                        case 2:
                            System.out.println("2ème action");
                            break;
                        case 3:
                            // Draw a move card from the deck
                            try {
                                if (gameStage.getCards(model.getIdPlayer()).length < 5) {
                                    int[] card = gameStage.getCardDeck().pickFromDeck();
                                    System.out.println("Drew a card: Direction = " + card[0] + ", Steps = " + card[1]);
                                } else {
                                    System.out.println("You can draw a card if you only have less than 5 cards.");
                                }
                            } catch (IllegalStateException e) {
                                System.out.println("No more cards available in the deck.");
                            }
                            break;
                        default:
                            System.out.println("Please enter a digit between 1 and 3 for your action");
                            break;
                    }
                } catch (IOException e) {
                    System.out.println("An error occurred. Please try again.");
                }
            }
        }
    }



    public void endOfTurn() {

        model.setNextPlayer();
        // get the new player to display its name
        Player p = model.getCurrentPlayer();
        RDRStageModel stageModel = (RDRStageModel) model.getGameStage();
        // stageModel.getPlayerName(1).setText(p.getName());
    }
    private boolean analyseAndMove(String direction, int move) {
        RDRStageModel gameStage = (RDRStageModel) model.getGameStage();
        RDRBoard board = gameStage.getBoard();

        // Get the new row and column from getMoveFromDirection
        int[] newPosition = getMoveFromDirection(direction, move);
        if (newPosition == null) {
            return false;
        }
        int row = newPosition[0];
        int col = newPosition[1];
        //System.out.println("col" + col + "row "+ row);
        if (!board.isEmptyAt(row,col)) return false;

        // check if the pawn is still in its pot
        //ContainerElement pot = null;
        GameElement pawn = gameStage.getKingPawn();

        // compute valid cells for the chosen pawn
        if (!gameStage.getBoard().canReachCell(row, col)) return false;

        // check the player id and select the pot pawn to player
        if(model.getIdPlayer() == 0){
            int bluepawnstoplay = gameStage.getBluePawnsToPlay();
            placePawn(gameStage.getBluePawns()[bluepawnstoplay-1], row, col);
            gameStage.reduceBluePawnToPlay();
            System.out.println("blue pawns remaining : " + gameStage.getBluePawnsRemaining());
        }else{
            int redpawnstoplay = gameStage.getRedPawnsToPlay();
            placePawn(gameStage.getRedPawns()[redpawnstoplay-1], row, col);
            gameStage.reduceRedPawnToPlay();
            System.out.println("red pawns remaining : " + gameStage.getRedPawnsRemaining());
        }

        ActionList actions = ActionFactory.generatePutInContainer(model, pawn, "RDRBoard", row, col);

        actions.setDoEndOfTurn(true); // after playing this action list, it will be the end of turn for current player.
        ActionPlayer play = new ActionPlayer(model, this, actions);
        play.start();
        return true;
    }



    public void placePawn(Pawn pawn, int row, int col) {
        RDRStageModel gameStage = (RDRStageModel) model.getGameStage();
        RDRBoard board = gameStage.getBoard();
        board.addElement(pawn, row, col);
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
            //System.out.println("NewRow : " + newRow + ", NewCol : " + newCol);
            System.out.println("New position is out of bounds");
            return null;
        }
    }

}
