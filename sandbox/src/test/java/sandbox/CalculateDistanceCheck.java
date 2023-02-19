package sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;

public class CalculateDistanceCheck {

    @Test
    public void testDistanceCheck() {
        Point p1 = new Point(3, 4);
        Point p2 = new Point(3, 6);
        Assert.assertEquals(p1.countDistance(p2), 2.0);

    }
}
