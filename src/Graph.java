import java.awt.*;
import java.util.ArrayList;


public class Graph {

    public int graphWidth;
    public int graphHeight;

    public int graphYScale = 1;
    public int graphYPixelsPerUnit = 40;

    public int graphXScale = 1;
    public int graphXPixelsPerUnit = 40;

    public ArrayList<Line> mountedLines = new ArrayList<>();
    public ArrayList<Point> mountedPoints = new ArrayList<>();

    public Graph(int graphWidth, int graphHeight){
        this.graphWidth = graphWidth;
        this.graphHeight = graphHeight;
    }

    public void addLine(Line line){
        System.out.println("Adding line");
        mountedLines.add(line);
    }

    public void addPoint(Point point){
        System.out.println("Adding point");
        mountedPoints.add(point);
    }

    public void addPoints(ArrayList<Point> new_points){
        mountedPoints.addAll(new_points);
    }

    public void clearLines(){
        mountedLines.clear();
    }

    public void clearPoints(){ mountedPoints.clear(); }

    public void drawPoints(Graphics2D g2, Color color){
        g2.setColor(color);

        for (Point point : mountedPoints){
            point.convertToScreen(graphWidth, graphHeight, graphXPixelsPerUnit, graphYPixelsPerUnit);
            int diameter = 6;
            g2.fillOval((int)point.screenX - (diameter / 2), (int)point.screenY - (diameter / 2), diameter, diameter);
        }
    }

    public void drawLines(Graphics2D g2){
        for (Line line : mountedLines){
            drawLine(g2, line);
        }
    }

    public void drawLine(Graphics2D g2, Line line){
        g2.setColor(line.color);
        g2.setStroke(new BasicStroke(4));

        for (ArrayList<Point> pointsSegment : line.pointsLists){

            for (int i = 0; i < pointsSegment.size() - 1; i++){
                Point a = pointsSegment.get(i);
                Point b = pointsSegment.get(i + 1);
                a.convertToScreen(graphWidth, graphHeight, graphXPixelsPerUnit, graphYPixelsPerUnit);
                b.convertToScreen(graphWidth, graphHeight, graphXPixelsPerUnit, graphYPixelsPerUnit);

                g2.drawLine((int)a.screenX, (int)a.screenY, (int)b.screenX, (int)b.screenY);
            }

        }
    }

    public void drawAxes(Graphics2D g2, int width, int height, Color color){
        g2.setColor(color);
        g2.drawLine(0, height / 2, width, height / 2);
        g2.drawLine(width / 2, 0, width / 2, height);



        for (int i = width / 2; i < width; i += graphXPixelsPerUnit * graphXScale){
            g2.drawLine(i, height / 2 - 5, i, height / 2 + 5);
            g2.drawString(Integer.toString((i - width / 2) / graphXPixelsPerUnit), i, height / 2 + 15);
        }

        for (int i = width / 2; i > 0; i -= graphXPixelsPerUnit * graphXScale){
            g2.drawLine(i, height / 2 - 5, i, height / 2 + 5);
            g2.drawString(Integer.toString((i - width / 2) / graphXPixelsPerUnit), i, height / 2 + 15);
        }

        for (int i = height / 2; i < height; i += graphYPixelsPerUnit * graphYScale){
            g2.drawLine(width / 2 - 5, i, width / 2 + 5, i);
            g2.drawString(Integer.toString((height / 2 - i) / graphYPixelsPerUnit), width / 2 + 15, i);
        }

        for (int i = height / 2; i > 0; i -= graphYPixelsPerUnit * graphYScale){
            g2.drawLine(width / 2 - 5, i, width / 2 + 5, i);
            g2.drawString(Integer.toString((height / 2 - i) / graphYPixelsPerUnit), width / 2 + 15, i);
        }

    }

    public void drawGrid(Graphics2D g2, int width, int height, Color color){
        g2.setColor(color);

        for (int i = width / 2; i < width; i += graphXPixelsPerUnit * graphXScale){
            g2.drawLine(i, 0, i, height);
        }

        for (int i = width / 2; i > 0; i -= graphXPixelsPerUnit * graphXScale){
            g2.drawLine(i, 0, i, height);
        }

        for (int i = height / 2; i < height; i += graphYPixelsPerUnit * graphYScale){
            g2.drawLine(0, i, width, i);
        }

        for (int i = height / 2; i > 0; i -= graphYPixelsPerUnit * graphYScale){
            g2.drawLine(0, i, width, i);
        }

    }

    public double convertYCoordinateToValue(int mouseY) {
        return (double)(graphHeight / 2 - mouseY) / (graphYPixelsPerUnit);
    }

    public double convertXCoordinateToValue(int mouseX) {
        return (double)(mouseX - graphWidth / 2) / (graphXPixelsPerUnit);
    }
}
