package ru.stqa.pft.sandbox;

import org.junit.Test;
import org.testng.Assert;

public class DistanceTests {
  @Test
  public void distanceNull(){
    Point p1 = new Point(1,3);
    Point p2 = new Point(1,3);
    Assert.assertEquals( p1.distance(p1, p2),0.0);
  }
  @Test
  public void distance10() {
    Point p1 = new Point(2, 2);
    Point p2 = new Point(10, 8);
    Assert.assertEquals(p1.distance(p1, p2), 10.0);
  }
  @Test
  public void distance1() {
    Point p1 = new Point(1, 2);
    Point p2 = new Point(1, 3);
    Assert.assertEquals(p1.distance(p1, p2), 1.0);
  }
}
