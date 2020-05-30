/* *****************************************************************************
 *  Name: Pei-Lun Hsu
 *  Date: 25.04.2020
 *  Description: Implementation of Kd-Tree (BST) for range search and nearest-neighbor search
 *  Notice: This is the version for runtime testing
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class KdTree {

    // constant corresponding to vertical split of search level in the tree
    private static final int VERTICAL = 0;
    // root of the tree, internal private Node type
    private Node root;
    // number of items have been inserted
    private int size;


    // construct an empty set of points
    public KdTree() {
        root = null;
        size = 0;
    }

    // rasie exception if input arg is null
    private void checkArgument(Object arg) {
        if (arg == null)
            throw new IllegalArgumentException("the argument of this mehtod cannot be null!");
    }

    // define node class in the kd-tree
    // use static inner class to save 8-byte overhead
    private static class Node {
        private Point2D point;
        private Node lb, rt;
        private RectHV rect;

        public Node(Point2D p, RectHV r) {
            point = new Point2D(p.x(), p.y());
            lb = null;
            rt = null;
            rect = r;
        }
    }

    // is the set empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // number of points in the set
    public int size() {
        return size;
    }

    // helper method (recursive) to insert a node
    // orientation: searchLevel % 2 == VERITCAL -> vertical split, searchLevel % 2 == HORIZONTAL -> horizonal split
    private Node put(Point2D p, Node n, int level, double xmin, double ymin, double xmax,
                     double ymax) {

        // base condition for the recursive:
        if (n == null) {
            RectHV rect = new RectHV(xmin, ymin, xmax, ymax);
            Node newNode = new Node(p, rect);
            size += 1;
            return newNode;
        }

        // recursively put the point into corresponding sub-tree or node according to
        // the result of comparison of point to the node's point
        // even level: comparison of the x coordinate, vertical split.
        if (level % 2 == VERTICAL) {
            if (p.x() < n.point.x()) // p.x < node.x -> left child
                n.lb = put(p, n.lb, level + 1, n.rect.xmin(), n.rect.ymin(), n.point.x(),
                           n.rect.ymax());
            else if (Double.compare(p.x(), n.point.x()) == 0
                    && Double.compare(p.y(), n.point.y()) == 0)
                return n; // point == node.point -> point already exists in the tree
            else  // p.x > node.x -> rigt child || p.x == n.x, p.y != n.y -> right child
                n.rt = put(p, n.rt, level + 1, n.point.x(), n.rect.ymin(), n.rect.xmax(),
                           n.rect.ymax());
        }
        // odd level: comparison of y coordiante, horizontal split.
        else {
            if (p.y() < n.point.y()) // p.y < node.y -> bottom child
                n.lb = put(p, n.lb, level + 1, n.rect.xmin(), n.rect.ymin(), n.rect.xmax(),
                           n.point.y());
            else if (Double.compare(p.x(), n.point.x()) == 0
                    && Double.compare(p.y(), n.point.y()) == 0)
                return n; // point == n.point -> point already existed
            else // p.y > node.y || p.y == node.y, p.x != node.x  -> top child
                n.rt = put(p, n.rt, level + 1, n.rect.xmin(), n.point.y(), n.rect.xmax(),
                           n.rect.ymax());
        }
        return n;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        checkArgument(p);
        root = put(p, root, 0, 0.0, 0.0, 1.0, 1.0);
    }

    // private compare method that determined whether to compare points by
    // x or y coordinate
    private int comparePoints(Point2D a, Point2D b, int level) {
        if (level % 2 == VERTICAL) return Double.compare(a.x(), b.x());
        else return Double.compare(a.y(), b.y());
    }

    // helper method(recursive) to check whether the Kd-tree contains point p
    private boolean get(Point2D p, Node n, int level) {
        // base condition
        if (n == null) {
            return false;
        }
        // compare the target point with the node point, recursively search
        // the child tree or return the point if matched
        int comparison = comparePoints(p, n.point, level);

        if (comparison < 0)
            return get(p, n.lb, level + 1); // p.x < node.point.x -> check left child
        else if (comparison == 0 && comparePoints(p, n.point, level + 1) == 0)
            return true; // p == node.point -> point is found
        else return get(p, n.rt, level + 1);  // (p.x > node.point.x) or
        // (p.x == node.point.x, but p.y != node.point.y) -> check right child
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        checkArgument(p);
        return get(p, root, 0);
    }

    // helper method for draw() by using depth-first search traversal
    private void drawDFS(Node n, int h) {

        // based condition
        if (n != null) {
            double lineRadius = 0.005;
            double pointRadius = 0.01;
            // draw current node's split
            // even level: vertical line
            if (h % 2 == 0) {
                StdDraw.setPenRadius(lineRadius);
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.line(n.point.x(), n.rect.ymin(), n.point.x(), n.rect.ymax());
            }
            // odd level: horizontal line
            else {
                StdDraw.setPenRadius(lineRadius);
                StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.line(n.rect.xmin(), n.point.y(), n.rect.xmax(), n.point.y());
            }
            // draw current node's point
            StdDraw.setPenRadius(pointRadius);
            StdDraw.setPenColor(StdDraw.BLACK);
            n.point.draw();

            // draw nodes in left sub-tree
            drawDFS(n.lb, h + 1);
            // draw nodes in right sub-tree
            drawDFS(n.rt, h + 1);
        }
    }

    // draw all points to standard draw
    // draw all subdivisons
    public void draw() {
        drawDFS(root, 0);
    }

    // helper method to implement range()
    private Queue<Point2D> range(Node n, RectHV rectRange, Queue<Point2D> setRange) {
        // base condition
        if (n == null) return setRange;

        // recursively search possible nodes and their sub-trees
        if (rectRange.intersects(n.rect)) {
            if (rectRange.contains(n.point)) setRange.enqueue(n.point);
            setRange = range(n.lb, rectRange, setRange);
            setRange = range(n.rt, rectRange, setRange);
        }
        return setRange;
    }

    // all points that are inside the rectangle (or on the boundary)
    // implement range() recursively
    public Iterable<Point2D> range(RectHV rect) {
        checkArgument(rect);
        Queue<Point2D> setRange = new Queue<>();
        setRange = range(root, rect, setRange);
        return setRange;
    }

    // helper method (recursive) to find the nearest neighbor in the tree to target point p
    private Point2D nearest(Node n, Point2D p, Point2D nearestP, int level) {
        // base condition
        if (n == null) return nearestP;

        // check if it is necessary to explore the current node
        if (n.rect.distanceSquaredTo(p) >= p.distanceSquaredTo(nearestP)) return nearestP;

        // update the nearest point if the distance from p to current node's point is shorter
        if (p.distanceSquaredTo(n.point) < p.distanceSquaredTo(nearestP)) nearestP = n.point;

        // Check the sub-trees of the current node to find the nearest point,
        // search the subtree on the same side as the target point p first
        int comparison = comparePoints(p, n.point, level);

        if (comparison < 0) {
            nearestP = nearest(n.lb, p, nearestP, level + 1);
            nearestP = nearest(n.rt, p, nearestP, level + 1);
        }
        else {
            nearestP = nearest(n.rt, p, nearestP, level + 1);
            nearestP = nearest(n.lb, p, nearestP, level + 1);
        }

        return nearestP;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        checkArgument(p);
        if (root == null) return null;

        Point2D nearestPoint = new Point2D(Double.MAX_VALUE, Double.MAX_VALUE);
        nearestPoint = nearest(root, p, nearestPoint, 0);
        return nearestPoint;
    }


    public static void main(String[] args) {

        // test constructor()
        KdTree testTree = new KdTree();
        KdTree testTree2 = new KdTree();
        PointSET testSet = new PointSET();

        // generate random points and rectangle for testing
        int NUMBER = 1000;
        int NEARESTNUM = 1000; // 1M

        // List of points for insertion runtime test
        ArrayList<Point2D> testPoints
                = new ArrayList<>();
        for (int i = 0; i < NUMBER; i++) {
            double x = StdRandom.uniform(0.0, 1.0);
            double y = StdRandom.uniform(0.0, 1.0);
            Point2D p = new Point2D(x, y);
            testPoints.add(p);
        }

        // List of points for nearest() runtime test
        ArrayList<Point2D> testPointList = new ArrayList<>();
        for (int i = 0; i < NEARESTNUM; i++) {
            testPointList
                    .add(new Point2D(StdRandom.uniform(0.0, 1.0), StdRandom.uniform(0.0, 1.0)));
            // System.out.println(testPointList.get(i));
        }

        // RectHV testRect = new RectHV(StdRandom.uniform(0.3, 0.5), StdRandom.uniform(0.2, 0.3),
        // StdRandom.uniform(0.52, 0.7), StdRandom.uniform(0.3, 0.6));


        // test runtime of insert(): inserting N points
        double runtime = 0.0;
        for (int i = 0; i < NUMBER; i++) {
            Stopwatch s = new Stopwatch();
            testTree.insert(testPoints.get(i));
            double elapsedTime = s.elapsedTime();
            runtime += elapsedTime;
        }

        StdOut.println("runtime of inserting " + NUMBER + " Points : " + runtime);


        // test runtime for nearest(): run time for finding the nearest point in the Kd-tree/PointSET
        // with N points insterted
        In in = new In(args[0]);
        while (in.hasNextLine()) {
            try {
                double x = in.readDouble();
                double y = in.readDouble();
                Point2D point = new Point2D(x, y);
                testTree2.insert(point);
                testSet.insert(point);
            }
            catch (NoSuchElementException e) {
                System.out.println("No more double tokens");
                System.out.println("size of the tree: " + testTree2.size());
                System.out.println("size of the point set: " + testSet.size());
                break;
            }
        }

        // runt time test for finding nearest point NEARESTNUM times in Kd-tree/PointSet
        double runtimeNearest = 0.0;
        for (int i = 0; i < NEARESTNUM; i++) {
            Stopwatch s = new Stopwatch();
            testSet.nearest(testPointList.get(i));
            double elapsedTime = s.elapsedTime();
            runtimeNearest += elapsedTime;
        }

        // System.out.println("nearest point test: " + testTree2.nearest(testPointList.get(2)));

        System.out.println(
                "runtime for performing " + NEARESTNUM + " nearest() for " + testSet.size()
                        + " inserted points: " + runtimeNearest);
        System.out.println(
                "Average runtime for performing 1 nearest() for " + testSet.size()
                        + " inserted points: " + (runtimeNearest / NEARESTNUM));


        /*
        // test size()
        assert (testTree.size() == NUMBER);
        assert (testSet.size() == NUMBER);
        assert (testTree2.size() == 0);

        StdOut.println("size of KdTree: " + testTree.size());
        StdOut.println("size of testSet: " + testSet.size());
        StdOut.println("size of KdTree2: " + testTree2.size());

        // test isEmpty()
        assert (!testTree.isEmpty());
        assert (!testSet.isEmpty());
        assert (testTree2.isEmpty());

        StdOut.println("Is KdTree empty? :" + testTree.isEmpty());
        StdOut.println("Is testSet empty? :" + testSet.isEmpty());
        StdOut.println("Is KdTree2 empty? :" + testTree2.isEmpty());


        // test insert(null)
        try {
            testTree.insert(null);
        }
        catch (IllegalArgumentException e) {
            StdOut.println(e);
        }


        // test contain()
        // access the instance variable, only for testing within the KdTree class
        Point2D pointIn = testPoints.get(3);
        Point2D pointOut = new Point2D(0.4, 0.5);
        assert (testTree.contains(pointIn));
        assert (testSet.contains(pointIn));
        assert (!testTree.contains(pointOut));
        assert (!testSet.contains(pointOut));
        StdOut.println("KdTree contains point " + pointIn + ": " + testTree.contains(pointIn));
        StdOut.println("testSet contains point " + pointIn + ": " + testSet.contains(pointIn));
        StdOut.println("KdTree contains point " + pointOut + ": " + testTree.contains(pointOut));
        StdOut.println("testSet contains point " + pointOut + ": " + testSet.contains(pointOut));


        // test range() with the test rectangle
        int rangeNumTree = 0;
        StdOut.println("points contained in rectangle " + testRect + " in KdTree : ");
        Iterable<Point2D> rangeResultTree = testTree.range(testRect);
        for (Point2D p : rangeResultTree) {
            StdOut.println(p);
            rangeNumTree += 1;
        }

        int rangeNumSet = 0;
        StdOut.println("points contained in rectangle " + testRect + " in testSet : ");
        Iterable<Point2D> rangeResultSet = testSet.range(testRect);
        for (Point2D p : rangeResultSet) {
            StdOut.println(p);
            rangeNumSet += 1;
        }
        // assert (rangeResultTree.equals(rangeResultSet));
        StdOut.println("number of points contained in rectangle " + testRect + " in KdTree : "
                               + rangeNumTree);
        StdOut.println("number of points contained in rectangle " + testRect + " in testSet : "
                               + rangeNumSet);


        // test nearest() with a test point
        Point2D nearestPointTree = testTree.nearest(testPoint);
        Point2D nearestPointSet = testSet.nearest(testPoint);
        assert (nearestPointTree.equals(nearestPointSet));
        StdOut.println(
                "point in tree that is nearest to point " + testPoint + " : " + nearestPointTree);
        StdOut.println(
                "point in Set that is nearest to point " + testPoint + " : " + nearestPointSet);

        // test draw()
        StdDraw.setPenRadius(0.005);
        StdDraw.setPenColor(StdDraw.GREEN);
        testRect.draw();
        testTree.draw();
        StdDraw.setPenRadius(0.02);
        StdDraw.setPenColor(StdDraw.ORANGE);
        testPoint.draw();
        StdDraw.setPenRadius(0.02);
        StdDraw.setPenColor(StdDraw.GREEN);
        nearestPointTree.draw();
        StdDraw.show();
         */

    }
}
