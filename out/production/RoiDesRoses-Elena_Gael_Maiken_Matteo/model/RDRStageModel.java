package model;

import boardifier.model.*;

public class RDRStageModel extends GameStageModel{

    // define stage state variables
    private int bluePawnsToPlay;
    private int redPawnsToPlay;
    // define stage game elements
    private RDRBoard board;
    private RDRPawnPot bluePot;
    private RDRPawnPot redPot;
    private RDRHeroCardPot blueHeroPot;
    private RDRHeroCardPot redHeroPot;
    private Pawn[] bluePawns;
    private Pawn[] redPawns;
    private Card[] PlayerCards1;
    private Card[] PlayerCards2;
    private HeroCard[] PlayerHeroCards1;
    private HeroCard[] PlayerHeroCards2;
    private int numberOfCard;
    private CardDeck cardDeck;
    private HeroCard[] heroCard;
    private PlayerCardHand player1CardHand;
    private PlayerCardHand player2CardHand;
    private Pawn kingPawn;
    private TextElement player1Name;
    private TextElement player2Name;
    private int blueHeroCardsUsed;
    private int redHeroCardsUsed;
    private int cardBlueUsed;
    private int cardRedUsed;
    private static final int MAX_HERO_CARDS = 4;

    // Uncomment next line if the example with a main container is used. see end of HoleStageFactory and HoleStageView
    //private ContainerElement mainContainer;
    public RDRStageModel(String name, Model model) {
        super(name, model);
        bluePawnsToPlay = 26;
        redPawnsToPlay = 26;
        blueHeroCardsUsed = 0;
        redHeroCardsUsed = 0;
        cardBlueUsed = 4;
        cardRedUsed = 4;
        numberOfCard = 5;
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
    public int getBluePawnsToPlay() {
        return bluePawnsToPlay;
    }
    public void reduceBluePawnToPlay(){
        bluePawnsToPlay--;
    }
    public int getRedPawnsToPlay(){
        return redPawnsToPlay;
    }
    public void reduceRedPawnToPlay(){
        redPawnsToPlay--;
    }
    public ContainerElement getContainer(GameElement element) {
        for (ContainerElement container : getContainers()) {
            if (container.contains(element)) {
                return container;
            }
        }
        return null;
    }
    public int getNumberOfCard(){
        return numberOfCard;
    }
    public CardDeck getCardDeck(){
        return cardDeck;
    }
    public void setCardDeck(CardDeck cardDeck){
        this.cardDeck = cardDeck;
        addContainer(cardDeck);
    }
    public PlayerCardHand getPlayerCardHand(int idPlayer){
        if(idPlayer==1) {
            return player1CardHand;
        }
        return player2CardHand;
    }
    public void setPlayerCardHand(PlayerCardHand playerCardHand, int idPlayer){
        if(idPlayer == 1){
            this.player1CardHand = playerCardHand;
            addContainer(playerCardHand);
        }
        else{
            this.player2CardHand = playerCardHand;
            addContainer(playerCardHand);
        }
    }
    public Card[] getCards(int idPlayer){
        if(idPlayer==1) {
            return PlayerCards1;
        }
        return PlayerCards2;
    }
    public void setPlayerCards(Card[] PlayerCards, int idPlayer){
        if(idPlayer==1) {
            this.PlayerCards1 = PlayerCards;
            for (int i = 0; i < PlayerCards.length; i++) {
                addElement(PlayerCards[i]);
            }
        }
        else{
            this.PlayerCards2 = PlayerCards;
            for (int i = 0; i < PlayerCards.length; i++) {
                addElement(PlayerCards[i]);
            }
        }
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
    public RDRHeroCardPot getblueHeroPot(){
        return blueHeroPot;
    }
    public void setBlueHeroPot(RDRHeroCardPot blueHeroPot){
        this.blueHeroPot = blueHeroPot;
        addContainer(blueHeroPot);
    }
    public RDRHeroCardPot getRedHeroPot(){
        return redHeroPot;
    }
    public void setRedHeroPot(RDRHeroCardPot redHeroPot){
        this.redHeroPot = redHeroPot;
        addContainer(redHeroPot);
    }
    public Pawn[] getBluePawns() {
        return bluePawns;
    }
    public void setBluePawns(Pawn[] bluePawns) {
        this.bluePawns = bluePawns;
        for(int i=0;i<bluePawns.length;i++) {
            addElement(bluePawns[i]);
        }
    }
    public HeroCard[] getHeroCardPlayer1(){
        return PlayerHeroCards1;
    }
    public HeroCard[] getHeroCardPlayer2(){
        return PlayerHeroCards2;
    }
    public void setHeroCardPlayer1(HeroCard[] PlayerHeroCards1){
        this.PlayerHeroCards1 = PlayerHeroCards1;
        for(int i = 0; i < PlayerHeroCards1.length; i++){
            addElement(PlayerHeroCards1[i]);
        }
    }
    public void setHeroCardsPlayer2(HeroCard[] PlayerHeroCards2){
        this.PlayerHeroCards2 = PlayerHeroCards2;
        for(int i = 0; i < PlayerHeroCards2.length; i++){
            addElement(PlayerHeroCards2[i]);
        }
    }
    public Pawn[] getRedPawns() {
        return redPawns;
    }
    public int getRedPawnsRemaining(){
        return redPawnsToPlay;
    }
    public int getBluePawnsRemaining(){
        return bluePawnsToPlay;
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
    }public boolean canUseHeroCard(int idPlayer) {
        if (idPlayer == 0) {
            return blueHeroCardsUsed < MAX_HERO_CARDS;
        } else {
            return redHeroCardsUsed < MAX_HERO_CARDS;
        }
    }
    public void useHeroCard(int idPlayer) {
        if (idPlayer == 0) {
            if (blueHeroCardsUsed < MAX_HERO_CARDS) {
                HeroCard heroCard1 = getHeroCardPlayer1()[cardBlueUsed-1];
                heroCard1.setVisible(false);
                removeElement(heroCard1);
                cardBlueUsed--;
                blueHeroCardsUsed++;
            } else {
                throw new IllegalStateException("Player 0 has already used all hero cards.");
            }
        } else {
            if (redHeroCardsUsed < MAX_HERO_CARDS) {
                HeroCard heroCard2 = getHeroCardPlayer2()[cardRedUsed-1];
                heroCard2.setVisible(false);
                removeElement(heroCard2);
                cardRedUsed--;
                redHeroCardsUsed++;
            } else {
                throw new IllegalStateException("Player 1 has already used all hero cards.");
            }
        }
    }
    public TextElement getPlayerName(int idPlayer) {
        if(idPlayer == 1) {
            return player1Name;
        }
        return player2Name;
    }
    public void setPlayerName(TextElement playerName, int idPlayer) {
        if(idPlayer == 1) {
            this.player1Name = playerName;
            addElement(playerName);
        }
        else{
            this.player2Name = playerName;
            addElement(playerName);
        }
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
