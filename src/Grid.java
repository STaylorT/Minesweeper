import java.awt.*;
//import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

public class Grid extends JPanel{
    private ArrayList<ArrayList<Cell>> cellGrid = new ArrayList<ArrayList<Cell>>();
    private int numRows;
    private int numCols;

    public int[] getDimensions()
    {
        int[] arr={numRows, numCols};  //initializing array
        return arr;
    }

    public Grid(int rows, int cols) {
        numRows = rows;
        numCols = cols;
        // set this grid layout
        this.setLayout(new GridLayout(rows, cols, 3, 3));
        this.setBackground(Color.black);
        this.setPreferredSize(new Dimension(350,350));
        // fill the grid with cell objects
        ArrayList<Cell> row = new ArrayList<Cell>();
        for (int i = 0; i < rows; i++) { // create rows for grid
            row.clear();
            for (int j = 0; j < cols; j++) { // fill each row
                Cell new_cell = new Cell(i,j);
                row.add(new_cell);
                this.add(new_cell);
            }
            cellGrid.add(row);
        }
    }

}
