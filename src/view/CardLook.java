package view;

import boardifier.model.GameElement;
import boardifier.view.ConsoleColor;
import boardifier.view.ElementLook;
import model.Card;

public class CardLook extends ElementLook{

    public CardLook(GameElement element) {
        super(element, 1, 2);
    }

    protected void render(){

        Card card = (Card)element;
        if (card.getDirection() == 0){
            //shape[0][0] = ""
        }
    }
}
