import java.awt.*;
import java.util.ArrayList;
import java.lang.Math;
import org.mariuszgromada.math.mxparser.*;

public class Line {

    Expression expression;
    Color color = new Color((int)(Math.random()*255), (int)(Math.random()*255), (int)(Math.random()*255));
    ArrayList<ArrayList<Point>> pointsLists = new ArrayList<>();

    public Line (Expression expression){
        this.expression = expression;
    }

    public Line (String expression){
        this.expression = new Expression(expression);
    }

    public double getY(double x){

        expression.removeAllArguments();
        Argument arg = new Argument("x", x);
        expression.addArguments(arg);

        if (Double.isNaN(expression.calculate())){
            System.out.println("At x=" + x + " -> y is not a number.");
        }

        return expression.calculate();
    }

    public void computeRandomPoints(int lb, int ub, int n_points){
        ArrayList<Point> points_segment = new ArrayList<>();
        for (int i = 0; i < n_points; i++){
            int x = ((int)(Math.random() * (ub - lb)) + lb);

            points_segment.add(new Point(x, (getY(x) * 1000) / 1000.0));
        }

        pointsLists.add(points_segment);
    }

    public void computePoints(double lb, double ub, double increment){
        ArrayList<Point> points_segment = new ArrayList<>();

        for (double i = lb; i < ub; i += increment){
            points_segment.add(new Point(i, getY(i)));
        }

        pointsLists.add(points_segment);
    }

    public void clearPoints(){
        pointsLists = new ArrayList<>();
    }


}
