package view;

import boardifier.model.GameElement;
import boardifier.view.ConsoleColor;
import boardifier.view.ElementLook;
import model.HeroCard;

public class HeroCardLook extends ElementLook {
    public HeroCardLook(GameElement element) {
        super(element, 1, 1);
    }

    public void render(){
        HeroCard heroCard = (HeroCard)element;
        if(heroCard.getColor() == HeroCard.HERO_CARD_BLUE){
            shape[0][0] = ConsoleColor.BLUE + "H" + ConsoleColor.RESET;
        } else {
            shape[0][0] = ConsoleColor.RED + "H" + ConsoleColor.RESET;
        }
    }
}
