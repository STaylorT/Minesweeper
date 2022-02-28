import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;

public class Menu extends JMenuBar
        implements ActionListener, ItemListener, ChangeListener{
    // grid size : 9x9=0  |  16x16=1   | 24x20=2   | custom = 10
    private int gridSize = 0;
    private int dimensions[] = {9,9,10}; // rows, cols, flags

    static final int MAX_ROW = 30;
    static final int MAX_COL = 24;
    static final int MIN_ROW = 9;
    static final int MIN_COL = 9;

    private JButton submit;

    private int menuState;

    // sliders
    JSlider cols;
    JSlider rows;
    JSlider mines;

    JLabel row_text = new JLabel("Rows");
    JLabel col_text = new JLabel("Columns");
    JLabel mine_text = new JLabel("Mine Percent");


    private double minePercent = .15;

    // boolean to see if change in menu occurred
    private static boolean changed;

    public Menu(){
        menuState = 0;
        // create first menu
        JMenu menu, menu2, submenu, customSubmenu, submenu2;
        JMenuItem menuItem;
        JRadioButtonMenuItem rbMenuItem;
        JCheckBoxMenuItem cbMenuItem;
        changed = false;

        menu = new JMenu("Options");
        menu.setMnemonic(KeyEvent.VK_A);
        menu.getAccessibleContext().setAccessibleDescription(
                "Configure game options.");
        this.add(menu);



        //a submenu
        menu.addSeparator();
        submenu = new JMenu("Select Size");
        submenu.setMnemonic(KeyEvent.VK_S);

        menuItem = new JMenuItem("Difficulty");
        submenu.add(menuItem);

        //a group of radio button menu items
        ButtonGroup group = new ButtonGroup();
        rbMenuItem = new JRadioButtonMenuItem("Beginner (9x9)");
        rbMenuItem.setSelected(true);
        group.add(rbMenuItem);
        submenu.add(rbMenuItem);
        rbMenuItem.addActionListener(this);

        rbMenuItem = new JRadioButtonMenuItem("Intermediate (16x16)");
        rbMenuItem.setMnemonic(KeyEvent.VK_O);
        group.add(rbMenuItem);
        submenu.add(rbMenuItem);
        rbMenuItem.addActionListener(this);

        rbMenuItem = new JRadioButtonMenuItem("Hard (24x20)");
        rbMenuItem.setMnemonic(KeyEvent.VK_O);
        group.add(rbMenuItem);
        submenu.add(rbMenuItem);
        rbMenuItem.addActionListener(this);

        //Create the sliders JSlider.VERTICAL,
        rows = new JSlider(JSlider.HORIZONTAL,
                MIN_ROW, MAX_ROW, 9);
        rows.addChangeListener(this);
        rows.setMajorTickSpacing(1);
        rows.setPaintTicks(true);
        rows.setPreferredSize(new Dimension(450, 80));

        // Create the slider
        cols = new JSlider(JSlider.HORIZONTAL,
                MIN_COL, MAX_COL, 9);
        cols.addChangeListener(this);
        cols.setMajorTickSpacing(1);
        cols.setPaintTicks(true);
        cols.setPreferredSize(new Dimension(450, 80));

        //Create the sliders JSlider.VERTICAL,
        mines = new JSlider(JSlider.HORIZONTAL,
                10, 25, 15);
        mines.addChangeListener(this);
        mines.setMajorTickSpacing(1);
        mines.setPaintTicks(true);
        mines.setPreferredSize(new Dimension(450, 80));

        // a Customsubmenu
        menu.addSeparator();
        customSubmenu = new JMenu("Custom Size");
        customSubmenu.addSeparator();

        menu.add(submenu);
        customSubmenu.add(row_text);
        customSubmenu.add(rows);
        customSubmenu.addSeparator();
        customSubmenu.add(col_text);
        customSubmenu.add(cols);
        customSubmenu.addSeparator();
        customSubmenu.add(mine_text);
        customSubmenu.add(mines);
        customSubmenu.addSeparator();

        submit = new JButton("Submit");
        customSubmenu.add(submit);
        submit.addActionListener(this);
        submenu.add(customSubmenu);



//                //Create the label table for rows
//        Hashtable labelTable1 = new Hashtable();
//        labelTable1.put( new Integer( 0 ), new JLabel("Min") );
//        labelTable1.put( new Integer( MAX_ROW/10 ), new JLabel("Mid") );
//        labelTable1.put( new Integer( MAX_ROW ), new JLabel("Max") );
//        rows.setLabelTable( labelTable1 );
//
//        //Create the label table
//        Hashtable labelTable2 = new Hashtable();
//        labelTable2.put( new Integer( 0 ), new JLabel("Min") );
//        labelTable2.put( new Integer( MAX_COL/10 ), new JLabel("Mid") );
//        labelTable2.put( new Integer( MAX_COL ), new JLabel("Max") );
//        cols.setLabelTable( labelTable2 );

        cols.setPaintLabels(true);
        rows.setPaintLabels(true);
        mines.setPaintLabels(true);
        // Quit Button
        menu.addSeparator();
        submenu2 = new JMenu("Game");
        group = new ButtonGroup();
        rbMenuItem = new JRadioButtonMenuItem("Restart");
        rbMenuItem.setSelected(false);
        group.add(rbMenuItem);
        submenu2.add(rbMenuItem);
        rbMenuItem.addActionListener(this);

        rbMenuItem = new JRadioButtonMenuItem("Quit");
        rbMenuItem.setMnemonic(KeyEvent.VK_O);
        group.add(rbMenuItem);
        submenu2.add(rbMenuItem);
        rbMenuItem.addActionListener(this);

        menu.add(submenu2);

        // Help Menu
        menu2 = new JMenu("Help");
        menuItem = new JMenuItem("Start by clicking any cell.",
                KeyEvent.VK_T);
        JMenuItem menuItem1 = new JMenuItem("A cell with the number X has X mines around that cell.",
                KeyEvent.VK_T);
        JMenuItem menuItem2 = new JMenuItem("Use this information to click all cells that ARE NOT mines.",
                KeyEvent.VK_T);
        JMenuItem menuItem3 = new JMenuItem("When only mine cells are left, you win!",
                KeyEvent.VK_T);

        menu2.add(menuItem);
        menu2.add(menuItem1);
        menu2.add(menuItem2);
        menu2.add(menuItem3);
        this.add(menu2);


    }

    public void stateChanged(ChangeEvent e) {
        JSlider source = (JSlider)e.getSource();
        dimensions[0] = rows.getValue();
        dimensions[1] = cols.getValue();
        minePercent = mines.getValue() * .01;
        dimensions[2] = (int) (minePercent * dimensions[0] * dimensions[1]);
    }

    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        //String actionSource = e.getSource();

        handleRadioButtons(actionCommand);

        if (actionCommand.contains("submit")){
            System.out.println("Submitted");
            gridSize = 10;
        }
        if (actionCommand.contains("Quit")){
            menuState = -1;
        }
        changed = true;

    }
    private void handleRadioButtons(String actionCommand){
        // decided which difficulty was selected and set corresponding gridSize variable
        if (actionCommand.contains("(9x9)")){
            gridSize = 0;
            dimensions[0] = 9;
            dimensions[1] = 9;
            dimensions[2] = 10;
            System.out.println("Easy Mode Selected");
        }
        else if (actionCommand.contains("(16x16)")){
            gridSize = 1;
            dimensions[0] = 16;
            dimensions[1] = 16;
            dimensions[2] = 40;
            System.out.println("Intermediate Mode Selected");
        }
        else if (actionCommand.contains("(24x20)")){
            dimensions[0] = 24;
            dimensions[1] = 20;
            dimensions[2] = 99;
            gridSize = 2;
            System.out.println("Hard Mode Selected");
        }

    }
    public void wait(int ms) {
        try {
            Thread.currentThread().sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public boolean getChanged(){
        wait(10);
        return changed;

    }
    public int[] getDimensions() {
        return dimensions;
    }

    public void setChanged(){
        changed = false;
    }
    public int getMenuState(){ // -1 is quit,  1 is restart
        return menuState;
    }


    public void itemStateChanged(ItemEvent e) {
        //...Get information from the item event...
        //...Display it in the text area...
    }
}
