import java.awt.*;
//import java.awt.event.*;
import javax.swing.*;
//import java.util.ArrayList;


public class Cell extends JButton{
    // is cell a mine?
    private boolean mine = false;
    // position on grid
    private int rowPosition;
    private int colPosition;

    private int numRows;
    private int numCols;

    private int CELL_SIZE;
    // unique id
    private int id = 0;
    private static int ID_COUNT = 0;
    // state of icon => "default"  |  "flagged"   |  "mine"  | "blank"  |  "num"
    private String iconState = "default";

    //scoreboard
    Scoreboard scoreboard;
    // variables for possible cell icons
    private ImageIcon defaultIcon = new ImageIcon("images/square.png");
    private ImageIcon mineIcon = new ImageIcon("images/bomb.png");
    private ImageIcon flagIcon = new ImageIcon("images/flag.png");
    private ImageIcon noMineCell = new ImageIcon("images/nomine.png");
    private ImageIcon numberIcons[];

    public Cell(int row_pos, int col_pos, int num_rows, int num_cols, Scoreboard score_board, int cellSize){
        rowPosition = row_pos;
        colPosition = col_pos;
        numCols = num_cols;
        numRows = num_rows;
        scoreboard = score_board;
        CELL_SIZE= cellSize;
        this.setBackground(Color.gray);
        setUpIcons();

        this.setIcon(defaultIcon);
        // set id's for cells
        ID_COUNT++;
        id = ID_COUNT;
        if (ID_COUNT == num_rows * num_cols) // if we have filled the grid, reset id_count
            ID_COUNT = 0;
    }

    // set number icons 1-8 in array
    private void setUpIcons(){
        numberIcons = new ImageIcon[8];
        if (CELL_SIZE < 30){
            resizeImages(CELL_SIZE);
            return;
        }
        for (int i=0 ; i< numberIcons.length ; i++){
            int b = i+1;
            numberIcons[i] = new ImageIcon("images/" + b + ".png");
        }
    }
    private void resizeImages(int cellSize){
        defaultIcon = new ImageIcon(((new ImageIcon("images/square.png")).getImage()).getScaledInstance(cellSize, cellSize, java.awt.Image.SCALE_SMOOTH));
        mineIcon = new ImageIcon(((new ImageIcon("images/bomb.png")).getImage()).getScaledInstance(cellSize, cellSize, java.awt.Image.SCALE_SMOOTH));
        flagIcon = new ImageIcon(((new ImageIcon("images/flag.png")).getImage()).getScaledInstance(cellSize, cellSize, java.awt.Image.SCALE_SMOOTH));
        noMineCell = new ImageIcon(((new ImageIcon("images/nomine.png")).getImage()).getScaledInstance(cellSize, cellSize, java.awt.Image.SCALE_SMOOTH));
        for (int i=0 ; i< numberIcons.length ; i++){
            int b = i+1;
            numberIcons[i] = new ImageIcon(((new ImageIcon("images/" + b + ".png")).getImage()).getScaledInstance(cellSize, cellSize, java.awt.Image.SCALE_SMOOTH));
        }
    }

    // member function to edit whether cell is a mine
    public void setMine(boolean bool){
        mine = bool;
    }

    public int click(boolean isRightClick, Scoreboard scoreboard, boolean firstClick){ // -1 = game over | 0 = first click
        if (isRightClick){ // if the click was a right click
            // handle setting flag
            setFlag();
        }
        else if (iconState != "flagged"){ // click was a left click (oooo)
            if (firstClick){ // user's first click
                return id; // return the id of the cell clicked
            }
            else if (mine == true){ // user hit a mine, oops
                return -1;
            }
            else if (this.isEnabled()){ // user clicked any old cell
                return 0;
            }
        }
        return -2;
    }

    public void setFlag(){
        if (iconState == "default"){
            this.setDisabledIcon(flagIcon);
            this.setEnabled(false);
            iconState = "flagged";
            scoreboard.removeFlag();
            return;
        }
        else if (iconState == "flagged"){
            this.setEnabled(true);
            this.setIcon(defaultIcon);
            iconState = "default";
            scoreboard.addFlag();
            return;
        }
    }

    // get ID of the cell in the grid
    public int getId(){
        return id;
    }
    public void setIcon(int code){
        if (code > 0){ // cell is 1-8
            this.setDisabledIcon(numberIcons[code-1]);
            this.setEnabled(false);
            iconState = "num";
        }
        else if (code == 0){ // cell is blank
            this.setDisabledIcon(noMineCell);
            this.setEnabled(false);
            iconState = "blank";
        }
        else if (code == -1){ // cell is mine
            this.setDisabledIcon(mineIcon);
            this.setEnabled(false);
            iconState = "mine";
        }
    }
    public void disableButton(){
//        setDisabledIcon(this.getIcon());
//        System.out.print(this.getIcon());
        this.setEnabled(false);
    }

    public boolean hasMine(){ // for classes to see if this cell is a mine
        return mine;
    }

    public String getState(){ // for classes to see the state of the cell
        return iconState;
    }

}
