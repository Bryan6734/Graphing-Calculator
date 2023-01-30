import javax.swing.*;
import java.awt.*;

public class MenuBar {

    public int menuBarWidth;
    public int menuBarHeight;

    public Graph graph;
    public JPanel menuPanel;

    public JTextField slopeInput = new JTextField();
    public JTextField yInterceptInput = new JTextField();
    public JTextField errorInput = new JTextField();

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
        menuPanel.setLayout(new GridLayout(4, 2));

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

        // make the submit button span two columns
        JButton submitButton = new JButton("Submit");
        menuPanel.add(submitButton);

        // make the submit button span two columns
        JButton clearButton = new JButton("Clear");
        menuPanel.add(clearButton);

        submitButton.addActionListener(e -> {
            System.out.println("Slope: " + slopeInput.getText());
            System.out.println("Y-Intercept: " + yInterceptInput.getText());
            System.out.println("Error: " + errorInput.getText());

            double slope = Double.parseDouble(slopeInput.getText());
            double yIntercept = Double.parseDouble(yInterceptInput.getText());
//            double error = Double.parseDouble(errorInput.getText());

            Line line = new Line(slope, yIntercept);
            line.calculateRandomPoints(-400, 400, 10);
            graph.addLine(line);

        });

        clearButton.addActionListener(e -> {
            graph.clearLines();
        });

    }




}
