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
import java.util.List;

public class RDRController extends Controller {

    BufferedReader consoleIn;
    private CardDeck cardDeck;
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
        //used to test useHeroCard
        initializeRedPawn();
        initializeBluePawn();
        //use update() to update all the changes and also in the view
        update();
        while(! model.isEndStage()) {
            playTurn();
            endOfTurn();
            update();
        }
        endGame();
    }
    private void initializeRedPawn() {
        RDRStageModel gameStage = (RDRStageModel) model.getGameStage();
        int row = 4;
        int col = 5;

        // Check if the position is empty
        if (gameStage.getBoard().isEmptyAt(row, col)) {
            // Get the next red pawn to play
            int redPawnsToPlay = gameStage.getRedPawnsToPlay();
            if (redPawnsToPlay > 0) {
                // Place the red pawn at the specified position
                placePawn(gameStage.getRedPawns()[redPawnsToPlay - 1], row, col);
                // Reduce the number of red pawns to play
                gameStage.reduceRedPawnToPlay();
                System.out.println("Red pawn placed at row " + row + ", col " + col);
            } else {
                System.out.println("No red pawns left to place.");
            }
        } else {
            System.out.println("Position (" + row + ", " + col + ") is not empty.");
        }
    }
    private void initializeBluePawn() {
        RDRStageModel gameStage = (RDRStageModel) model.getGameStage();
        int row = 3;
        int col = 5;

        // Check if the position is empty
        if (gameStage.getBoard().isEmptyAt(row, col)) {
            // Get the next red pawn to play
            int bluePawnsToPlay = gameStage.getBluePawnsToPlay();
            if (bluePawnsToPlay > 0) {
                // Place the red pawn at the specified position
                placePawn(gameStage.getBluePawns()[bluePawnsToPlay - 1], row, col);
                // Reduce the number of red pawns to play
                gameStage.reduceBluePawnToPlay();
                System.out.println("Red pawn placed at row " + row + ", col " + col);
            } else {
                System.out.println("No red pawns left to place.");
            }
        } else {
            System.out.println("Position (" + row + ", " + col + ") is not empty.");
        }
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
                            ok = handleMoveKingAndPlacePawn(p, gameStage);
                            break;

                        case 2:
                            ok = handleMoveKingAndUseHeroCard(p, gameStage);
                            break;

                        case 3:
                            handleDrawMoveCard(gameStage);
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

    private boolean handleMoveKingAndPlacePawn(Player p, RDRStageModel gameStage) {
        System.out.print(p.getName() + " > ");
        int NumberCardPlayed = getValidatedCardNumber(gameStage, p);
        if (NumberCardPlayed == -1) {
            return false;
        }

        String direction = gameStage.getCards(model.getIdPlayer())[NumberCardPlayed].getStringDirection();
        int move = gameStage.getCards(model.getIdPlayer())[NumberCardPlayed].getCardSteps();
        boolean moveSuccessful = MoveKingPawn(direction, move);
        if (moveSuccessful) {
            removeCardFromHand(gameStage, NumberCardPlayed);
        } else {
            System.out.println("Incorrect instruction. Retry!");
        }
        return moveSuccessful;
    }
    public boolean handleMoveKingAndUseHeroCard(Player p, RDRStageModel gameStage){
        System.out.print(p.getName() + " > ");
        int NumberCardPlayed = getValidatedCardNumber(gameStage, p);
        if (NumberCardPlayed == -1) {
            return false;
        }

        String direction = gameStage.getCards(model.getIdPlayer())[NumberCardPlayed].getStringDirection();
        int move = gameStage.getCards(model.getIdPlayer())[NumberCardPlayed].getCardSteps();
        boolean moveSuccessful = useHeroCard(model.getIdPlayer(), direction, move);
        if (moveSuccessful) {
            removeCardFromHand(gameStage, NumberCardPlayed);
        } else {
            System.out.println("Incorrect instruction. Retry!");
        }
        return moveSuccessful;
    }
    private int getValidatedCardNumber(RDRStageModel gameStage, Player p) {
        while (true) {
            try {
                String reader = consoleIn.readLine();
                int numberCardPlayed = Integer.parseInt(reader) - 1;
                int cardsLength = gameStage.getCards(model.getIdPlayer()).length;
                if (numberCardPlayed >= 0 && numberCardPlayed < cardsLength && gameStage.getCards(model.getIdPlayer())[numberCardPlayed].isVisible()) {
                    return numberCardPlayed;
                } else {
                    System.out.println("Please enter a valid card number between 0 and " + (cardsLength - 1) + " and ensure the card is visible.");
                }
            } catch (NumberFormatException | IOException e) {
                System.out.println("Invalid input. Please enter a number.");
                System.out.print(p.getName() + " > ");
            }
        }
    }

    private void removeCardFromHand(RDRStageModel gameStage, int numberCardPlayed) {
        Card cardToPlay = gameStage.getCards(model.getIdPlayer())[numberCardPlayed];
        CardDeck deck = gameStage.getCardDeck();
        deck.addElement(cardToPlay, 0, 0);
        //PlayerCardHand playerHand = gameStage.getPlayerCardHand(model.getIdPlayer());
        //playerHand.removeCardFromPlayerHand(cardToPlay);
        System.out.println("Card removed from hand.");
        for (int i = 0; i < 5; i++) {
            System.out.println(gameStage.getCards(model.getIdPlayer())[i]);
        }
    }

    public boolean useHeroCard(int idPlayer, String direction, int move) {
        RDRStageModel gameStage = (RDRStageModel) model.getGameStage();
        RDRBoard board = gameStage.getBoard();
        Player currentPlayer = model.getCurrentPlayer();
        GameElement kingPawn = gameStage.getKingPawn();

        if (!gameStage.canUseHeroCard(idPlayer)) {
            System.out.println("Player " + idPlayer + " has no hero cards left.");
            return false;
        }

        int[] newPosition = getMoveFromDirection(direction, move);

        int newRow = newPosition[0];
        int newCol = newPosition[1];

        if (board.isEmptyAt(newRow, newCol)) {
            System.out.println("No opponent's pawn at the new position.");
            return false;
        } else {
            List<GameElement> elementsAtNewPosition = board.getElements(newRow, newCol);
            for (GameElement element : elementsAtNewPosition) {
                if (element instanceof Pawn) {
                    Pawn opponentPawnCast = (Pawn) element;
                    if (opponentPawnCast.getPlayerId() != idPlayer) {
                        opponentPawnCast.setVisible(false);
                        board.removeElement(opponentPawnCast);
                        System.out.println("Removed opponent's pawn at the new position.");

                        if (idPlayer == 0) {
                            int bluePawnsToPlay = gameStage.getBluePawnsToPlay();
                            placePawn(gameStage.getBluePawns()[bluePawnsToPlay - 1], newRow, newCol);
                            gameStage.reduceBluePawnToPlay();
                        } else {
                            int redPawnsToPlay = gameStage.getRedPawnsToPlay();
                            placePawn(gameStage.getRedPawns()[redPawnsToPlay - 1], newRow, newCol);
                            gameStage.reduceRedPawnToPlay();
                        }

                        gameStage.useHeroCard(idPlayer);

                        ActionList actions = ActionFactory.generatePutInContainer(model, kingPawn, "RDRBoard", newRow, newCol);
                        actions.setDoEndOfTurn(true);
                        ActionPlayer play = new ActionPlayer(model, this, actions);
                        play.start();
                        return true;
                    }
                }
            }
            System.out.println("No opponent's pawn found at the new position.");
        }
        return false;
    }




    private void handleDrawMoveCard(RDRStageModel gameStage) {
        try {
            if (gameStage.getCards(model.getIdPlayer()).length < 5) {
                drawAndAddCard(gameStage.getPlayerCardHand(model.getIdPlayer()));
            } else {
                System.out.println("You only can draw a card if you have less than 5 cards.");
            }
        } catch (IllegalStateException e) {
            System.out.println("No more cards available in the deck.");
        }
    }

    public void endOfTurn() {

        model.setNextPlayer();
        // get the new player to display its name
        Player p = model.getCurrentPlayer();
        RDRStageModel stageModel = (RDRStageModel) model.getGameStage();
        //stageModel.getPlayerName(1).setText(p.getName());
    }
    private boolean MoveKingPawn(String direction, int move) {
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
            //System.out.println("blue pawns remaining : " + gameStage.getBluePawnsRemaining());
        }else{
            int redpawnstoplay = gameStage.getRedPawnsToPlay();
            placePawn(gameStage.getRedPawns()[redpawnstoplay-1], row, col);
            gameStage.reduceRedPawnToPlay();
            //System.out.println("red pawns remaining : " + gameStage.getRedPawnsRemaining());
        }

        ActionList actions = ActionFactory.generatePutInContainer(model, pawn, "RDRBoard", row, col);

        actions.setDoEndOfTurn(true); // after playing this action list, it will be the end of turn for current player.
        ActionPlayer play = new ActionPlayer(model, this, actions);
        play.start();
        return true;
    }
    public void drawAndAddCard(PlayerCardHand playerHand) {
        Card drawnCard = cardDeck.drawCard();
        playerHand.addCardToPlayerHand(drawnCard, 1);
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
