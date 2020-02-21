/* *****************************************************************************
 *  Name: Pei-Lun Hsu
 *  Date: 21022020
 *  Description: assignment for Coursera, Algorithm I, Sorting
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class FastCollinearPoints {


    private Point[] pArray;
    private LineSegment[] lsArray;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        // raise error if the argument is null
        if (points == null)
            throw new IllegalArgumentException("arg of FastCollinearPoints cannot be null");
        // raise error if there is null in argument array
        for (Point p : points) {
            if (p == null) throw new IllegalArgumentException(
                    "arg array of FastCollinearPoints cannot contain null point");
        }

        pArray = points;  // sort points according to their natural comparison(y then x-coordinate)
        Arrays.sort(pArray, 0, pArray.length);
        // ArrayList to temperarly store the line segments1
        List<LineSegment> tempList = new ArrayList<LineSegment>();

        // raise error if there is duplicated points in argument array
        for (int i = 0; i < (pArray.length - 1); i++) {
            if (pArray[i].compareTo(pArray[i + 1]) == 0)
                throw new IllegalArgumentException("array cannot contain repeated points");
        }

        // for (int r = 0; r < pArray.length; r++) StdOut.println(pArray[r]);

        Comparator<Point> ctest = pArray[0].slopeOrder();
        double s1 = pArray[0].slopeTo(pArray[1]);
        double s3 = pArray[0].slopeTo(pArray[3]);
        int testCompare = ctest.compare(pArray[1], pArray[3]);

        /* StdOut.println("slope of p1:" + s1);
        StdOut.println("slope of p3:" + s3);
        StdOut.println("s1 compare with s3: " + testCompare);
        StdOut.println("------------------------------------------------"); */

        for (int i = 0; i < pArray.length; i++) {

            // sort the points based on slope to point[i] ~nlogn
            // pick the adjacent points with same slope to point[i] ~n
            // sort the points back to natural order ~nlogn
            Comparator<Point> c = pArray[i].slopeOrder();
            Arrays.sort(pArray, i, pArray.length, c);
            /* StdOut.println(i);
            for (int r = 0; r < pArray.length; r++) StdOut.println(pArray[r]);
            StdOut.println("------------------------------------------------"); */
            // count the number of adjacent points with same slope to point[i]
            int count = 1;

            for (int j = i + 1; j < (pArray.length - 1); j++) {
                Point current = pArray[j];
                Point next = pArray[j + 1];
                /* StdOut.println("j point: " + current);
                StdOut.println("j + 1 point: " + next);
                StdOut.println("slope compare: " + c.compare(current, next));*/

                // if adjacent points have same slope to point i, on the same line
                if (c.compare(current, next) == 0) {
                    count += 1;
                    // StdOut.println(count);
                }
                // if adjacent points' slopes are different, not on the same line
                else if (c.compare(current, next) != 0) {
                    if (count >= 3) {
                        tempList.add(new LineSegment(pArray[i], pArray[j]));
                    }
                    count = 1;
                }
                if (j == (pArray.length - 2) && count >= 3)
                    tempList.add(new LineSegment(pArray[i], pArray[j + 1]));
                // StdOut.println(" - - - - - - - - - - - - - - - - - - - - - - - - - - - - - ");
            }
            Arrays.sort(pArray, i, pArray.length);
        }

        // StdOut.println("tempList legth = " + tempList.size());
        lsArray = new LineSegment[tempList.size()];
        for (int m = 0; m < lsArray.length; m++) {
            lsArray[m] = tempList.get(m);
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return lsArray.length;
    }

    public LineSegment[] segments() {
        return lsArray;
    }

    // test client
    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
