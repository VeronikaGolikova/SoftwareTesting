package sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;

public class CalculateDistanceCheck {

    @Test
    public void testDistanceCheckWithPositiveValue() {
        Point p1 = new Point(3, 4);
        Point p2 = new Point(3, 6);
        Assert.assertEquals(p1.countDistance(p2), 2.0);
    }

    @Test
    public void testDistanceCheckWithNegativeValue() {
        Point p1 = new Point(-4, 4);
        Point p2 = new Point(3, 6);
        Assert.assertEquals(p1.countDistance(p2), 7.280109889280518);
    }

    @Test
    public void testDistanceCheckWithZeroValue() {
        Point p1 = new Point(0, 0);
        Point p2 = new Point(3, 6);
        Assert.assertEquals(p1.countDistance(p2), 6.708203932499369);
    }

    @Test
    public void testDistanceCheckWithSimillarPoint() {
        Point p1 = new Point(2, 2);
        Point p2 = new Point(2, 2);
        Assert.assertEquals(p1.countDistance(p2), 0.0);
    }
}
