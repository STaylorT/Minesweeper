import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.Color;
import java.lang.reflect.ReflectPermission;
import java.nio.ByteOrder;

public class Minesweeper extends JFrame
                    implements ActionListener{
    JPanel gridContainer;
    Grid grid; // grid where minesweeper occurs
    private Menu menu;
    private Scoreboard scoreboard;

    // for end game messages
    private JPanel endGameBoard = new JPanel();
    private JLabel message1;

    // cell size
    private int CELL_SIZE = 30;

    private static Minesweeper game;

    Container c = getContentPane();

    public Minesweeper(){
        super("Minesweeper");
        // initialize new game setup

        c.setLayout(new BorderLayout());
        menu = new Menu();
        c.add(menu,BorderLayout.PAGE_START);

        // set up a score board
        scoreboard = new Scoreboard(9, 9, 10);
        c.add(scoreboard,BorderLayout.PAGE_END);

        // get grid of cells
        gridContainer = new JPanel();
        gridContainer.setPreferredSize(new Dimension(9*CELL_SIZE,9*CELL_SIZE));

        // make new grid and add it to the grid container
        grid = new Grid(9, 9, scoreboard, 10, CELL_SIZE);
        gridContainer.add(grid, BorderLayout.CENTER);
        c.add(gridContainer);

        setSize(9*CELL_SIZE + 30,9*CELL_SIZE + 110);
        setVisible(true);
    }

    // creates new grid given dimensions of new game
    public void newGame(int rows, int cols, int flags){
        c.remove(endGameBoard);
        if (rows >= 25 || cols>=22){ // change size of cells if grid large
            CELL_SIZE = 24;
        }
        else if(rows >=22 || cols >= 20){
            CELL_SIZE = 27;
        }
        else
            CELL_SIZE = 30;
        // get grid of cells, representing the minesweeper game
        c.remove(gridContainer);
        gridContainer = new JPanel();
        gridContainer.setPreferredSize(new Dimension(cols*CELL_SIZE,rows*CELL_SIZE));
        c.add(gridContainer);

        // set up a new score board
        c.remove(scoreboard);
        scoreboard = new Scoreboard(rows, cols, flags);

        // make new grid and add it to the grid container
        grid = new Grid(rows, cols, scoreboard, flags, CELL_SIZE);
        gridContainer.add(grid, BorderLayout.CENTER);


        c.add(scoreboard, BorderLayout.PAGE_END);

        setSize(cols*CELL_SIZE + 30,rows*CELL_SIZE + 110);
        setVisible(true);
        repaint();
        revalidate();
    }
    public void gameOver(int code){

        // instantiate end game UI
        endGameBoard.removeAll();
        endGameBoard.setPreferredSize(new Dimension(75,50));
        endGameBoard.setLayout(new BorderLayout());
        if (code == 1){
            System.out.println("YOU WON");
            message1 = new JLabel("You won!");
        }
        else if (code == 0){
            System.out.println("YOU LOST");
            message1 = new JLabel("You Lost!");
        }
        endGameBoard.add(message1, BorderLayout.CENTER);
        c.add(endGameBoard, BorderLayout.LINE_START);
        setSize(getWidth()+75,getHeight());
        c.repaint();
        c.revalidate();

    }

    public static void main(String[] args) {
        boolean quit = false;
        boolean gameEnded = false;
        game = new Minesweeper();
        while (quit == false){

            if (game.menu.getChanged() == true){// check if menu item changed
                if (game.menu.getMenuState() == -1){
                    System.exit(0);
                }
                int rows = game.menu.getDimensions()[0];
                int cols = game.menu.getDimensions()[1];
                int flags = game.menu.getDimensions()[2];
                System.out.println("Rows for new game:" + rows);
                System.out.println("Cols for new game:" + cols);
                System.out.println("Flags for new game:" + flags);
                game.newGame(rows, cols, flags);
                game.menu.setChanged();
                gameEnded = false;
            }
            else if (game.grid.getGameStatus() >= 0 && !gameEnded){ // game lost = 0  | game won = 1
                game.gameOver(game.grid.getGameStatus());
                gameEnded = true;
            }
            game.addWindowListener(
                    new WindowAdapter(){
                        public void windowClosing(WindowEvent e)
                        {
                            System.exit(0);
                        }
                    }
            );
        }


    }
    public void actionPerformed(ActionEvent e) {
        System.out.println("Action performed on menu in MAIN THING");

    }

}
