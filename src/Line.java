import java.awt.*;
import java.util.ArrayList;
import java.lang.Math;

public class Line {

    double slope;
    double yIntercept;
    double averageDeviation;

    Color color = new Color((int)(Math.random()*255), (int)(Math.random()*255), (int)(Math.random()*255));

    ArrayList<Point> points = new ArrayList<>();
    ArrayList<Point> errorPoints = new ArrayList<>();

    public Line(double slope, double yIntercept){
        this.slope = slope;
        this.yIntercept = yIntercept;
    }

    public Line (Point a, Point b){
        this.slope = (a.y - b.y) / (a.x - b.x);
        this.yIntercept = a.y - (a.x * slope);
    }

    /**
     *
     * @param x Value for coordinate x
     * @return Y value for given x coordinate
     */
    public double getY(double x){
        return slope * x + yIntercept;
    }

    /**
     *
     * @param lb Lower bound for random generator
     * @param ub Upper bound for random generator
     * @param n_points Number of points to generate within given bounds
     */
    public void calculateRandomPoints(int lb, int ub, int n_points){
        clearPoints();
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
                errorPoints.add(new Point(point.x, (int)(point.y * (1 + (Math.random() * error * 2 - error) / 100))));
            }
            else {
                errorPoints.add(new Point(point.x, point.y + (int)(Math.random()*(2 * error) - error)));
            }
        }
    }

    /**
     *
     * @param errorPoints ArrayList of error points with (+/-) values to be compared to
     */
    public void calculateAverageDeviation(ArrayList<Point> errorPoints){
        for (Point errorPoint : errorPoints) {
            averageDeviation += Math.abs((errorPoint.y - getY(errorPoint.x)));
        }
        averageDeviation /= errorPoints.size();
    }

    /**
     * This method determines the optimal slope of a line by comparing it to lines with slightly adjusted slopes
     * (+/- slope increment) and selecting the slope that results in a lower AAD. If neither decision results in a lower
     * AAD, the method terminates.
     * @param errorPoints ArrayList of error points with (+/-) values to be compared to
     * @param slopeIncrement (+/-) value to adjust the slope
     */
    public void adjustSlope(ArrayList<Point> errorPoints, double slopeIncrement){
        double rightAAD = 0;
        double leftAAD = 0;

        while (true){

            // Calculate the line's current AAD
            calculateAverageDeviation(errorPoints);

            // Calculate the line's AAD with adjusted slopes (+/- slope increments)
            for (Point errorPoint : errorPoints) {
                rightAAD += Math.abs(errorPoint.y - ((slope + slopeIncrement) * errorPoint.x + yIntercept));
                leftAAD += Math.abs(errorPoint.y - ((slope - slopeIncrement) * errorPoint.x + yIntercept));

            }
            rightAAD /= errorPoints.size();
            leftAAD /= errorPoints.size();

//            System.out.println("--- LEFT AAD:" + leftAAD);
//            System.out.println("--- RIGHT AAD:" + rightAAD);
//            System.out.println("---- CURRENT:" + averageDeviation);

            // Adjust the line's slope *if* it results in a better AAD; otherwise, break.
            if (averageDeviation < leftAAD && averageDeviation < rightAAD){
                // If (+) slopeIncrement and (-) slopeIncrement both result in higher AAD, break from the loop.
                break;
            } else if (leftAAD < rightAAD){
                // If (-) slopeIncrement results in a lower AAD (better fit), decrease the slope and repeat the program.
                slope -= slopeIncrement;
                calculateRandomPoints(-400, 400, 1000);
                System.out.println("decreased (-0.1): " + slope);
            } else if (rightAAD <= leftAAD){
                // If (+) slopeIncrement results in a lower AAD (better fit), increase the slope and repeat the program.
                slope += slopeIncrement;
                calculateRandomPoints(-400, 400, 1000);
                System.out.println("increased (+0.1): " + slope);
            }
        }
    }

    /**
     * This method determines the optimal y-intercept of a line by comparing it to lines with slightly adjusted
     * y-intercepts (+/- slope increments) and selecting the y-intercept that results in a lower AAD. If neither decision
     * results in aa lower AAD, the method terminates.
     * @param errorPoints ArrayList of error points with (+/-) values to be compared to
     * @param yInterceptIncrement (+/-) value to adjust the y-intercept
     */
    public void adjustYIntercept(ArrayList<Point> errorPoints, double yInterceptIncrement){
        double upAAD = 0;
        double downAAD = 0;

        while (true){

            // Calculate the line's current AAD
            calculateAverageDeviation(errorPoints);

            // Calculate the line's AAD with adjusted slopes (+/- slope increments)
            for (Point errorPoint : errorPoints) {
                upAAD += Math.abs(errorPoint.y - (slope * errorPoint.x + (yIntercept + yInterceptIncrement)));
                downAAD += Math.abs(errorPoint.y - (slope * errorPoint.x + (yIntercept + yInterceptIncrement)));
            }
            upAAD /= errorPoints.size();
            downAAD /= errorPoints.size();
//
//            System.out.println("--- DOWN AAD:" + downAAD);
//            System.out.println("--- UP AAD:" + upAAD);
//            System.out.println("---- CURRENT:" + averageDeviation);

            // Adjust the line's slope *if* it results in a better AAD; otherwise, break.
            if (averageDeviation < downAAD && averageDeviation < upAAD){
                // If (+) slopeIncrement and (-) slopeIncrement both result in higher AAD, break from the loop.
                break;
            } else if (downAAD < upAAD){
                // If (-) slopeIncrement results in a lower AAD (better fit), decrease the slope and repeat the program.
                yIntercept -= yInterceptIncrement;
                calculateRandomPoints(0, 800, 1000);
                System.out.println("decreased (-0.1): " + yIntercept);
            } else if (upAAD <= downAAD){
                // If (+) slopeIncrement results in a lower AAD (better fit), increase the slope and repeat the program.
                yIntercept += yInterceptIncrement;
                calculateRandomPoints(0, 800, 1000);
                System.out.println("increased (+0.1): " + yIntercept);
            }
        }
    }

    /**
     * Clear all points
     */
    public void clearPoints(){
        points = new ArrayList<>();
    }

    /**
     *
     * @param g Java Graphics2D rendering engine
     * @param color Desired color
     */
    public void drawPoints(Main main, Graphics2D g, Color color){
        g.setColor(color);
        for (Point point : points){
            g.fillOval((int)(point.screenX), (main.screenHeight - (int)point.screenY), 4, 4);
        }
    }

    /**
     *
     * @param g Java Graphics2D rendering engine
     * @param color Desired color
     */
    public void drawErrorPoints(Main main, Graphics2D g, Color color){
        g.setColor(color);
        for (Point point : errorPoints){
            g.fillOval((int)point.screenX, main.screenHeight - (int)point.screenY, 4, 4);
        }
    }

    /**
     *
     * @param main Main class for screenWidth and screenHeight variables
     * @param g Java Graphics2D rendering engine
     * @param color Desired color
     */
    public void drawInfo(Main main, Graphics2D g, Color color){
        g.setColor(color);
        g.drawString("Slope: " + slope, 10, 50);
        g.drawString("Y-Intercept: " + yIntercept, 10, 70);
    }


    /**
     *
     * @param time Milliseconds to pause for
     */
    public void pause(int time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
