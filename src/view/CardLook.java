package view;

import boardifier.model.GameElement;
import boardifier.view.ConsoleColor;
import boardifier.view.ElementLook;
import model.Card;

public class CardLook extends ElementLook{

    public CardLook(GameElement element) {
        super(element, 6, 3);
    }

    protected void render(){

        Card card = (Card)element;
        // The look of the direction of the card
        switch (card.getDirection()){
            case 0:
                shape[0][1] = ConsoleColor.WHITE + "↑" + ConsoleColor.RESET;
                break;

            case 1:
                shape[0][1] = ConsoleColor.WHITE + "←" + ConsoleColor.RESET;
                break;

            case 2:
                shape[0][1] = ConsoleColor.WHITE + "↓" + ConsoleColor.RESET;
                break;

            case 3:
                shape[0][1] = ConsoleColor.WHITE + "→" + ConsoleColor.RESET;
                break;

            case 4:
                shape[0][1] = ConsoleColor.WHITE + "↖" + ConsoleColor.RESET;
                break;

            case 5:
                shape[0][1] = ConsoleColor.WHITE + "↙" + ConsoleColor.RESET;
                break;

            case 6:
                shape[0][1] = ConsoleColor.WHITE + "↘" + ConsoleColor.RESET;
                break;

            case 7:
                shape[0][1] = ConsoleColor.WHITE + "↗" + ConsoleColor.RESET;
                break;

            default:
                shape[0][1] = ConsoleColor.WHITE + "?" + ConsoleColor.RESET;
                break;
        }
        // The look of the "steps" of the card
        switch (card.getCardSteps()) {
            case 1:
                shape[1][1] = ConsoleColor.WHITE + "I" + ConsoleColor.RESET;
                break;

            case 2:
                shape[1][0] = ConsoleColor.WHITE + "I" + ConsoleColor.RESET;
                shape[1][2] = ConsoleColor.WHITE + "I" + ConsoleColor.RESET;
                break;

            case 3:
                shape[1][0] = ConsoleColor.WHITE + "I" + ConsoleColor.RESET;
                shape[1][1] = ConsoleColor.WHITE + "I" + ConsoleColor.RESET;
                shape[1][2] = ConsoleColor.WHITE + "I" + ConsoleColor.RESET;
                break;

            default:
                shape[1][1] = ConsoleColor.WHITE + "?" + ConsoleColor.RESET;
                break;
        }
    }
}
