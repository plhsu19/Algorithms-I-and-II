/* *****************************************************************************
 *  Name: Pei-Lun Hsu
 *  Date: 23.04.2020
 *  Description: Implement a Point-SET using TreeSet API with brute force method
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class PointSET {

    private SET<Point2D> pointSet;

    // construct an empty set of points
    public PointSET() {
        pointSet = new SET<>();
    }

    // rasie exception if input arg is null
    private void checkArgument(Object arg) {
        if (arg == null)
            throw new IllegalArgumentException("the argument of this mehtod cannot be null!");
    }

    // is the set empty?
    public boolean isEmpty() {
        return pointSet.isEmpty();
    }

    // number of points in the set
    public int size() {
        return pointSet.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        checkArgument(p);
        Point2D point = new Point2D(p.x(), p.y());
        pointSet.add(point);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        checkArgument(p);
        return pointSet.contains(p);
    }

    // draw all points to standard draw
    public void draw() {

        double penRadius = 0.01;
        StdDraw.setPenRadius(penRadius);
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.enableDoubleBuffering();
        for (Point2D p : pointSet) {
            p.draw();
        }
        // StdDraw.show();
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        checkArgument(rect);
        SET<Point2D> insideRectP = new SET<>();

        for (Point2D p : pointSet) {
            if (rect.contains(p)) insideRectP.add(p);
        }
        return insideRectP;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        checkArgument(p);
        double minDistance = Double.POSITIVE_INFINITY;
        Point2D nearestP = null;
        // if the point Set is empty, return null (no nearest point)
        if (pointSet.isEmpty()) return null;

        for (Point2D point : pointSet) {
            double distance = p.distanceSquaredTo(point);
            if (distance < minDistance) {
                minDistance = distance;
                nearestP = point;
            }
        }
        return nearestP;
    }

    // method return the min point in the SET, only for testing
    private Point2D minPoint() {
        return pointSet.min();
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {

        // test constructor()
        PointSET testSet = new PointSET();
        PointSET testSet2 = new PointSET();
        RectHV testRect = new RectHV(0.3, 0.2, 0.43, 0.5);

        // test insert()
        for (int i = 0; i < 100; i++) {
            double x = StdRandom.uniform(0.0, 1.0);
            double y = StdRandom.uniform(0.0, 1.0);
            Point2D p = new Point2D(x, y);
            testSet.insert(p);
        }


        // test size()
        assert (testSet.size() == 100);
        assert (testSet2.size() == 0);
        StdOut.println("size of PointSet: " + testSet.size());
        StdOut.println("size of PointSet2: " + testSet2.size());

        // test isEmpty()
        StdOut.println("Is PointSet empty? :" + testSet.isEmpty());
        StdOut.println("Is PointSet2 empty? :" + testSet2.isEmpty());

        // test inserting null
        try {
            testSet.insert(null);
        }
        catch (IllegalArgumentException e) {
            StdOut.println(e);
        }


        // test contain()
        Point2D pointIn1 = testSet.minPoint();
        // Point2D pointIn2 = testSet.maxPoint();
        StdOut.println("testSet contains point " + pointIn1 + ": " + testSet.contains(pointIn1));
        // Point2D pointOut1 = new Point2D(0.0, 0.0);
        Point2D pointOut2 = new Point2D(0.4, 0.5);
        StdOut.println("testSet contains point " + pointOut2 + ": " + testSet.contains(pointOut2));


        // test draw()
        // StdDraw.setXscale(0.0, 20.0);
        // StdDraw.setYscale(0.0, 20.0);
        testSet.draw();
        StdDraw.setPenRadius(0.005);
        StdDraw.setPenColor(StdDraw.PRINCETON_ORANGE);
        testRect.draw();
        StdDraw.show();

        // test nearest()
        Point2D pointTest = new Point2D(0.2, 0.9);
        Point2D nearestPoint = testSet.nearest(pointTest);
        StdDraw.setPenColor(StdDraw.BOOK_RED);
        StdDraw.setPenRadius(0.03);
        pointTest.draw();
        StdDraw.setPenColor(StdDraw.MAGENTA);
        nearestPoint.draw();
        StdDraw.show();
        StdOut.println(
                "The nearest point in set to " + pointTest + " is: " + nearestPoint);

        // test range()
        Iterable<Point2D> rangeSet = testSet.range(testRect);
        StdOut.println("points in rectangle " + testRect + ": ");
        for (Point2D p : rangeSet) {
            StdOut.println(p);
        }
    }
}



























