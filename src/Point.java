public class Point{

    public double x;
    public double y;

    public double screenX;
    public double screenY;


    public Point(double x, double y){
        this.x = x;
        this.y = y;

        adjustScreenPosition(700);
    }

    public void printInfo(){
        System.out.println("Point: " + this.x + " " + this.y);
    }

    public void adjustScreenPosition(int screenHeight) {
        screenX = x + (int)(screenHeight / 2);
        screenY = y + (int)(screenHeight / 2);
    }
}
