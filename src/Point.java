public class Point{

    public double x;
    public double y;

    public double screenX;
    public double screenY;


    public Point(double x, double y){
        this.x = x;
        this.y = y;
    }


    public void convertToScreen(int graphWidth, int graphHeight, int graphXInterval, int graphYInterval){
        screenX = (x * graphXInterval) + (double)(graphWidth / 2);
        screenY = (double)(graphHeight / 2) - (y * graphYInterval);
    }

//    public void convertToScreen(int graphWidth, int graphHeight, double graphXScale, double graphYScale, int graphXPixelsPerUnit, int graphYPixelsPerUnit){
//        screenX = (double)(graphWidth / 2) + (x * graphXScale);
//        screenY = (double)(graphHeight / 2) - (y * graphYScale);
//
//        System.out.println(screenX);
//        System.out.println(screenY);
//    }

}
