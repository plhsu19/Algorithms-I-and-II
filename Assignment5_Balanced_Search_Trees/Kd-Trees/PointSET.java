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

public class PointSET {

    private SET<Point2D> pointSet;

    // construct an empty set of points
    public PointSET() {
        pointSet = new SET<>();
    }

    // rasie exception function
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
        pointSet.add(p);

    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        checkArgument(p);
        return pointSet.contains(p);
    }

    // draw all points to standard draw
    public void draw() {

        StdDraw.enableDoubleBuffering();
        for (Point2D p : pointSet) {
            StdDraw.point(p.x(), p.y());
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
        if (pointSet.isEmpty()) return null;

        for (Point2D point : pointSet) {
            double distance = p.distanceTo(point);
            if (distance < minDistance) {
                minDistance = distance;
                nearestP = point;
            }
        }
        return nearestP;
    }

    private Point2D minPoint() {
        return pointSet.min();
    }

    private Point2D maxPoint() {
        return pointSet.max();
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {

        // test constructor()
        PointSET testSet = new PointSET();
        PointSET testSet2 = new PointSET();

        // test insert()
        for (int i = 1; i < 21; i++) {
            for (int j = 1; j < 21; j++) {
                // double y = StdRandom.uniform(0.0, 20.0);
                Point2D p = new Point2D((double) i, (double) j);
                testSet.insert(p);
            }
        }


        // test size()
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
        Point2D pointOut2 = new Point2D(10.5, 10.5);
        StdOut.println("testSet contains point " + pointOut2 + ": " + testSet.contains(pointOut2));


        // test drawing
        RectHV testRect = new RectHV(5.3, 2.2, 6.3, 10.5);
        StdDraw.setXscale(0.0, 20.0);
        StdDraw.setYscale(0.0, 20.0);
        StdDraw.setPenRadius(0.005);
        StdDraw.setPenColor(StdDraw.BLUE);
        testSet.draw();
        StdDraw.setPenRadius(0.005);
        StdDraw.setPenColor(StdDraw.PRINCETON_ORANGE);
        testRect.draw();
        StdDraw.show();

        // test nearest()
        Point2D pointTest = new Point2D(14.2, 7.9);
        StdOut.println(
                "The nearest point in set to " + pointTest + " is: " + testSet.nearest(pointTest));

        // test range()
        Iterable<Point2D> rangeSet = testSet.range(testRect);
        StdOut.println("points in rectangle " + testRect + ": ");
        for (Point2D p : rangeSet) {
            StdOut.println(p);
        }
    }
}



























