package model;

import boardifier.control.Logger;
import boardifier.model.ContainerElement;
import boardifier.model.GameStageModel;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class RDRBoard extends ContainerElement {
    private int nbRows = 9;
    private int nbCols = 9;
    public RDRBoard(int x, int y, GameStageModel gameStageModel) {
        // call the super-constructor to create a 9x9 grid, named "RDRBoard", and in x,y in space
        super("RDRBoard", x, y, 9 , 9, gameStageModel);
    }

    public int getRows(){
        return nbRows;
    }
    public int getCols(){
        return nbCols;
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

    public List<Point> computeValidCells(int number) {
        List<Point> lst = new ArrayList<>();
        Pawn p = null;
        // if the grid is empty, is it the first turn and thus, all cells are valid
        if (isEmpty()) {
            // i are rows
            for(int i=0;i<8;i++) {
                // j are cols
                for (int j = 0; j < 8; j++) {
                    // cols is in x direction and rows are in y direction, so create a point in (j,i)
                    lst.add(new Point(j,i));
                }
            }
            return lst;
        }
        // else, take each empty cell and check if it is valid
        for(int i=0;i<8;i++) {
            for(int j=0;j<8;j++) {
                if (isEmptyAt(i,j)) {
                    // check adjacence in row-1
                    if (i-1 >= 0) {
                        if (j-1>=0) {
                            p = (Pawn)getElement(i-1,j-1);

                            // check if same parity
                            if ((p != null) && ( p.getNumber()%2 == number%2)) {
                                lst.add(new Point(j,i));
                                continue; // go to the next point
                            }
                        }
                        p = (Pawn)getElement(i-1,j);
                        // check if different parity
                        if ((p != null) && ( p.getNumber()%2 != number%2)) {
                            lst.add(new Point(j,i));
                            continue; // go to the next point
                        }
                        if (j+1<=2) {
                            p = (Pawn)getElement(i-1,j+1);
                            // check if same parity
                            if ((p != null) && ( p.getNumber()%2 == number%2)) {
                                lst.add(new Point(j,i));
                                continue; // go to the next point
                            }
                        }
                    }
                    // check adjacence in row+1
                    if (i+1 <= 2) {
                        if (j-1>=0) {
                            p = (Pawn)getElement(i+1,j-1);
                            // check if same parity
                            if ((p != null) && ( p.getNumber()%2 == number%2)) {
                                lst.add(new Point(j,i));
                                continue; // go to the next point
                            }
                        }
                        p = (Pawn)getElement(i+1,j);
                        // check if different parity
                        if ((p != null) && ( p.getNumber()%2 != number%2)) {
                            lst.add(new Point(j,i));
                            continue; // go to the next point
                        }
                        if (j+1<=2) {
                            p = (Pawn)getElement(i+1,j+1);
                            // check if same parity
                            if ((p != null) && ( p.getNumber()%2 == number%2)) {
                                lst.add(new Point(j,i));
                                continue; // go to the next point
                            }
                        }
                    }
                    // check adjacence in row
                    if (j-1>=0) {
                        p = (Pawn)getElement(i,j-1);
                        // check if different parity
                        if ((p != null) && ( p.getNumber()%2 != number%2)) {
                            lst.add(new Point(j,i));
                            continue; // go to the next point
                        }
                    }
                    if (j+1<=2) {
                        p = (Pawn)getElement(i,j+1);
                        // check if different parity
                        if ((p != null) && ( p.getNumber()%2 != number%2)) {
                            lst.add(new Point(j,i));
                            continue; // go to the next point
                        }

                    }
                }
            }
        }
        return lst;
    }

}
