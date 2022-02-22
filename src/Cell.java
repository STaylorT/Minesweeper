import java.awt.*;
//import java.awt.event.*;
import javax.swing.*;
//import java.util.ArrayList;

public class Cell extends JButton{
    private static boolean mine = false; // bool designating whether cell is a mine
    private int rowPosition;
    private int colPosition;
    // variables for possible cell icons
    private ImageIcon defaultIcon = new ImageIcon();
    private ImageIcon mineIcon = new ImageIcon("images/bomb.png");
    private ImageIcon flagIcon = new ImageIcon();

    public Cell(int row_pos, int col_pos){
        rowPosition = row_pos;
        colPosition = col_pos;
        this.setIcon(defaultIcon);
        this.setBackground(Color.gray);
    }

    // member function to edit whether cell is a mine
    private static void setMine(boolean bool){
        mine = bool;
    }
}
