public class Point {
    public double x;
    public double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public static double countDistance(Point p1, Point p2) {

        return Math.sqrt((p2.x - p1.x) * (p2.x - p1.x) + (p2.y - p1.y) * (p2.y - p1.y));

    }

    public void countDistance(Point p2) {

        System.out.println("Вычисление при помощи метода: \n" + Math.sqrt((p2.x - x) * (p2.x - x) + (p2.y - y) * (p2.y - y)));
    }

}
