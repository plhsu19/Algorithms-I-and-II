/* *****************************************************************************
 *  Name: Pei-Lun Hsu
 *  Date: 18022020
 *  Description: assignemt sorting for Cousera, Algorithms Part I
 *               build a Point data type implements Comparable<> interface,
 *               and with class implememnts Comparator<> interface
 **************************************************************************** */

/******************************************************************************
 *  Compilation:  javac Point.java
 *  Execution:    java Point
 *  Dependencies: none
 *
 *  An immutable data type for points in the plane.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

public class Point implements Comparable<Point> {

    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point

    /**
     * Initializes a new point.
     *
     * @param x the <em>x</em>-coordinate of the point
     * @param y the <em>y</em>-coordinate of the point
     */
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    /**
     * Draws this point to standard draw.
     */
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment between this point and the specified point to standard draw.
     *
     * @param that the other point
     */
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * Returns the slope between this point and the specified point. Formally, if the two points are
     * (x0, y0) and (x1, y1), then the slope is (y1 - y0) / (x1 - x0). For completeness, the slope
     * is defined to be +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertical; and Double.NEGATIVE_INFINITY if
     * (x0, y0) and (x1, y1) are equal.
     *
     * @param that the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point that) {
        /* YOUR CODE HERE */
        double slope;
        if (this.x == that.x && this.y == that.y)
            return Double.NEGATIVE_INFINITY; // degenerate line segment
        else if (this.x == that.x) return Double.POSITIVE_INFINITY; // Vertical line
        else if (this.y == that.y) return 0.0; // Horizontal line
        else {
            slope = (that.y - this.y) / ((double) (that.x - this.x));
        }
        return slope;
    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate. Formally, the invoking
     * point (x0, y0) is less than the argument point (x1, y1) if and only if either y0 < y1 or if
     * y0 = y1 and x0 < x1.
     *
     * @param that the other point
     * @return the value <tt>0</tt> if this point is equal to the argument point (x0 = x1 and y0 =
     * y1); a negative integer if this point is less than the argument point; and a positive integer
     * if this point is greater than the argument point
     */
    public int compareTo(Point that) {
        /* YOUR CODE HERE */
        if (this.y < that.y) return -1;
        else if (this.y > that.y) return +1;
        else {
            if (this.x < that.x) return -1;
            else if (this.x > that.x) return +1;
            else if (this.x == that.x) return 0;
        }
        return 0;
    }

    /**
     * Compares two points by the slope they make with this point. The slope is defined as in the
     * slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     */
    public Comparator<Point> slopeOrder() {
        /* YOUR CODE HERE */
        return new PointOrder();
    }

    // private class
    private class PointOrder implements Comparator<Point> {
        public int compare(Point p1, Point p2) {
            double s1 = slopeTo(p1);
            double s2 = slopeTo(p2);
            return Double.compare(s1, s2);
        }
    }


    /**
     * Returns a string representation of this point. This method is provide for debugging; your
     * program should not rely on the format of the string representation.
     *
     * @return a string representation of this point
     */
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }


    // print the result of compareTo() and comparator.compare()
    // private static method
    private static void printResult(int compare) {
        if (compare < 0) StdOut.println("p1 < p2");
        else if (compare > 0) StdOut.println("p1 > p2");
        else StdOut.println("p1 equals p2");
    }

    /**
     * Unit tests the Point data type.
     */
    public static void main(String[] args) {
        /* YOUR CODE HERE */
        int x0 = StdIn.readInt();
        int y0 = StdIn.readInt();
        int x1 = StdIn.readInt();
        int y1 = StdIn.readInt();
        int x2 = StdIn.readInt();
        int y2 = StdIn.readInt();

        Point p0 = new Point(x0, y0);
        Point p1 = new Point(x1, y1);
        Point p2 = new Point(x2, y2);

        StdOut.println("p0: " + p0.toString());
        StdOut.println("p1: " + p1.toString());
        StdOut.println("p2: " + p2.toString());

        // test compareTo()
        int resultN = p1.compareTo(p2);
        printResult(resultN);
        StdOut.println();

        // test slopeTo
        double slope = p1.slopeTo(p2);
        double slope01 = p0.slopeTo(p1);
        double slope02 = p0.slopeTo(p2);
        StdOut.println("slope from p1 to p2: " + slope);
        StdOut.println();

        // test comparator
        Comparator<Point> p0Comparator = p0.slopeOrder();
        int resultC = p0Comparator.compare(p1, p2);
        StdOut.println("slope from p0 to p1: " + slope01);
        StdOut.println("slope from p0 to p2: " + slope02);
        StdOut.println("compare slope of p1 and p2: ");
        printResult(resultC);

    }
}
