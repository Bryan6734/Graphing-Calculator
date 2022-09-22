public class Point{

    public double x;
    public double y;

    public double screenX;
    public double screenY;


    public Point(double x, double y){
        this.x = x;
        this.y = y;

        convertPoint();
    }

    public void printInfo(){
        System.out.println("Point: " + this.x + " " + this.y);
    }

    public void convertPoint() {
//        screenX = (int) (x + (700 / 2));
//        screenY = (int) (x + (700 / 2));

        screenX = x + (int)(700 / 2);
        screenY = y + (int)(700 / 2);

    }
}
