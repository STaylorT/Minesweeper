import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.Color;

public class Minesweeper extends JFrame
                    implements ActionListener, MouseListener{
    JPanel gridContainer;
    Grid grid; // grid where minesweeper occurs
    boolean firstClick = true;
    private int time;
    private Timer elapsedTimer; // timer to count elapsed time each game
    private Menu menu;
    private Scoreboard scoreboard;

    private static Minesweeper game;

    public Minesweeper(){
        super("Minesweeper");
        // initialize new game setup
        newGame(9,9, 10);

    }

    // creates new grid given dimensions of new game
    public void newGame(int rows, int cols, int flags){
        setUpTimers(); // instantiate timer

        // get the content pane, onto which everything is eventually added
        Container c = getContentPane();
        c.removeAll();
//        c.setLayout(new BorderLayout());
        // get menu bar
        menu = new Menu();
        c.add(menu,BorderLayout.PAGE_START);

        // set up a score board
        scoreboard = new Scoreboard(rows, cols, flags);
        c.add(scoreboard,BorderLayout.LINE_START);

        // get grid of cells, representing the minesweeper game
        gridContainer = new JPanel();
        gridContainer.setPreferredSize(new Dimension(rows*30,cols*30));

        // make new grid and add it to the grid container
        grid = new Grid(rows, cols, scoreboard, flags);
        gridContainer.removeAll();
        gridContainer.add(grid, BorderLayout.LINE_END);
        repaint();
        revalidate();
        c.add(gridContainer);

        setSize(700, 800);
        setVisible(true);
    }

    public void mousePressed(MouseEvent e ){}
    public void mouseReleased(MouseEvent e ){}
    public void mouseEntered(MouseEvent e ){}
    public void mouseExited(MouseEvent e ){}
    public void mouseClicked(MouseEvent e){
        System.out.println("CLICKED LOLOL");
    }

    public static void main(String[] args) {
        boolean quit = false;
        game = new Minesweeper();
        while (quit == false){
            if (game.menu.getChanged() == true){// check if menu item changed
                int rows = game.menu.getDimensions()[0];
                int cols = game.menu.getDimensions()[1];
                int flags = game.menu.getDimensions()[2];
                game.newGame(rows, cols, flags);
            }
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
    public void actionPerformed(ActionEvent e) {
        System.out.println("Action performed on menu in MAIN THING");

    }
    private void setUpTimers() {
        // timer object for counting how many seconds elapsed
        elapsedTimer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                time += 1;
                game.scoreboard.setTime(time);
                repaint();
            }
        }
        );
    }
}
