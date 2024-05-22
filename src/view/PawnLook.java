package view;

import boardifier.model.GameElement;
import boardifier.view.ConsoleColor;
import boardifier.view.ElementLook;
import model.Pawn;

/**
 * The look of the Pawn is fixed, with a single characters representing the value of the pawn
 * and a black or red background.
 */
public class PawnLook extends ElementLook {

    public PawnLook(GameElement element) {
        super(element, 1, 1);
    }

    protected void render() {

        Pawn pawn = (Pawn)element;

        if (pawn.getColor() == Pawn.PAWN_BLUE) {
            shape[0][0] = ConsoleColor.BLUE + "o" + ConsoleColor.RESET;
        } else if (pawn.getColor() == Pawn.PAWN_RED) {
            shape[0][0] = ConsoleColor.RED + "o" + ConsoleColor.RESET;
        }
        else {
            shape[0][0] = ConsoleColor.YELLOW + "●" + ConsoleColor.RESET;
        }
    }
}
