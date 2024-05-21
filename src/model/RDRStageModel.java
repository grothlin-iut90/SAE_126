package model;

import boardifier.model.*;

import java.util.Stack;

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
        if(idPlayer == 0) {
            return player1CardHand;
        }
        return player2CardHand;
    }
    public void setPlayerCardHand(PlayerCardHand playerCardHand, int idPlayer){
        if(idPlayer == 0){
            this.player1CardHand = playerCardHand;
            addContainer(playerCardHand);
        }
        else{
            this.player2CardHand = playerCardHand;
            addContainer(playerCardHand);
        }
    }
    public Card[] getCards(int idPlayer){
        if(idPlayer == 0) {
            return PlayerCards1;
        }
        return PlayerCards2;
    }
    public void setPlayerCards(Card[] PlayerCards, int idPlayer){
        if(idPlayer == 0) {
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
        if(idPlayer == 0) {
            return player1Name;
        }
        return player2Name;
    }
    public void setPlayerName(TextElement playerName, int idPlayer) {
        if(idPlayer == 0) {
            this.player1Name = playerName;
            addElement(playerName);
        }
        else{
            this.player2Name = playerName;
            addElement(playerName);
        }
    }
    private void setupCallbacks() {
        onPutInContainer((element, gridDest, rowDest, colDest) -> {
            if (gridDest != board) return;
            if (!(element instanceof Pawn)) return;
            if (!canPlayerPlay(0) || !canPlayerPlay(1)) {
                computePartyResult();
            }
        });
    }

    // Fonction pour vérifier si un joueur peut jouer
    private boolean canPlayerPlay(int idPlayer) {
        if (idPlayer == 0) {
            return bluePawnsToPlay > 0 || blueHeroCardsUsed < MAX_HERO_CARDS;
        } else {
            return redPawnsToPlay > 0 || redHeroCardsUsed < MAX_HERO_CARDS;
        }
    }

    // function to know the result (need to change everything)
    public void computePartyResult() {
        boolean[][] visited = new boolean[board.getRows()][board.getCols()];
        int blueScore = 0;
        int redScore = 0;

        for (int row = 0; row < board.getRows(); row++) {
            for (int col = 0; col < board.getCols(); col++) {
                if (!visited[row][col]) {
                    Pawn pawn = (Pawn) board.getElement(row, col);
                    if (pawn != null) {
                        int zoneSize = exploreZone(row, col, pawn.getColor(), visited);
                        int zoneScore = zoneSize * zoneSize;
                        if (pawn.getColor() == Pawn.PAWN_BLUE) {
                            blueScore += zoneScore;
                        } else if (pawn.getColor() == Pawn.PAWN_RED) {
                            redScore += zoneScore;
                        }
                    }
                }
            }
        }

        System.out.println("Blue Player Score: " + blueScore);
        System.out.println("Red Player Score: " + redScore);

        if (blueScore > redScore) {
            System.out.println("Blue Player Wins!");
        } else if (redScore > blueScore) {
            System.out.println("Red Player Wins!");
        } else {
            System.out.println("It's a Tie!");
        }
    }

    private int exploreZone(int startRow, int startCol, int color, boolean[][] visited) {
        int zoneSize = 0;
        Stack<int[]> stack = new Stack<>();
        stack.push(new int[]{startRow, startCol});

        while (!stack.isEmpty()) {
            int[] position = stack.pop();
            int row = position[0];
            int col = position[1];

            if (row < 0 || row >= board.getRows() || col < 0 || col >= board.getCols() || visited[row][col]) {
                continue;
            }

            Pawn pawn = (Pawn) board.getElement(row, col);
            if (pawn == null || pawn.getColor() != color) {
                continue;
            }

            visited[row][col] = true;
            zoneSize++;

            stack.push(new int[]{row - 1, col}); // Up
            stack.push(new int[]{row + 1, col}); // Down
            stack.push(new int[]{row, col - 1}); // Left
            stack.push(new int[]{row, col + 1}); // Right
        }

        return zoneSize;
    }

    @Override
    public StageElementsFactory getDefaultElementFactory() {
        return new RDRStageFactory(this);
    }
}
