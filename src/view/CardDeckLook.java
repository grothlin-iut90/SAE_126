package view;

import boardifier.model.ContainerElement;
import boardifier.view.TableLook;

public class CardDeckLook extends TableLook {
    public CardDeckLook(ContainerElement containerElement) {
        super (containerElement, 3, 6, -1, 0, 3, 2);
        //To allow numbers to be displayed on the side of card
        setPaddingLeft(-1);
        setPaddingTop(0);
    }
}
