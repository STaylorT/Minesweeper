import java.awt.*;
//import java.awt.event.*;
import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class Grid extends JPanel implements MouseListener {
    // matrix of cells
    private Cell cellGrid[][];

    //  variables for rows and cols
    private int numRows;
    private int numCols;

    // num flags
    private int numMines;

    // first click bool
    private boolean firstClick;

    // scoreboard so we can update the scoreboard easily
    Scoreboard scoreboard;

    public int[] getDimensions()
    {
        int[] arr={numRows, numCols};  //initializing array
        return arr;
    }

    public Grid(int rows, int cols, Scoreboard score_board, int flags) {
        // initialize variables for new grid
        cellGrid = new Cell[rows][cols];
        numRows = rows;
        numCols = cols;
        // set numMines in grid to number of flags initially
        numMines = flags;
        scoreboard = score_board;
        firstClick = true;

        // set this grid layout
        this.setLayout(new GridLayout(rows, cols, 0, 0));
        this.setBackground(Color.black);
        this.setPreferredSize(new Dimension(rows*30,rows*30));

        // fill the grid with cell objects
        for (int i = 0; i < rows; i++) { // create rows for grid
            for (int j = 0; j < cols; j++) { // fill each row
                Cell new_cell = new Cell(i,j, numRows, numCols, scoreboard);
                new_cell.addMouseListener((MouseListener)this);
                cellGrid[i][j] = new_cell;
                this.add(new_cell);
            }
      }
    }

    public void mousePressed(MouseEvent e ){}
    public void mouseReleased(MouseEvent e ){}
    public void mouseEntered(MouseEvent e ){}
    public void mouseExited(MouseEvent e ){}
    public void mouseClicked(MouseEvent e){
        // iterate through cells to see which one was clicked
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                if (e.getSource() == cellGrid[i][j]) { //
                    System.out.println(i);
                    System.out.println(j);
                    // let cell handle click
                    if (SwingUtilities.isRightMouseButton(e)) { // if right click, do flag things!
                        cellGrid[i][j].click(true, scoreboard, firstClick);
                    }
                    else {
                        // tell the cell it's been clicked
                        int result = cellGrid[i][j].click(false, scoreboard, firstClick);

                        firstClick = false; // set first click to false now

                        if (result == -1 ){ // game over
                            System.out.println("GAME OVER");
                        }
                        else if (result > 0){ // first click, set mines
                            setMines(result);
                            // then tell the cell it's been clicked again, but now as a regular click!
                            checkNeighbors(getNeighbors(i,j));
                        }
                    }

                }
            }
        }
    }

    private void checkNeighbors(int[] neighbors){

        for (int i = 0 ; i < neighbors.length ; i=i+2){
            int row = neighbors[i];
            int col = neighbors[i+1];
            cellGrid[row][col].setFlag();
        }
    }
    // get the neighbors of the given cell
    private int[] getNeighbors(int rowPosition, int colPosition){
        // every 2 elements represents a neighbor (row, col)
        int[] neighbors = new int[16];
        // top left
        neighbors[0] = rowPosition -1;
        neighbors[1] = colPosition -1;
        // top middle
        neighbors[2] = rowPosition -1;
        neighbors[3] = colPosition;
        // top right
        neighbors[4] = rowPosition -1;
        neighbors[5] = colPosition + 1;
        // middle left
        neighbors[6] = rowPosition;
        neighbors[7] = colPosition - 1;
        // middle right
        neighbors[8] = rowPosition;
        neighbors[9] = colPosition + 1;
        // bottom left
        neighbors[10] = rowPosition + 1;
        neighbors[11] = colPosition - 1;
        // bottom middle
        neighbors[12] = rowPosition + 1;
        neighbors[13] = colPosition;
        // bottom right
        neighbors[14] = rowPosition + 1;
        neighbors[15] = colPosition + 1;

        for (int i = 0 ; i < 16 ; i=i+2){ // for cells with fewer neighbors (edges), set the non-cells to -1
            if (neighbors[i] < 0  || neighbors[i] >= numRows)
                neighbors[i] = -1;
            if(neighbors[i+1] >= numCols || neighbors[i+1] < 0)
                neighbors[i+1] = -1;
        }
        return neighbors;
    }
    // function that sets all mines on the grid (called after first click)
    private void setMines(int cellID){
        System.out.println("Setting mines");
        ArrayList<Integer> minedCells = new ArrayList<>();
        // randomly pick mines
        while (minedCells.size() < numMines) {
            int randomID = (int) (Math.random() * numRows*numCols);
            if (!minedCells.contains(randomID)){
                minedCells.add(randomID);
            }
        }
        // set the mines on the rng cells
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                if (minedCells.contains(cellGrid[i][j].getId())){ // cells that were picked
                    cellGrid[i][j].setMine(true);
                }
            }
        }
    }

}
