import javax.swing.*;
import java.awt.*;

public class MenuBar {

    public int menuBarWidth;
    public int menuBarHeight;

    public Graph graph;
    public JPanel menuPanel;

    public JTextArea titleTextArea = new JTextArea("Advanced Functions Grapher");
    public JTextArea introLabel = new JTextArea("This program is a grapher equipped with various common data analysis algorithms.");

    public JTextField slopeInput = new JTextField();
    public JTextField yInterceptInput = new JTextField();
    public JTextField errorInput = new JTextField();

    public JButton gradientDescentButton = new JButton("Gradient Descent");
    public JButton kmeansClusteringButton = new JButton("K-Means Algorithm");

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


        JLabel slopeLabel = new JLabel("Slope");
        slopeLabel.setHorizontalAlignment(JLabel.CENTER);

        menuPanel.add(slopeLabel);
        menuPanel.add(slopeInput);

        JLabel yInterceptLabel = new JLabel("Y-Intercept");
        yInterceptLabel.setHorizontalAlignment(JLabel.CENTER);

        menuPanel.add(yInterceptLabel);
        menuPanel.add(yInterceptInput);

        JLabel errorLabel = new JLabel("Error");
        errorLabel.setHorizontalAlignment(JLabel.CENTER);

        menuPanel.add(errorLabel);
        menuPanel.add(errorInput);

        menuPanel.add(gradientDescentButton);
        menuPanel.add(kmeansClusteringButton);

        menuPanel.add(clearPoints);
        menuPanel.add(clearLines);

        gradientDescentButton.addActionListener(e -> {

            if (graph.points.size() < 2) {
                JOptionPane.showMessageDialog(null, "Must have at least 2 points");
                return;
            }

            try {
                String userInput = JOptionPane.showInputDialog("Enter the number of iterations (>500, default 1000)");
                int iterations = userInput.equals("") ? 1000 : Integer.parseInt(userInput);
                Line line = graph.gradientDescent(0.0001, iterations);
                line.calculateRandomPoints(-400, 400, 10);
                graph.addLine(line);

            } catch (NumberFormatException exception) {
                JOptionPane.showMessageDialog(null, "Please enter a valid number");
            }

        });

        clearPoints.addActionListener(e -> {
            graph.clearPoints();
        });

        clearLines.addActionListener(e -> {
            graph.clearLines();
        });

    }




}
