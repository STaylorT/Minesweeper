import javax.swing.*;
import java.awt.*;

public class Scoreboard extends JPanel {
    // flag icon + # of flags remaining
    private JLabel flags_score;
    private int flags = 0;
    private JButton flag_icon = new JButton(new ImageIcon("images/flag.png"));
    // clock icon + time elapsed
    private JLabel elapsedTime;
    private JButton clock_icon = new JButton(new ImageIcon("images/clock.png"));
    private int time = 0;

    public Scoreboard(int rows, int cols, int numFlags){
        flags = numFlags; // count total flags not placed
        flag_icon.setPreferredSize(new Dimension(30,30));
        clock_icon.setPreferredSize(new Dimension(30,30));


        // set Scoreboard Dimensions
        this.setSize(30*cols, 100);
        this.setLayout(new FlowLayout());

        // add flag icon and number
        this.add(flag_icon);
        flags_score = new JLabel("Flags: " + flags);
        this.add(flags_score);

        // add timer icon and times passed
        this.add(clock_icon);
        elapsedTime = new JLabel("" + time);
        this.add(elapsedTime);

    }

    public int getFlags(){
        return flags;
    }
    public void removeFlag(){
        flags--;
        flags_score.setText("" + flags);
    }
    public void addFlag(){
        flags++;
        flags_score.setText("" + flags);
    }
    public void setTime(int newTime){
        time = newTime;
        elapsedTime.setText("" + time);
    }

}
