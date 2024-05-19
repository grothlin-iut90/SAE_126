package view;

import boardifier.model.ContainerElement;
import boardifier.view.ContainerLook;
import boardifier.view.ElementLook;
import boardifier.view.TableLook;
import model.CardDeck;

import java.awt.*;

public class CardDeckLook extends ElementLook {
    public CardDeckLook(ContainerElement containerElement) { super(containerElement, 1, 3, -1);}

    protected void render(){
        shape[0][0] = "P";
    }
}
