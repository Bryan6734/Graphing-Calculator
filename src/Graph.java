import java.awt.*;
import java.util.ArrayList;

public class Graph {

    public int graphWidth;
    public int graphHeight;

    public int graphXInterval = 50;
    public int graphYInterval = 25;



    public ArrayList<Line> lines = new ArrayList<>();

    public Graph(int graphWidth, int graphHeight){

        this.graphWidth = graphWidth;
        this.graphHeight = graphHeight;

    }

    public void addLine(Line line){
        System.out.println("Adding line");
        lines.add(line);
    }

    public void clearLines(){
        lines.clear();
    }

    public void drawLines(Graphics2D g2){
        for (Line line : lines){
//            Color color = new Color((int)(Math.random()*255), (int)(Math.random()*255), (int)(Math.random()*255));
            Color color = new Color(105, 229, 232);
            drawLine(g2, line, color);
        }
    }

    public void drawLine(Graphics2D g2, Line line, Color color){
        g2.setColor(color);
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

        for (int i = 0; i < width; i += 25){
            g2.drawLine(i, 0, i, height);
        }

        for (int i = 0; i < height; i += 25){
            g2.drawLine(0, i, width, i);
        }
    }



}
