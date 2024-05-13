package model;

import boardifier.control.Logger;
import boardifier.model.ContainerElement;
import boardifier.model.GameStageModel;

import java.awt.*;
import java.util.List;

public class RDRBoard extends ContainerElement {
    public RDRBoard(int x, int y, GameStageModel gameStageModel) {
        // call the super-constructor to create a 9x9 grid, named "RDRBoard", and in x,y in space
        super("RDRBoard", x, y, 9 , 9, gameStageModel);
    }

    /*
    public void setValidCells(int number) {
        Logger.debug("called",this);
        resetReachableCells(false);
        List<Point> valid = computeValidCells(number);
        if (valid != null) {
            for(Point p : valid) {
                reachableCells[p.y][p.x] = true;
            }
        }
    }
    */

}
