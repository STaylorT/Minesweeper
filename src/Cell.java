import java.awt.*;
//import java.awt.event.*;
import javax.swing.*;
//import java.util.ArrayList;

public class Cell extends JButton{
    // is cell a mine?
    private static boolean mine = false;
    // position on grid
    private int rowPosition;
    private int colPosition;
    // unique id
    private int id = 0;
    private static int ID_COUNT = 0;
    // state of icon
    private String iconState = "default";

    //scoreboard
    Scoreboard scoreboard;
    // variables for possible cell icons
    private ImageIcon defaultIcon = new ImageIcon("images/square.png");
    private ImageIcon mineIcon = new ImageIcon("images/bomb.png");
    private ImageIcon flagIcon = new ImageIcon("images/flag.png");
    private ImageIcon numberIcons[];

    public Cell(int row_pos, int col_pos, int num_rows, int num_cols, Scoreboard score_board){
        rowPosition = row_pos;
        colPosition = col_pos;
        scoreboard = score_board;
        this.setIcon(defaultIcon);
        this.setBackground(Color.gray);
        setUpIcons();
        id = ID_COUNT;
        ID_COUNT++;
    }

    // set number icons 1-8 in array
    private void setUpIcons(){
        numberIcons = new ImageIcon[8];
        for (int i=0 ; i< numberIcons.length ; i++){
            numberIcons[i] = new ImageIcon("images/" + i+1 + ".png");
        }
    }

    // member function to edit whether cell is a mine
    public void setMine(boolean bool){
        mine = bool;
    }
    public int click(boolean isRightClick, Scoreboard scoreboard, boolean firstClick){ // -1 = game over | 0 = first click
        if (isRightClick){ // if the click was a right click
            // set flag icon if not already set
            if (iconState == "default"){
                setFlag();
            }
            else if (iconState == "flagged"){
                this.setIcon(defaultIcon);
                iconState = "default";
                scoreboard.addFlag();
            }
        }
        else{ // click was a left click (oooo)
            if (firstClick){ // user's first click
                return id;
            }
            else if (mine == true){ // user hit a mine, oops
                return -1;
            }
            else{ // user clicked any old cell
                clickCell();

            }
        }
        return 0;
    }

    private int clickCell(){ // handle clicking on cell
        return 1;
    }

    public void setFlag(){
        this.setIcon(flagIcon);
        iconState = "flagged";
        scoreboard.removeFlag();
    }

    // get ID of the cell in the grid
    public int getId(){
        return id;
    }
}
