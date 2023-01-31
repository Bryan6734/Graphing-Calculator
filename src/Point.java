public class Point{

    public double x;
    public double y;

    public double screenX;
    public double screenY;


    public Point(double x, double y){
        this.x = x;
        this.y = y;
    }

    // every tick mark is 50 pixels away but worth it
    // 50 pixels = 1 unit on graph
    public void convertToScreen(int graphWidth, int graphHeight, int graphXInterval, int graphYInterval){
        screenX = (x * graphXInterval) + (double)(graphWidth / 2);
        screenY = (double)(graphHeight / 2) - (y * graphYInterval);
    }

}
