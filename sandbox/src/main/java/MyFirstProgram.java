public class MyFirstProgram {

    public static void main(String[] args) {
        System.out.println("Hello world!");

        Point p1 = new Point(2.0, 5.0);
        Point p2 = new Point(6.0, 10.0);

        System.out.println(Math.sqrt((p2.x - p1.x) * (p2.x - p1.x) + (p2.y - p1.y) * (p2.y - p1.y)));

        Point.countDistance(p1, p2);
    }
}
