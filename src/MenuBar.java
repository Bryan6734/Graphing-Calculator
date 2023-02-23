
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import org.mariuszgromada.math.mxparser.*;

public class MenuBar {

    public int menuBarWidth;
    public int menuBarHeight;

    public Graph graph;
    public JPanel menuPanel;

    public JTextField expressionInput = new JTextField();

    public JButton button1 = new JButton("Button 1");
    public JButton button2 = new JButton("Button 2");
    public JButton button3 = new JButton("Button 3");
    public JButton button4 = new JButton("Button 4");
    public JButton button5 = new JButton("Button 5");
    public JButton button6 = new JButton("Button 6");

    public JButton clearPoints = new JButton("Clear Points");
    public JButton clearLines = new JButton("Clear Lines");

    public MenuBar(Graph graph, JPanel menuPanel, int menuBarWidth, int menuBarHeight){
        this.graph = graph;
        this.menuPanel = menuPanel;
        this.menuBarWidth = menuBarWidth;
        this.menuBarHeight = menuBarHeight;

        setUpMenuBarPanel();
        addMenuBarComponents();
    }

    public void setUpMenuBarPanel(){

        menuPanel.setBounds(0, 0, menuBarWidth, menuBarHeight);
        menuPanel.setBackground(Color.lightGray);
        menuPanel.setLayout(new GridLayout(5, 2));

    }

    public void addMenuBarComponents(){


        JLabel expressionLabel = new JLabel("Expression");
        expressionLabel.setHorizontalAlignment(JLabel.CENTER);

        menuPanel.add(expressionLabel);
        menuPanel.add(expressionInput);

        menuPanel.add(clearPoints);
        menuPanel.add(clearLines);

        menuPanel.add(button1);
        menuPanel.add(button2);
        menuPanel.add(button3);
        menuPanel.add(button4);
        menuPanel.add(button5);
        menuPanel.add(button6);


        button1.addActionListener(e -> {

        });

        button2.addActionListener(e -> {


        });

        button3.addActionListener(e -> {



        });

        button4.addActionListener(e -> {

        });

        button5.addActionListener(e -> {

        });

        button6.addActionListener(e -> {

        });

        expressionInput.addActionListener(e -> {

            System.out.println("expression input");
            System.out.println(expressionInput.getText());

            Expression expression = new Expression(expressionInput.getText());

            Line line = new Line(expression);
            line.computePoints(-15, 15, 0.1);

            graph.addLine(line);

        });

        clearPoints.addActionListener(e -> {
            graph.clearPoints();
        });

        clearLines.addActionListener(e -> {
            graph.clearLines();
        });

    }




}
