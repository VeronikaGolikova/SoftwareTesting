public class MyFirstProgram {

    public static void main(String[] args) {
        System.out.println("Hello world!");

        Point p1 = new Point(2.0, 5.0);
        Point p2 = new Point(6.0, 10.0);

        Point.countDistance(p1, p2);
    }
}
