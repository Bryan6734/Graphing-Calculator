import java.awt.*;
import java.util.ArrayList;

// where to put my folder

public class Graph {

    public int graphWidth;
    public int graphHeight;

    public int graphXInterval = 50;
    public int graphYInterval = 25;

    public ArrayList<Line> lines = new ArrayList<>();
    public ArrayList<Point> points = new ArrayList<>();



    public Graph(int graphWidth, int graphHeight){
        this.graphWidth = graphWidth;
        this.graphHeight = graphHeight;
        generateSinusoidalPoints();
    }

    public void addLine(Line line){
        System.out.println("Adding line");
        lines.add(line);
    }

    public void generateSinusoidalPoints(){
        for (int i = -400; i < 400; i++){
            points.add(new Point(i, Math.sin(i)));
        }
    }

    public void addPoint(Point point){
        System.out.println("Adding point");
        points.add(point);
    }

    public Line gradientDescent(double learningRate, int iterations){
        Line line = new Line(0, 0);
        line.calculateRandomPoints(-400, 400, 10);
        for (int i = 0; i < iterations; i++){
            double slope = line.slope;
            double yIntercept = line.yIntercept;
            double error = calculateError(slope, yIntercept);
            double slopeChange = calculateSlopeChange(slope, yIntercept, learningRate);
            double yInterceptChange = calculateYInterceptChange(slope, yIntercept, learningRate);
            line.slope = slope - slopeChange;
            line.yIntercept = yIntercept - yInterceptChange;
            System.out.println("Iteration: " + i + " Error: " + error + " Slope: " + line.slope + " Y-Intercept: " + line.yIntercept);
        }
        return line;
    }

    public double calculateError(double slope, double yIntercept){
        double error = 0;
        for (Point point : points){
            error += Math.pow(point.y - (slope * point.x + yIntercept), 2);
        }
        return error;
    }

    public double calculateSlopeChange(double slope, double yIntercept, double learningRate){
        double slopeChange = 0;
        for (Point point : points){
            slopeChange += -2 * point.x * (point.y - (slope * point.x + yIntercept));
        }
        return slopeChange * learningRate;
    }

    public double calculateYInterceptChange(double slope, double yIntercept, double learningRate){
        double yInterceptChange = 0;
        for (Point point : points){
            yInterceptChange += -2 * (point.y - (slope * point.x + yIntercept));
        }
        return yInterceptChange * learningRate;
    }


    public void clearLines(){
        lines.clear();
    }

    public void clearPoints(){ points.clear(); }

    public void drawPoints(Graphics2D g2, Color color){
        g2.setColor(color);

        for (Point point : points){
            point.convertToScreen(graphWidth, graphHeight, graphXInterval, graphYInterval);
            int diameter = 6;
            g2.fillOval((int)point.screenX - (diameter / 2), (int)point.screenY - (diameter / 2), diameter, diameter);
        }
    }

    public void drawLines(Graphics2D g2){
        for (Line line : lines){
            drawLine(g2, line);
        }
    }

    public void drawLine(Graphics2D g2, Line line){
        g2.setColor(line.color);
        g2.setStroke(new BasicStroke(2));

        for (int i = 0; i < line.points.size() - 1; i++){

            line.points.get(i).convertToScreen(graphWidth, graphHeight, graphXInterval, graphYInterval);
            line.points.get(i + 1).convertToScreen(graphWidth, graphHeight, graphXInterval, graphYInterval);

            g2.drawLine((int)line.points.get(i).screenX, (int)line.points.get(i).screenY, (int)line.points.get(i + 1).screenX, (int)line.points.get(i + 1).screenY);

        }
    }

    public void drawAxes(Graphics2D g2, int width, int height, Color color){
        g2.setColor(color);
        g2.drawLine(0, height / 2, width, height / 2);
        g2.drawLine(width / 2, 0, width / 2, height);



        for (int i = width / 2; i < width; i += graphXInterval){
            g2.drawLine(i, height / 2 - 5, i, height / 2 + 5);
            g2.drawString(Integer.toString((i - width / 2) / graphXInterval), i, height / 2 + 15);

        }

        for (int i = width / 2; i > 0; i -= graphXInterval){
            g2.drawLine(i, height / 2 - 5, i, height / 2 + 5);
            g2.drawString(Integer.toString((i - width / 2) / graphXInterval), i, height / 2 + 15);
        }

        for (int i = height / 2; i < height; i += graphYInterval){
            g2.drawLine(width / 2 - 5, i, width / 2 + 5, i);
            g2.drawString(Integer.toString((height / 2 - i) / graphYInterval), width / 2 + 10, i);
        }

        for (int i = height / 2; i > 0; i -= graphYInterval){
            g2.drawLine(width / 2 - 5, i, width / 2 + 5, i);
            g2.drawString(Integer.toString((height / 2 - i) / graphYInterval), width / 2 + 10, i);
        }


    }

    public void drawGrid(Graphics2D g2, int width, int height, Color color){
        g2.setColor(color);
        g2.setStroke(new BasicStroke(1));


        for (int i = (width / 2); i < width; i += graphXInterval){
            g2.drawLine(i, 0, i, height);
        }

        for (int i = (width / 2); i > 0; i -= graphXInterval){
            g2.drawLine(i, 0, i, height);
        }

        for (int i = (height / 2); i < height; i += graphYInterval){
            g2.drawLine(0, i, width, i);
        }

        for (int i = (height / 2); i > 0; i -= graphYInterval){
            g2.drawLine(0, i, width, i);
        }
    }


    public double convertYCoordinateToValue(int mouseY) {
        return (double)(graphHeight / 2 - mouseY) / graphYInterval;
    }

    public double convertXCoordinateToValue(int mouseX) {
        return (double)(mouseX - graphWidth / 2) / graphXInterval;
    }
}
