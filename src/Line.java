import java.awt.*;
import java.util.ArrayList;

public class Line {

    double slope;
    double y_intercept;
    double ave_dev;

    ArrayList<Point> points = new ArrayList<>();
    ArrayList<Point> error_points = new ArrayList<>();

    public Line(double slope, double y_intercept){
        this.slope = slope;
        this.y_intercept = y_intercept;
    }

    /**
     *
     * @param x Value for coordinate x
     * @return Y value for given x coordinate
     */
    public double getY(double x){
        return slope * x + y_intercept;
    }

    /**
     *
     * @param lb Lower bound for random generator
     * @param ub Upper bound for random generator
     * @param n_points Number of points to generate within given bounds
     */
    public void calculatePoints(int lb, int ub, int n_points){
        for (int i = 0; i < n_points; i++){
            int x = ((int)(Math.random() * (ub - lb)) + lb);
            points.add(new Point(x, getY(x)));
        }
    }

    /**
     *
     * @param error Error value (+/-) for coordinates given as int or percent
     * @param isPercent Boolean to calculate error as int or percent
     */
    public void calculateErrorPoints(int error, boolean isPercent){
        for (Point point : points){
            if (isPercent){
                error_points.add(new Point(point.x, (int)(point.y * (1 + (Math.random() * error * 2 - error) / 100))));
            }
            else {
                error_points.add(new Point(point.x, point.y + (int)(Math.random()*(2 * error) - error)));
            }
        }
    }

    /**
     *
     * @param error_points ArrayList of error points with (+/-) values to be compared to
     */
    public void calculateAverageDeviation(ArrayList<Point> error_points){
        for (Point error_point : error_points) {
            ave_dev += Math.pow((error_point.y - getY(error_point.x)), 2);
        }
        ave_dev /= error_points.size();
    }

    /**
     *
     * @param g Java Graphics2D rendering engine
     * @param color Desired color
     */
    public void drawPoints(Main main, Graphics2D g, Color color){
        g.setColor(color);
        for (Point point : points){
            g.fillOval((int)point.x, main.screenHeight - (int)point.y, 4, 4);
        }
    }

    /**
     *
     * @param g Java Graphics2D rendering engine
     * @param color Desired color
     */
    public void drawErrorPoints(Main main, Graphics2D g, Color color){
        g.setColor(color);
        for (Point point : error_points){
            g.fillOval((int)point.x, main.screenHeight - (int)point.y, 4, 4);
        }
    }

    /**
     *
     * @param main Main class for screenWidth and screenHeight variables
     * @param g Java Graphics2D rendering engine
     * @param color Desired color
     */
    public void drawInfo(Main main, Graphics2D g, Color color){
        g.drawString("Slope: " + slope, 10, 50);
        g.drawString("Y-Intercept: " + y_intercept, 10, 70);
        g.drawString("Ave-Dev: " + ave_dev, 10, 90);
    }



}
