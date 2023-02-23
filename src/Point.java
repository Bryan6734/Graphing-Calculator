public class Point{

    public double x;
    public double y;

    public double screenX;
    public double screenY;


    public Point(double x, double y){
        this.x = x;
        this.y = y;
    }

    public void convertToScreen(int graphWidth, int graphHeight, int graphXPixelsPerUnit, int graphYPixelsPerUnit){
        screenX = (x * graphXPixelsPerUnit) + (double)(graphWidth / 2);
        screenY = (double)(graphHeight / 2) - (y * graphYPixelsPerUnit);
    }

}
