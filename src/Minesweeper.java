import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
//import java.awt.event.*;
import javax.swing.*;

public class Minesweeper extends JFrame {
    JPanel gridContainer;
    Grid grid; // grid where minesweeper occurs
    boolean firstClick = true;
    private Timer elapsedTimer; // timer to count elapsed time each game

    public Minesweeper(){
        super("Minesweeper");
        setUpTimers(); // instantiate timer

        // get the content pane, onto which everything is eventually added
        Container c = getContentPane();
        // get menu bar
        Menu menu = new Menu();
        c.add(menu,BorderLayout.NORTH );

        // get grid of cells, representing the minesweeper game
        gridContainer = new JPanel();
        gridContainer.setPreferredSize(new Dimension(400,400));
        newGame(9,9);
        c.add(gridContainer);

        setSize(400, 400);
        setVisible(true);
    }

    public void newGame(int rows, int cols){ // creates new grid given dimensions of new game
        // make new grid and add it to the grid container
        grid = new Grid(rows, cols);
        gridContainer.removeAll();
        gridContainer.add(grid);
        repaint();
    }

    public void setMines(int row, int col){ // set mines on grid given the user's first click
        // start clock

        int rows = grid.getDimensions()[0];
        int cols = grid.getDimensions()[1];


    }

    public static void main(String[] args) {
        Minesweeper game = new Minesweeper();
        System.out.print("Hello");

        game.addWindowListener(
                new WindowAdapter(){
                    public void windowClosing(WindowEvent e)
                    {
                        System.exit(0);
                    }
                }
        );
    }
    private void setUpTimers() {
        // timer object for counting how many seconds elapsed
        elapsedTimer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                time += 1;
                timeElapsed.setText(" | Time Elapsed: " + time);
                repaint();
            }
        }
        );
    }
}
