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
    public Line fittedLine;

    public static void main(String[] args){
        Main ex = new Main();
        new Thread(ex).start();
    }

    public Main(){

        setUpGraphics();

        line = new Line(2, 7);
        line.calculatePoints(0, 800, 1000);
        line.calculateErrorPoints(200, false);

        fittedLine = calculateFittedLine(line.error_points, 100000);
        fittedLine.calculateAverageDeviation(line.error_points);
        fittedLine.calculatePoints(0, 800, 10000);

    }

    /**
     *
     * @param error_points ArrayList of error points with (+/-) values
     * @param iterations Number of iterations
     * @return Fitted line
     */
    public Line calculateFittedLine(ArrayList<Point> error_points, int iterations){

        double lowestSlope = 0;
        double lowestY_Intercept = 0;
        double lowestDeviation = 0;

        for (int i = 0; i < iterations; i++){

            // Select two random points from error points
            Point a = error_points.get((int)(Math.random()*error_points.size()));
            Point b = error_points.get((int)(Math.random()*error_points.size()));

            // Get slope and y-intercept from the two error points
            Line error_line = calculateEquation(a, b);

            // Store first line's average deviation for future comparisons
            if (i == 0){
                error_line.calculateAverageDeviation(error_points);
                lowestDeviation = error_line.ave_dev;
                continue;
            }

            // Calculate average deviation of the two error points
            error_line.calculateAverageDeviation(error_points);

            if (error_line.ave_dev < lowestDeviation){

                System.out.println("––––––");
                System.out.println("[" + i + "] " + "Ave Dev: " + error_line.ave_dev);
                System.out.println("–" + "Slope " + error_line.slope);
                System.out.println("–" + "Y-Intercept " + error_line.y_intercept);

                lowestSlope = error_line.slope;
                lowestY_Intercept = error_line.y_intercept;
                lowestDeviation = error_line.ave_dev;
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

        // line.drawPoints(this, g, Color.blue)
        line.drawErrorPoints(this, g, Color.orange);

        fittedLine.drawPoints(this, g, Color.red);
        fittedLine.drawInfo(this, g, Color.orange);

        g.dispose();
        bufferStrategy.show();
    }

    @Override
    public void run() {
        while (true){
            render();
            pause(60);
        }

    }

    public void pause(int time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
