package view;

import boardifier.model.GameElement;
import boardifier.view.ConsoleColor;
import boardifier.view.ElementLook;
import model.CardBack;

public class CardBackLook extends ElementLook {

    public CardBackLook(GameElement element) {
        super(element, 6, 3);
    }

    protected void render(){

        CardBack cardBack = (CardBack)element;

        // int numberCardsLeft = cardBack.getDeck().getNumberCardsLeft();
        shape[0][1] = ConsoleColor.WHITE + "?" + ConsoleColor.RESET;
        shape[1][1] = ConsoleColor.WHITE + "?" + ConsoleColor.RESET;
        shape[2][1] = ConsoleColor.WHITE + "?" + ConsoleColor.RESET;
        shape[1][2] = ConsoleColor.WHITE + "?" + ConsoleColor.RESET;
        shape[2][2] = ConsoleColor.WHITE + "?" + ConsoleColor.RESET;
        shape[0][2] = ConsoleColor.WHITE + "0" + ConsoleColor.RESET;
        System.out.println("yipee");
    }
}
