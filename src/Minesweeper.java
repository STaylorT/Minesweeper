import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.Color;

public class Minesweeper extends JFrame
                    implements ActionListener{
    JPanel gridContainer;
    Grid grid; // grid where minesweeper occurs
    private Menu menu;
    private Scoreboard scoreboard;

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
        gridContainer.setPreferredSize(new Dimension(9*30,9*30));

        // make new grid and add it to the grid container
        grid = new Grid(9, 9, scoreboard, 10);
        gridContainer.add(grid, BorderLayout.CENTER);
        c.add(gridContainer);

        setSize(9*30 + 30,9*30 + 110);
        setVisible(true);
    }

    // creates new grid given dimensions of new game
    public void newGame(int rows, int cols, int flags){

        // get grid of cells, representing the minesweeper game
        c.remove(gridContainer);
        gridContainer = new JPanel();
        gridContainer.setPreferredSize(new Dimension(cols*30,rows*30));
        c.add(gridContainer);

        // set up a new score board
        c.remove(scoreboard);
        scoreboard = new Scoreboard(rows, cols, flags);

        // make new grid and add it to the grid container
        grid = new Grid(rows, cols, scoreboard, flags);
        gridContainer.add(grid, BorderLayout.CENTER);


        c.add(scoreboard, BorderLayout.PAGE_END);

        setSize(cols*30 + 30,rows*30 + 110);
        setVisible(true);
        repaint();
        revalidate();
    }
    public static void gameOver(int code){
        if (code == 1){
            System.out.println("YOU WON");
        }
        else if (code == -1){
            System.out.println("YOU LOST");
        }

    }

    public static void main(String[] args) {
        boolean quit = false;
        game = new Minesweeper();
        while (quit == false){

            if (game.menu.getChanged() == true){// check if menu item changed
                int rows = game.menu.getDimensions()[0];
                int cols = game.menu.getDimensions()[1];
                int flags = game.menu.getDimensions()[2];
                System.out.println("Rows for new game:" + rows);
                System.out.println("Cols for new game:" + cols);
                System.out.println("Flags for new game:" + flags);
                game.newGame(rows, cols, flags);
                game.menu.setChanged();
            }
            else if (game.grid.getGameStatus() >= 0){ // game lost or won
                gameOver(game.grid.getGameStatus());
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
