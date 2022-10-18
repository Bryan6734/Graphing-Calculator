import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

public class Main implements Runnable {

    public JFrame frame;
    public JPanel panel;
    public Canvas canvas;
    public BufferStrategy bufferStrategy;

    public int screenWidth = 700;
    public int screenHeight = 700;

    public Line modelLine;
    public Line initialLine;
    public Line adjustedLine;
    public ArrayList<Line> adjustedLines;

    public static void main(String[] args){
        Main ex = new Main();
        new Thread(ex).start();
    }

    public Main(){
        setUpGraphics();

        System.out.println("Main started");
        modelLine = new Line(2, 7);
        modelLine.calculatePoints(-400, 400, 2500);
        modelLine.calculateErrorPoints(500, false);
        System.out.println("Calculated main line");

        double slope = Math.random()*4;
        double yIntercept = Math.random()*100;

        initialLine = new Line(slope, yIntercept);
        initialLine.calculatePoints(-400, 400, 2000);

        adjustedLine = calculateAdjustedLines(slope, yIntercept, 10);
        adjustedLine.calculatePoints(-400, 400, 1000);

    }

    public Line calculateAdjustedLines(double slope, double yIntercept, int iterations){

        double bestSlope = 0;
        double bestYIntercept = 0;
        double bestAverageDeviation = 0;

        for (int i = 0; i < iterations; i++){

            Line testLine = new Line(slope, yIntercept);
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
        frame = new JFrame("Advanced Functions Graphing Calculator: By Bryan Sukidi");

        panel = (JPanel) frame.getContentPane();
        panel.setPreferredSize(new Dimension(screenWidth,screenHeight));
        panel.setLayout(null);

        canvas = new Canvas();
        canvas.setBounds(0, 0, screenWidth, screenHeight);
        canvas.setIgnoreRepaint(true);

        panel.add(canvas);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setResizable(false);
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
//
        g.drawOval(screenWidth / 2, screenHeight / 2, 4, 4);
        g.drawLine(0, screenHeight / 2, screenWidth, screenHeight / 2);
        g.drawLine(screenWidth / 2, 0, screenHeight / 2, screenHeight);


        modelLine.drawPoints(this, g, Color.lightGray);
        modelLine.drawErrorPoints(this, g, Color.orange);

        initialLine.drawPoints(this, g, Color.red);

        adjustedLine.drawPoints(this, g, Color.green);
        adjustedLine.drawInfo(this, g, Color.black);


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
