package model;


import boardifier.model.GameStageModel;
import boardifier.model.StageElementsFactory;
import boardifier.model.TextElement;

public class RDRStageFactory extends StageElementsFactory{
    private final RDRStageModel stageModel;

    public RDRStageFactory(GameStageModel gameStageModel) {
        super(gameStageModel);
        stageModel = (RDRStageModel) gameStageModel;
    }

    public void setup(){
        // create the text that displays the player 1 name and put it in 0,0 in the virtual space
        TextElement textPlayer1 = new TextElement("player1", stageModel);
        textPlayer1.setLocation(0,0);
        stageModel.setPlayerName(textPlayer1, 0);

        // create the text that displays the player 2 name and put it in 0,25 in the virtual space
        TextElement textPlayer2 = new TextElement("player2", stageModel);
        textPlayer2.setLocation(0,25);
        stageModel.setPlayerName(textPlayer2, 1);
        
        // create the board, in 0,5 in the virtual space
        RDRBoard board = new RDRBoard(0, 5, stageModel);
        // assign the board to the game stage model
        stageModel.setBoard(board);

        //create the king pawn
        Pawn kingPawn = new Pawn(1,Pawn.PAWN_YELLOW, stageModel);
        stageModel.setKingPawn(kingPawn);
        board.addElement(kingPawn, 4, 4);


        //create the blue pot in 40,14 in the virtual space
        RDRPawnPot bluePot = new RDRPawnPot(40,14, stageModel);
        // assign the blue pot to the game stage model
        stageModel.setBluePot(bluePot);
        //create the red pot in 40,6 in the virtual space
        RDRPawnPot redPot = new RDRPawnPot(40,6, stageModel);
        // assign the red pot to the game stage model
        stageModel.setRedPot(redPot);
        // create the CardDeck
        CardDeck cardDeck = new CardDeck(40, 19, stageModel);
        stageModel.setCardDeck(cardDeck);

        //Create the players card hand
        //Player 1 hand
        PlayerCardHand player1CardHand = new PlayerCardHand(1, 1, stageModel);
        stageModel.setPlayerCardHand(player1CardHand, 0);
        //Player 2 hand
        PlayerCardHand player2CardHand = new PlayerCardHand(1, 26, stageModel);
        stageModel.setPlayerCardHand(player2CardHand, 1);

        Card[] PlayerCards1 = new Card[5];
        Card[] PlayerCards2 = new Card[5];
        for (int i = 0; i < 5; i++){
            PlayerCards1[i] = new Card(i,i%3+1,stageModel);
            PlayerCards2[i] = new Card(i,i%3+1,stageModel);
        }
        stageModel.setPlayerCards(PlayerCards1, 0);
        stageModel.setPlayerCards(PlayerCards2, 1);
        for (int i = 0; i < 5; i++){
            player1CardHand.addElement(PlayerCards1[i], 0, i);
            player2CardHand.addElement(PlayerCards2[i], 0, i);
        }


        /* create the pawns
            NB: their coordinates are by default 0,0 but since they are put
            within the pots, their real coordinates will be computed by the view
        */
        Pawn[] bluePawns = new Pawn[26];
        for(int i=0;i<26;i++) {
            bluePawns[i] = new Pawn(i,Pawn.PAWN_BLUE, stageModel);
        }
        // assign the black pawns to the game stage model
        stageModel.setBluePawns(bluePawns);
        Pawn[] redPawns = new Pawn[26];
        for(int i=0;i<26;i++) {
            redPawns[i] = new Pawn(i,Pawn.PAWN_RED, stageModel);
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
