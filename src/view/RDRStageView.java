package view;

import boardifier.model.GameStageModel;
import boardifier.view.GameStageView;
import boardifier.control.Logger;
import boardifier.view.ClassicBoardLook;

import boardifier.view.TextLook;
import model.RDRStageModel;

public class RDRStageView extends GameStageView {
    public RDRStageView(String name, GameStageModel gameStageModel) {
        super(name, gameStageModel);
    }

    public void createLooks(){
        RDRStageModel model = (RDRStageModel)gameStageModel;

        addLook(new TextLook(model.getPlayerName(0)));
        addLook(new TextLook(model.getPlayerName(1)));


        // create a ClassicBoardLook (with borders and coordinates) for the main board.
        addLook(new ClassicBoardLook(2, 4, model.getBoard(), 1, 1, true));


        // create looks for both pots
        addLook(new BluePawnPotLook(model.getBluePot()));
        addLook(new RedPawnPotLook(model.getRedPot()));
        // create looks for all pawns
        for(int i=0;i<26;i++) {
            addLook(new PawnLook(model.getBluePawns()[i]));
            addLook(new PawnLook(model.getRedPawns()[i]));
        }

        addLook(new PawnLook(model.getKingPawn()));

        addLook(new BlueHeroCardPotLook(model.getblueHeroPot()));
        addLook(new RedHeroCardPotLook(model.getRedHeroPot()));
        for(int i = 0; i < 4; i++){
            addLook(new HeroCardLook(model.getHeroCardPlayer1()[i]));
            addLook(new HeroCardLook(model.getHeroCardPlayer2()[i]));
        }

        // create look for CardDeck
        addLook(new CardDeckLook(model.getCardDeck()));

        addLook(new PlayerCardHandLook(model.getPlayerCardHand(0)));
        addLook(new PlayerCardHandLook(model.getPlayerCardHand(1)));

        for (int i = 0; i < 5; i++){
            addLook(new CardLook(model.getCards(0)[i]));
            addLook(new CardLook(model.getCards(1)[i]));
        }

        /*
        addLook(new TextLook(model.getBluePawnsRemaining()));
        System.out.println("nbr bleu:"+ model.getBluePawnsRemaining());
        System.out.println("nbr rouge:"+ model.getRedPawnsRemaining());
         */
        Logger.debug("finished creating game stage looks", this);
    }
}
