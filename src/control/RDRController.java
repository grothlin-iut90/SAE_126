package control;

import boardifier.control.ActionPlayer;
import boardifier.control.Controller;
import boardifier.model.GameElement;
import boardifier.model.Model;
import boardifier.model.Player;
import boardifier.view.View;
import model.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class RDRController extends Controller {

    BufferedReader consoleIn;
    CardDeck cardDeck;
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
        RDRStageModel gameStage = (RDRStageModel) model.getGameStage();
        //used to test useHeroCard
        /*
        initializeRedPawn();
        initializeBluePawn();
         */
        //use update() to update all the changes and also in the view
        update();
        while(! model.isEndStage()) {
            playTurn();
            endOfTurn();
            update();
        }
        /*
        RDRStageModel gameStage = (RDRStageModel) model.getGameStage();
        gameStage.computePartyResult();
         */
        endGame();
    }
    void initializeRedPawn() {
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
    void initializeBluePawn() {
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
                placePawn(gameStage.getBluePawns()[bluePawnsToPlay - 2], 2, 5);
                gameStage.reduceBluePawnToPlay();
                placePawn(gameStage.getBluePawns()[bluePawnsToPlay - 3], 1, 5);
                gameStage.reduceBluePawnToPlay();
                System.out.println("Red pawn placed at row " + row + ", col " + col);
            } else {
                System.out.println("No red pawns left to place.");
            }
        } else {
            System.out.println("Position (" + row + ", " + col + ") is not empty.");
        }
    }
    void playTurn() {
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
                System.out.print("1. Move the King and place a Pawn | 2. Move the King and Use Hero Card | 3. Draw a Move Card\n" + "player" + (model.getIdPlayer()+1) + " >");
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
                            //getFreeCardSlotsController(gameStage.getPlayerCardHand(model.getIdPlayer()));
                            ok = handleDrawMoveCard(gameStage);
                            break;

                        default:
                            System.out.println("Please enter a digit between 1 and 3 for your action");
                            break;
                    }
                } catch (IOException e) {
                    System.out.println("An error occurred. Please try again.");
                } catch (NumberFormatException e) {
                    System.out.println("\nEnter a valid number please between 1 and 3 >:)\n");
                }
            }
        }
    }

    boolean handleMoveKingAndPlacePawn(Player p, RDRStageModel gameStage) {
        System.out.print("What card do you want to play > ");
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
            System.out.println("The move was unsuccessful, try another move please");
        }
        return moveSuccessful;
    }
    public boolean handleMoveKingAndUseHeroCard(Player p, RDRStageModel gameStage){
        System.out.print("What card do you want to play > ");
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
            System.out.println("The move was unsuccessful, try another move please");
        }
        return moveSuccessful;
    }
    private int getValidatedCardNumber(RDRStageModel gameStage, Player p) {
        while (true) {
            try {
                String reader = consoleIn.readLine();
                int numberCardPlayed = Integer.parseInt(reader) - 1;
                int cardsLength = gameStage.getCards(model.getIdPlayer()).length;
                if (numberCardPlayed >= 0 && numberCardPlayed < cardsLength && gameStage.getCards(model.getIdPlayer())[numberCardPlayed] != null) {
                    return numberCardPlayed;
                } else {
                    System.out.println("Please enter a valid card number between 1 and " + cardsLength + " and ensure the slot contains a card.");
                }
            } catch (NumberFormatException | IOException e) {
                System.out.println("Invalid input. Please enter a number.");
                System.out.print(p.getName() + " > ");
            }
        }
    }
    private void removeCardFromHand(RDRStageModel gameStage, int numberCardPlayed) {
        Card cardToPlay = gameStage.getCards(model.getIdPlayer())[numberCardPlayed];
        PlayerCardHand playerHand = gameStage.getPlayerCardHand(model.getIdPlayer());
        gameStage.putCardInDeck(numberCardPlayed, model.getIdPlayer());
        playerHand.removeCardFromPlayerHand(cardToPlay);
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
            System.out.println("\nPlease use a card that leads to an ennemy pawn\n");
            return false;
        } else {
            List<GameElement> elementsAtNewPosition = board.getElements(newRow, newCol);
            for (GameElement element : elementsAtNewPosition) {
                if (element instanceof Pawn) {
                    Pawn opponentPawnCast = (Pawn) element;
                    if (opponentPawnCast.getPlayerId() != idPlayer) {
                        opponentPawnCast.setVisible(false);
                        board.removeElement(opponentPawnCast);
                        //System.out.println("Removed opponent's pawn at the new position.");

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

                        // Placez le pion du roi sur la nouvelle position
                        board.removeElement(kingPawn);
                        board.addElement(kingPawn, newRow, newCol);

                        return true;
                    }
                }
            }
            System.out.println("No opponent's pawn found at the new position.");
        }
        return false;
    }
    boolean handleDrawMoveCard(RDRStageModel gameStage) {
        try {
            PlayerCardHand playerCardHand = gameStage.getPlayerCardHand(model.getIdPlayer());
            List<Integer> freeCardSlots = playerCardHand.getFreeCardSlots(playerCardHand);
            System.out.println(freeCardSlots.size());
            if (freeCardSlots.size() <= 5 && freeCardSlots.size() > 0) {
                System.out.println("test");
                drawAndAddCard(playerCardHand);
                System.out.println("test1");
                return true;
            } else {
                System.out.println("You only can draw a card if you have less than 5 cards.");
            }
        } catch (IllegalStateException e) {
            System.out.println("No more cards available in the deck.");
        }
        return false;
    }
    public int getFirstPlacePlayerHandCard() {
        RDRStageModel gameStage = (RDRStageModel) model.getGameStage(); // Access the RDRStageModel instance
        PlayerCardHand playerCardHand = gameStage.getPlayerCardHand(model.getIdPlayer());
        List<Integer> freeSlots = playerCardHand.getFreeCardSlots(playerCardHand);
        if (freeSlots.isEmpty()) {
            throw new IllegalStateException("No free card slots available");
        }
        return freeSlots.get(0);
    }
    public void endOfTurn() {
        model.setNextPlayer();
        // get the new player to display its name
        Player p = model.getCurrentPlayer();
        RDRStageModel stageModel = (RDRStageModel) model.getGameStage();
        //stageModel.getPlayerName(1).setText(p.getName());
    }
    boolean MoveKingPawn(String direction, int move) {
        RDRStageModel gameStage = (RDRStageModel) model.getGameStage();
        RDRBoard board = gameStage.getBoard();
        Pawn kingPawn = gameStage.getKingPawn();

        // Get the new row and column from getMoveFromDirection
        int[] newPosition = getMoveFromDirection(direction, move);
        if (newPosition == null) {
            return false;
        }
        int newRow = newPosition[0];
        int newCol = newPosition[1];

        if (!board.isEmptyAt(newRow,newCol)) return false;

        // compute valid cells for the chosen pawn
        if (!gameStage.getBoard().canReachCell(newRow, newCol)) return false;
        // check the player id and select the pot pawn to player
        if(model.getIdPlayer() == 0){
            int bluepawnstoplay = gameStage.getBluePawnsToPlay();
            placePawn(gameStage.getBluePawns()[bluepawnstoplay-1], newRow, newCol);
            gameStage.reduceBluePawnToPlay();
            //System.out.println("blue pawns remaining : " + gameStage.getBluePawnsRemaining());
        }else{
            int redpawnstoplay = gameStage.getRedPawnsToPlay();
            placePawn(gameStage.getRedPawns()[redpawnstoplay-1], newRow, newCol);
            gameStage.reduceRedPawnToPlay();
            //System.out.println("red pawns remaining : " + gameStage.getRedPawnsRemaining());
        }

        // Placez le pion du roi sur la nouvelle position
        board.removeElement(kingPawn);
        board.addElement(kingPawn, newRow, newCol);

        return true;
    }
    public void drawAndAddCard(PlayerCardHand playerHand) {
        RDRStageModel gameStage = (RDRStageModel) model.getGameStage();
        ArrayList<Card> deck = gameStage.getDeck();
        System.out.println(deck);
        if (deck == null) {
            throw new IllegalStateException("Card deck is not initialized.");
        }
        System.out.println("Test 3");
        Card drawnCard = gameStage.drawCard();
        System.out.println("drawncard " + drawnCard);
        gameStage.addCardToPlayerHand(drawnCard, model.getIdPlayer());
        playerHand.addCardToPlayerHand(drawnCard, getFirstPlacePlayerHandCard());
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
        //AfficherPos();
        RDRStageModel gameStage = (RDRStageModel) model.getGameStage();
        Pawn kingPawn = gameStage.getKingPawn();
        int[] currentPosition = kingPawn.getPosition();
        int newRow = currentPosition[0];
        int newCol = currentPosition[1];

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
            System.out.println("The pawn is out of the board");
            return null;
        }
    }

    public boolean canUseCard(Card card){
        RDRStageModel gameStage = (RDRStageModel) model.getGameStage();
        RDRBoard board = gameStage.getBoard();

        String direction = card.getStringDirection();
        int move = card.getCardSteps();

        int[] newPosition = getMoveFromDirection(direction, move);
        if (newPosition == null) {
            return false;
        }
        int newRow = newPosition[0];
        int newCol = newPosition[1];

        if (!board.isEmptyAt(newRow,newCol)) {
            return false;
        }

        if (!gameStage.getBoard().canReachCell(newRow, newCol)) {
            return false;
        }
        return true;
    }

    public boolean canUseAnyCard(){
        RDRStageModel gameStage = (RDRStageModel) model.getGameStage();
        RDRBoard board = gameStage.getBoard();
        Card[] cards = gameStage.getCards(model.getIdPlayer());

        for(Card card : cards){
            if(!canUseCard(card)){
                return false;
            }
        }
        return true;
    }

    public boolean canDrawCard(){
        //Return true if the player has less than 5 cards in hand
        return ((RDRStageModel)model.getGameStage()).getCards(model.getIdPlayer()).length < 5;
    }

}
