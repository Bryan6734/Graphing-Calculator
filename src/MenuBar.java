
import javax.swing.*;
import java.awt.*;
import org.mariuszgromada.math.mxparser.*;

public class MenuBar {

    public int menuBarWidth;
    public int menuBarHeight;

    public Graph graph;
    public JPanel menuPanel;

    public JTextField expressionInput = new JTextField();

    public JButton button1 = new JButton("Button 1");
    public JButton button2 = new JButton("Button 2");

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

        menuPanel.add(button1);
        menuPanel.add(button2);

        menuPanel.add(clearPoints);
        menuPanel.add(clearLines);

        button1.addActionListener(e -> {

            Line line = new Line(2, 7);
            line.calculateRandomPoints(-10, 10, 20);
            line.calculateErrorPoints(10, false);
            graph.points = line.errorPoints;

        });

        expressionInput.addActionListener(e -> {

            System.out.println("expression input");
            System.out.println(expressionInput.getText());

            Expression expression = new Expression(expressionInput.getText());

            Line line = new Line(expression);
            line.calculateExpressionIncrementalPoints(-10, 10, 0.1);

            graph.points = line.points;

        });

        clearPoints.addActionListener(e -> {
            graph.clearPoints();
        });

        clearLines.addActionListener(e -> {
            graph.clearLines();
        });

    }




}
