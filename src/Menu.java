//import java.awt.*;
//import java.awt.event.WindowAdapter;
//import java.awt.event.WindowEvent;
import java.awt.event.*;
import javax.swing.*;
public class Menu extends JMenuBar implements ActionListener, ItemListener {
    // grid size : 9x9=0  |  16x16=1   | 24x20=2   | custom = 3
    private int gridSize = 0;
    private int customGrid[] = {0,0};

    public Menu(){
        // create first menu
        JMenu menu, submenu;
        JMenuItem menuItem;
        JRadioButtonMenuItem rbMenuItem;
        JCheckBoxMenuItem cbMenuItem;

        menu = new JMenu("Options");
        menu.setMnemonic(KeyEvent.VK_A);
        menu.getAccessibleContext().setAccessibleDescription(
                "Configure game options.");
        this.add(menu);

        // a group of JMenuItems
        menuItem = new JMenuItem("A text-only menu item",
                KeyEvent.VK_T);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_1, ActionEvent.ALT_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription(
                "This doesn't really do anything");
        menu.add(menuItem);

        menu.addSeparator();
        cbMenuItem = new JCheckBoxMenuItem("Another one");
        cbMenuItem.setMnemonic(KeyEvent.VK_H);
        menu.add(cbMenuItem);

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

        rbMenuItem = new JRadioButtonMenuItem("Custom");
        rbMenuItem.setMnemonic(KeyEvent.VK_O);
        group.add(rbMenuItem);
        submenu.add(rbMenuItem);
        menu.add(submenu);
        rbMenuItem.addActionListener(this);
    }
    private void handleRadioButtons(String actionCommand){
        // decided which difficulty was selected and set corresponding gridSize variable
        if (actionCommand.contains("(9x9)")){
            gridSize = 0;
            System.out.println("Easy Mode Selected");
        }
        else if (actionCommand.contains("(16x16)")){
            gridSize = 1;
            System.out.println("Intermediate Mode Selected");
        }
        else if (actionCommand.contains("(24x20)")){
            System.out.println("Hard Mode Selected");
            gridSize = 2;
        }
        else if (actionCommand.contains("custom")){
            System.out.println("Custom selected");
            gridSize = 3;
        }
    }
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        //String actionSource = e.getSource();

        handleRadioButtons(actionCommand);

    }

    public void itemStateChanged(ItemEvent e) {
        //...Get information from the item event...
        //...Display it in the text area...
    }
}
