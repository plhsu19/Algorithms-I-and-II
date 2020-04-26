/* *****************************************************************************
 *  Name: Pei-Lun Hsu
 *  Date: 25.04.2020
 *  Description: Implementation of Kd-Tree (BST) for range search and nearest-neighbor search
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class KdTree {

    private Node root;
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
    // orientation: h % 2 == 0 -> vertiacal, h % 2 == 1 -> horizonal
    private Node put(Point2D p, Node n, int h, double xmin, double ymin, double xmax, double ymax) {
        // base condition for the recursive:
        if (n == null) {
            RectHV rect = new RectHV(xmin, ymin, xmax, ymax);
            Node newNode = new Node(p, rect);
            size += 1;
            return newNode;
        }
        // even level: comparison of the x coordinate, vertical split.
        if (h % 2 == 0) {
            if (p.x() < n.point.x()) // p.x < node.x -> left child
                n.lb = put(p, n.lb, h + 1, n.rect.xmin(), n.rect.ymin(), n.point.x(),
                           n.rect.ymax());
            else if (p.x() > n.point.x()) // p.x > node.x -> rigt child
                n.rt = put(p, n.rt, h + 1, n.point.x(), n.rect.ymin(), n.rect.xmax(),
                           n.rect.ymax());
                // p.x == n.x, p.y != n.y -> right child
            else if (p.x() == n.point.x() && p.y() != n.point.y())
                n.rt = put(p, n.rt, h + 1, n.point.x(), n.rect.ymin(), n.rect.xmax(),
                           n.rect.ymax());
            else return n; // point == node.point -> point already exists in the tree
        }
        // odd level: comparison of y coordiante, horizontal split.
        else {
            if (p.y() < n.point.y()) // p.y < node.y -> bottom child
                n.lb = put(p, n.lb, h + 1, n.rect.xmin(), n.rect.ymin(), n.rect.xmax(),
                           n.point.y());
            if (p.y() > n.point.y()) // p.y > node.y -> top child
                n.rt = put(p, n.rt, h + 1, n.rect.xmin(), n.point.y(), n.rect.xmax(),
                           n.rect.ymax());
            if (p.y() == n.point.y() && p.x() != n.point.x())
                n.rt = put(p, n.rt, h + 1, n.rect.xmin(), n.point.y(), n.rect.xmax(),
                           n.rect.ymax());
            else return n;
        }
        return n;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        checkArgument(p);
        root = put(p, root, 0, 0.0, 0.0, 1.0, 1.0);
    }

    // helper method(recursive) to check whether the Kd-tree contains point p
    private boolean get(Point2D p, Node n, int h) {
        // base condition
        if (n == null) {
            return false;
        }
        // even level: comparison of the x coordinate, Left/Right.
        if (h % 2 == 0) {
            if (p.x() < n.point.x()) return get(p, n.lb, h + 1); // x < node.x -> check left child
            else if (p.x() > n.point.x())
                return get(p, n.rt, h + 1); // x > node.x -> check right child
            else if (p.x() == n.point.x() && p.y() != n.point.y())
                return get(p, n.rt, h + 1); // same x, different y -> check right child
            else return true; // same x, same y -> find the point p in the tree
        }
        // odd level: comparison of y coordiante, Top/Bottom.
        else {
            if (p.y() < n.point.y()) return get(p, n.lb, h + 1); // y < node.y -> check bottom child
            if (p.y() > n.point.y()) return get(p, n.rt, h + 1); // y > node.y -> check top child
            if (p.y() == n.point.y() && p.x() != n.point.x())
                return get(p, n.rt, h + 1); // same y, different x -> check top child
            else return true; // same y, same x -> find the point p in the tree
        }
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
    private Point2D nearest(Node n, Point2D p, Point2D nearestP, int h) {
        // base condition
        if (n == null) return nearestP;

        // check if it is necessary to explore the current node
        if (n.rect.distanceSquaredTo(p) >= p.distanceSquaredTo(nearestP)) return nearestP;

        // update the nearest point if the distance from p to current node's point is shorter
        if (p.distanceSquaredTo(n.point) < p.distanceSquaredTo(nearestP)) nearestP = n.point;

        // Check the sub-trees of the current node:
        // Even level, check which side of the vertical splitting line that p is on,
        // then search subtree on that side firstly
        if (h % 2 == 0) {
            if (p.x() < n.point.x()) {
                nearestP = nearest(n.lb, p, nearestP, h + 1);
                nearestP = nearest(n.rt, p, nearestP, h + 1);
            }
            else {
                nearestP = nearest(n.rt, p, nearestP, h + 1);
                nearestP = nearest(n.lb, p, nearestP, h + 1);
            }
        }
        // Odd level, check which side of the horizontal splitting line that p is on,
        // then search subtree on that side firstly
        else {
            if (p.y() < n.point.y()) {
                nearestP = nearest(n.lb, p, nearestP, h + 1);
                nearestP = nearest(n.rt, p, nearestP, h + 1);
            }
            else {
                nearestP = nearest(n.rt, p, nearestP, h + 1);
                nearestP = nearest(n.lb, p, nearestP, h + 1);
            }
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
        RectHV testRect = new RectHV(0.3, 0.2, 0.5, 0.5);
        Point2D testPoint = new Point2D(StdRandom.uniform(0.0, 1.0), StdRandom.uniform(0.0, 1.0));

        // test insert()
        for (int i = 0; i < 50; i++) {
            double x = StdRandom.uniform(0.0, 1.0);
            double y = StdRandom.uniform(0.0, 1.0);
            Point2D p = new Point2D(x, y);
            testTree.insert(p);
        }

        // test size()
        StdOut.println("size of KdTree: " + testTree.size());
        StdOut.println("size of KdTree2: " + testTree2.size());

        // test isEmpty()
        StdOut.println("Is KdTree empty? :" + testTree.isEmpty());
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
        Point2D pointIn = testTree.root.point;
        Point2D pointOut = new Point2D(0.4, 0.5);
        StdOut.println("testTree contains point " + pointIn + ": " + testTree.contains(pointIn));
        StdOut.println("testTree contains point " + pointOut + ": " + testTree.contains(pointOut));


        // test range() with a test rectangle
        StdOut.println("points contained in rectangle " + testRect + " : ");
        Iterable<Point2D> rangeResult = testTree.range(testRect);
        for (Point2D p : rangeResult) {
            StdOut.println(p);
        }

        // StdOut.println("distance from (0.4, 0.4) to testRectangle: " + testRect.distanceTo(new Point2D(0.4, 0.4)));

        // test nearest() with a test point
        Point2D nearestPoint = testTree.nearest(testPoint);
        StdOut.println(
                "point in tree that is nearest to point " + testPoint + " : " + nearestPoint);


        // test draw()
        StdDraw.setPenRadius(0.005);
        StdDraw.setPenColor(StdDraw.GREEN);
        testRect.draw();
        testTree.draw();
        StdDraw.setPenRadius(0.01);
        StdDraw.setPenColor(StdDraw.ORANGE);
        testPoint.draw();
        StdDraw.setPenRadius(0.01);
        StdDraw.setPenColor(StdDraw.GREEN);
        nearestPoint.draw();
        StdDraw.show();

    }
}
