/* *****************************************************************************
 *  Name: Pei-Lun Hsu
 *  Date: 20022020
 *  Description: assignment for Coursera, Algorithm I, Sorting
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteCollinearPoints {

    private final LineSegment[] lsArray;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        // raise error if the argument is null
        if (points == null)
            throw new IllegalArgumentException("arg of BruteCollinearPoints cannot be null");
        // raise error if there is null in argument array
        for (Point p : points) {
            if (p == null) throw new IllegalArgumentException(
                    "arg array of BruteCollinearPoints cannot contain null point");
        }


        // copy objects of points to a defensive array pArray
        // sorted array of points
        Point[] pArray = new Point[points.length];
        for (int i = 0; i < points.length; i++) {
            pArray[i] = points[i];
        }
        Arrays.sort(pArray, 0, pArray.length);

        // raise error if there is duplicated points in argument array
        for (int i = 0; i < (pArray.length - 1); i++) {
            if (pArray[i].compareTo(pArray[i + 1]) == 0)
                throw new IllegalArgumentException("array cannot contain repeated points");
        }

        List<LineSegment> tempList = new ArrayList<LineSegment>();
        // brutely find all line segments containing 4 points, and add these segmemts into LineSegment array
        for (int i = 0; i < pArray.length; i++) {
            for (int j = i + 1; j < pArray.length; j++) {
                for (int k = j + 1; k < pArray.length; k++) {
                    for (int l = k + 1; l < pArray.length; l++) {
                        double s1 = pArray[i].slopeTo(pArray[j]);
                        double s2 = pArray[j].slopeTo(pArray[k]);
                        if (Double.compare(s1, s2) == 0) {
                            double s3 = pArray[k].slopeTo(pArray[l]);
                            if (Double.compare(s2, s3) == 0) {
                                tempList.add(new LineSegment(pArray[i], pArray[l]));
                            }
                        }
                    }
                }
            }
        }
        lsArray = new LineSegment[tempList.size()];
        for (int m = 0; m < tempList.size(); m++) {
            lsArray[m] = tempList.get(m);
        }

    }

    // the number of line segments
    public int numberOfSegments() {
        return lsArray.length;
    }

    // the line segments
    public LineSegment[] segments() {
        // make an defensive copy of lsArray
        LineSegment[] defLsArray = Arrays.copyOf(lsArray, lsArray.length);
        return defLsArray;
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
