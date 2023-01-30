import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

public class Main implements Runnable {

    public JFrame frame;
    public JPanel panel;
    public Canvas canvas;
    public BufferStrategy bufferStrategy;

    public JPanel menuBarPanel;
    public MenuBar menuBar;

    public int screenWidth = 1000;
    public int screenHeight = 700;

    public int menuPanelWidth = 250;
    public int graphPanelWidth = screenWidth - menuPanelWidth;

    public Graph graph;

    public Line modelLine;
    public Line initialLine;
    public Line adjustedLine;

    public static void main(String[] args){
        Main ex = new Main();
        new Thread(ex).start();
    }

    public Main(){
        setUpGraphics();

        // create a new graph
        graph = new Graph(graphPanelWidth, screenHeight);

        // create model line
        modelLine = new Line(2, 7);

        // calculate points for model line
        modelLine.calculateRandomPoints(-400, 400, 2500);

        // calculate error points from model line
        modelLine.calculateErrorPoints(500, false);

        // slope and yIntercept for new line
        double slope = Math.random()*1;
        double yIntercept = Math.random()*100;

        // initial line (red)
        initialLine = new Line(slope, yIntercept);
        initialLine.calculateRandomPoints(-400, 400, 2000);

        // adjusted line (green)
        adjustedLine = calculateAdjustedLines(slope, yIntercept, 30);

        // calculate points for adjusted line
        adjustedLine.calculateRandomPoints(-400, 400, 1000);



    }

    public Line calculateAdjustedLines(double slope, double yIntercept, int iterations){

        double bestSlope = 0;
        double bestYIntercept = 0;
        double bestAverageDeviation = 0;

        for (int i = 0; i < iterations; i++){

            Line testLine = new Line(slope, yIntercept);
//            testLine.adjustSlopeInterceptForm(modelLine.errorPoints, 0.1, 0.1);

            testLine.adjustSlope(modelLine.errorPoints, 0.1);
            testLine.adjustYIntercept(modelLine.errorPoints, 0.1);
            testLine.calculateAverageDeviation(modelLine.errorPoints);

            if (i == 0 || testLine.averageDeviation < bestAverageDeviation){
                bestSlope = testLine.slope;
                bestYIntercept = testLine.yIntercept;
                bestAverageDeviation = testLine.averageDeviation;
                System.out.println("---------------------");

                System.out.println(bestAverageDeviation);
            }
        }

        return new Line(bestSlope, bestYIntercept);

    }


    @Override
    public void run() {
        while (true){
            render();
            pause(60);
        }
    }

    private void setUpGraphics(){
        frame = new JFrame("Advanced Functions Graphing Calculator");

        panel = (JPanel) frame.getContentPane();
        panel.setPreferredSize(new Dimension(screenWidth, screenHeight));
        panel.setLayout(null);

        menuBarPanel = new JPanel();
        menuBar = new MenuBar(menuBarPanel, menuPanelWidth, screenHeight);
        menuBar.setUpMenuBar();
        panel.add(menuBar.menuPanel);


        canvas = new Canvas();
        canvas.setBounds(menuPanelWidth, 0, graphPanelWidth, screenHeight);
        canvas.setIgnoreRepaint(true);
        panel.add(canvas);




        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setResizable(true);
        frame.setVisible(true);

        canvas.createBufferStrategy(2);
        canvas.requestFocus();
        bufferStrategy = canvas.getBufferStrategy();

        canvas.requestFocus();
        System.out.println("Graphics Setup Completed");

    }

    public void render(){
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
        g.clearRect(0, 0, screenWidth, screenHeight);

        graph.drawGrid(g, graphPanelWidth, screenHeight, new Color(0, 0, 0, 25));
        graph.drawAxes(g, graphPanelWidth, screenHeight, Color.black);

        graph.drawLine(g, modelLine, Color.lightGray);
        graph.drawLine(g, initialLine, Color.red);
        graph.drawLine(g, adjustedLine, Color.green);

//        modelLine.drawPoints(this, g, Color.lightGray);
//        modelLine.drawErrorPoints(this, g, Color.orange);
//
//        initialLine.drawPoints(this, g, Color.red);
//
//        adjustedLine.drawPoints(this, g, Color.green);
//        adjustedLine.drawInfo(this, g, Color.black);


        g.dispose();
        bufferStrategy.show();
    }

    public void pause(int time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param errorPoints ArrayList of error points with (+/-) values
     * @param iterations Number of iterations
     * @return Fitted line
     */
    @Deprecated
    public Line calculateFittedLineDeprecated(ArrayList<Point> errorPoints, int iterations){

        double lowestSlope = 0;
        double lowestY_Intercept = 0;
        double lowestDeviation = 0;

        for (int i = 0; i < iterations; i++){

            // Select two random points from error points
            Point a = errorPoints.get((int)(Math.random()*errorPoints.size()));
            Point b = errorPoints.get((int)(Math.random()*errorPoints.size()));

            // Get slope and y-intercept from the two error points
            Line error_line = new Line(a, b);

            // Store first line's average deviation for future comparisons
            if (i == 0){
                error_line.calculateAverageDeviation(errorPoints);
                lowestDeviation = error_line.averageDeviation;
                continue;
            }

            // Calculate average deviation of the two error points
            error_line.calculateAverageDeviation(errorPoints);

            if (error_line.averageDeviation < lowestDeviation){

                System.out.println("––––––");
                System.out.println("[" + i + "] " + "Ave Dev: " + error_line.averageDeviation);
                System.out.println("–" + "Slope " + error_line.slope);
                System.out.println("–" + "Y-Intercept " + error_line.yIntercept);

                lowestSlope = error_line.slope;
                lowestY_Intercept = error_line.yIntercept;
                lowestDeviation = error_line.averageDeviation;
            }
        }
        return new Line(lowestSlope, lowestY_Intercept);
    }
}
