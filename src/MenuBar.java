import javax.swing.*;
import java.awt.*;

public class MenuBar {

    public int menuBarWidth;
    public int menuBarHeight;

    public JPanel menuPanel;

    public MenuBar(JPanel menuPanel, int menuBarWidth, int menuBarHeight){
        this.menuPanel = menuPanel;
        this.menuBarWidth = menuBarWidth;
        this.menuBarHeight = menuBarHeight;
    }

    public void setUpMenuBar(){
        menuPanel.setBounds(0, 0, menuBarWidth, menuBarHeight);
        menuPanel.setBackground(Color.lightGray);
        menuPanel.setLayout(new GridLayout(10, 1));
    }

}
