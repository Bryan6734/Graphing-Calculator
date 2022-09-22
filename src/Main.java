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

    public Line line;
    public Line initialLine;
    public Line adjustedLine;

    public static void main(String[] args){
        Main ex = new Main();
        new Thread(ex).start();
    }

    public Main(){
        setUpGraphics();

        System.out.println("Main started");
        line = new Line(2, 7);
        line.calculatePoints(-400, 400, 2500);
        line.calculateErrorPoints(500, false);
        System.out.println("Calculated main line");

        // Calculate deviations for (+) increment and (-) increment
        // Choose whichever increment results in a lower deviation
        // Repeat until both (+) increment and (-) increment result in higher deviations -> break from loop
        // Proceed with y-intercept

        double slope = Math.random()*4;
        double yIntercept = Math.random()*100;

        initialLine = new Line(slope, yIntercept);
        initialLine.calculatePoints(-400, 400, 2000);

        adjustedLine = new Line (slope, yIntercept);
        adjustedLine.calculateAverageDeviation(line.errorPoints);

        for (int i = 0; i < 10; i++){
            adjustedLine.adjustSlope(line.errorPoints, 0.1);
            adjustedLine.adjustYIntercept(line.errorPoints, 0.1);
        }

        adjustedLine.calculatePoints(-400, 400, 1000);

    }

    @Override
    public void run() {
        while (true){
            render();
            pause(60);
        }
    }

    /**
     *
     * @param errorPoints ArrayList of error points with (+/-) values
     * @param iterations Number of iterations
     * @return Fitted line
     */
    public Line calculateFittedLine(ArrayList<Point> errorPoints, int iterations){

        double lowestSlope = 0;
        double lowestY_Intercept = 0;
        double lowestDeviation = 0;

        for (int i = 0; i < iterations; i++){

            // Select two random points from error points
            Point a = errorPoints.get((int)(Math.random()*errorPoints.size()));
            Point b = errorPoints.get((int)(Math.random()*errorPoints.size()));

            // Get slope and y-intercept from the two error points
            Line error_line = calculateEquation(a, b);

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



    /**
     *
     * @param a Point a with (x, y)
     * @param b Point b with (x, y)
     * @return New line with given points
     */
    public Line calculateEquation(Point a, Point b){

        double slope = (a.y - b.y) / (a.x - b.x);
        double y_intercept = a.y - (a.x * slope);

        return new Line(slope, y_intercept);
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


        line.drawPoints(this, g, Color.lightGray);
        line.drawErrorPoints(this, g, Color.orange);

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
}
