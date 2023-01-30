import java.awt.*;
import java.util.ArrayList;

public class Graph {

    public int graphWidth;
    public int graphHeight;

    public Graph(int graphWidth, int graphHeight){

        this.graphWidth = graphWidth;
        this.graphHeight = graphHeight;

    }

    public void drawLine(Graphics2D g2, Line line, Color color){
        g2.setColor(color);
        g2.setStroke(new BasicStroke(2));

        for (int i = 0; i < line.points.size() - 1; i++){
            Point p1 = line.points.get(i);
            Point p2 = line.points.get(i + 1);
            g2.drawLine((int)p1.screenX, (int)p1.screenY, (int)p2.screenX, (int)p2.screenY);
        }
    }

    public void drawAxes(Graphics2D g2, int width, int height, Color color){
        g2.setColor(color);
        g2.drawLine(0, height / 2, width, height / 2);
        g2.drawLine(width / 2, 0, width / 2, height);

        for (int i = 0; i < width; i += 25){
            g2.drawLine(i, height / 2 - 5, i, height / 2 + 5);
        }

        for (int i = 0; i < height; i += 25){
            g2.drawLine(width / 2 - 5, i, width / 2 + 5, i);
        }
    }

    public void drawGrid(Graphics2D g2, int width, int height, Color color){
        g2.setColor(color);
        g2.setStroke(new BasicStroke(1));

        for (int i = 0; i < width; i += 25){
            g2.drawLine(i, 0, i, height);
        }

        for (int i = 0; i < height; i += 25){
            g2.drawLine(0, i, width, i);
        }
    }



}
