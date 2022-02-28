import java.awt.*;
//import java.awt.event.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Grid extends JPanel implements MouseListener {
    // matrix of cells
    private Cell cellGrid[][];

    //  variables for rows and cols
    private int numRows;
    private int numCols;

    // keeping track
    private int numMines;
    private int numUncovered; // number of cells uncovered
    private int targetCellsUncovered;

    // cell size
    private int CELL_SIZE=30;

    // first click bool
    private boolean firstClick;

    // scoreboard so we can update the scoreboard easily
    Scoreboard scoreboard;

    // time
    private int time = 0;
    private Timer elapsedTimer; // timer to count elapsed time each game


    // status of game  0 = Lost  |  1 = Won  | -1 = in game
    private int gameStatus = -1;

    public int[] getDimensions()
    {
        int[] arr={numRows, numCols};  //initializing array
        return arr;
    }

    public Grid(int rows, int cols, Scoreboard score_board, int flags, int cellSize) {
        CELL_SIZE = cellSize;

        // initialize variables for new grid
        setUpTimers(); // instantiate timer
        cellGrid = new Cell[rows][cols];
        numRows = rows;
        numCols = cols;
        // set numMines in grid to number of flags initially
        numMines = flags;
        numUncovered = 0;
        targetCellsUncovered = rows*cols - numMines;
        scoreboard = score_board;
        firstClick = true;
        time=0;

        // set this grid layout
        this.setLayout(new GridLayout(rows, cols, 0, 0));
        this.setBackground(Color.black);
        this.setPreferredSize(new Dimension(numCols*CELL_SIZE,numRows*CELL_SIZE));

        // fill the grid with cell objects
        for (int i = 0; i < rows; i++) { // create rows for grid
            for (int j = 0; j < cols; j++) { // fill each row
                Cell new_cell = new Cell(i,j, numRows, numCols, scoreboard, CELL_SIZE);
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
                    // let cell handle click
                    if (SwingUtilities.isRightMouseButton(e)) { // if right click, do flag things!
                        cellGrid[i][j].click(true, scoreboard, firstClick);
                    }
                    else { // LEFT CLICK
                        // tell the cell it's been clicked
                        int result = cellGrid[i][j].click(false, scoreboard, firstClick);
                        firstClick = false; // set first click to false

                        if (result == -1 ){ // game over
                            gameOver(-1);
                        }
                        else if (result > 0){ // first click, set mines
                            elapsedTimer.restart();
                            setMines(i, j);
                            // explode the surrounding area.
                            // then tell the cell it's been clicked again, but now as a regular click!
                            int numMinesAround = getNumMinesAround(i, j, getNeighbors(i,j));
                            System.out.println("Num mines around first :" + numMinesAround);
                            // update icon for this cell => 0=blank | 1-8=numberIcon
                            if (cellGrid[i][j].getState() == "default" && !cellGrid[i][j].hasMine()){
                                cellGrid[i][j].setIcon(numMinesAround);
                                numUncovered++;
                            }
                            explodeChain(i, j);

                        }
                        if (result == 0){ // a regular cell was clicked . .
                            checkNeighbors(i,j, getNeighbors(i,j));
                        }
                        if (numUncovered == targetCellsUncovered){ // won game.
                            gameOver(1);
                        }
                    }
                }
            }
        }
    }
    private void gameOver(int code){ // 0 = game over  |  1 = win
        if (code == -1){
            elapsedTimer.stop();
            showMines();
            gameStatus = 0;
        }
        else if(code == 1){
            elapsedTimer.stop();
            gameStatus = 1;
        }
    }

    private void showMines(){ // show all mines and disable the buttons
        for (int i = 0; i < numRows ;i++){
            for (int j = 0;j<numCols ;j++){
                if (cellGrid[i][j].hasMine()){
                    cellGrid[i][j].setIcon(-1);
                }
                cellGrid[i][j].disableButton();
            }
        }
    }


    // look at neighbors after cell clicked and set icon
    private void checkNeighbors(int row, int col, int[] neighbors){
        int numMinesAround = getNumMinesAround(row, col, neighbors);
        // update icon for this cell => 0=blank | 1-8=numberIcon
        if (cellGrid[row][col].getState() == "default"){
            cellGrid[row][col].setIcon(numMinesAround);
            numUncovered++;
        }
        if (numMinesAround == 0){ // if cell is blank
            explodeChain(row, col); // start explode chain (blank cells)
        }
    }

    // function returns the number of mines around a cell
    private int getNumMinesAround(int row, int col, int[] neighbors){
        int numMinesAround = 0;
        // iterate through the neighbors
        for (int i = 0 ; i < neighbors.length ; i=i+2){
            if (neighbors[i] != -1 && neighbors[i+1] != -1) {
                int n_row = neighbors[i];
                int n_col = neighbors[i + 1];

                if (cellGrid[n_row][n_col].hasMine()) { // if given neighbor is a mine
                    numMinesAround++; // increase num mines surrounding
                }
            }
        }
        return numMinesAround;
    }

    // function called when clicked cell is blank to start chain of blank cell reveals
    private void explodeChain(int row, int col) {
        int[] neighbors = getNeighbors(row, col); // neighbors of blank cell

        for (int i = 0; i < neighbors.length; i = i + 2) { // iterate through blank cell neighbors

            // make sure valid neighbors
            if (neighbors[i] != -1 && neighbors[i + 1] != -1){

                // check if this neighbor is blank ( no surrounding mines)
                int numMinesAround = getNumMinesAround(neighbors[i], neighbors[i + 1], getNeighbors(neighbors[i], neighbors[i + 1]));

                if ( numMinesAround== 0) {
                    // make sure the neighbor hasn't already been visited (hasn't been revealed)
                    if (cellGrid[neighbors[i]][neighbors[i + 1]].getState() == "default") {

                        if (!cellGrid[neighbors[i]][neighbors[i+1]].hasMine()) { // make sure cell isn't a mine.
                            cellGrid[neighbors[i]][neighbors[i + 1]].setIcon(0); // set icon to blank
                            numUncovered++;

                            explodeChain(neighbors[i], neighbors[i + 1]); // do same thing for this blank cell (recurse)
                        }
                    }
                }
                else if (cellGrid[neighbors[i]][neighbors[i + 1]].getState() == "default" && !cellGrid[neighbors[i]][neighbors[i + 1]].hasMine()){
                    cellGrid[neighbors[i]][neighbors[i+1]].setIcon(numMinesAround);
                    numUncovered++;
                }
            }
        }
    }

    // get the neighbors (rows, cols) of the given cell
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

        // for cells with fewer neighbors (edges), set the non-cells to -1
        for (int i = 0 ; i < 16 ; i=i+2){
            if (neighbors[i] < 0  || neighbors[i] >= numRows)
                neighbors[i] = -1;
            if(neighbors[i+1] >= numCols || neighbors[i+1] < 0)
                neighbors[i+1] = -1;
        }
        return neighbors;
    }
    // function that sets all mines on the grid (called after first click)
    private void setMines(int row, int col){
        System.out.println("Setting mines");
        ArrayList<Integer> minedCells = new ArrayList<>();
        ArrayList<Integer> neighborIDs = new ArrayList<>();
        int[] neighbors = getNeighbors(row, col); // get neighbors
        for (int i = 0 ; i < neighbors.length ; i+=2){
            if (neighbors[i] != -1 && neighbors[i+1] != -1)
                neighborIDs.add(cellGrid[neighbors[i]][neighbors[i+1]].getId());
        }


        // randomly pick mines
        while (minedCells.size() < numMines) {
            int randomID = (int) (Math.random() * numRows*numCols);

            if (!neighborIDs.contains(randomID) // first click neighbors aren't mines
                    && !minedCells.contains(randomID) // cell isn't already added to mine
                    && randomID != cellGrid[row][col].getId()) { // first click not mine
                minedCells.add(randomID);
            }
        }
        // add all valid mines to board
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                if (minedCells.contains(cellGrid[i][j].getId())){ // cells that were picked
                    cellGrid[i][j].setMine(true);
//                    cellGrid[i][j].setFlag();
                }
            }
        }
    }

    public int getGameStatus() {
        return gameStatus;
    }
    private void setUpTimers() {
        // timer object for counting how many seconds elapsed
        elapsedTimer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                time += 1;
                scoreboard.setTime(time);
                repaint();
            }
        }
        );
    }
    public void wait(int ms) {
        try {
            Thread.currentThread().sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
