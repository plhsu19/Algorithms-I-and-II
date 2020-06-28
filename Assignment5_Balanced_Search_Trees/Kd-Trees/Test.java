/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

public class Test {

    public static class Point {
        private int x;
        private int y;

        public Point(int xc, int yc) {
            x = xc;
            y = yc;
        }
    }

    public static class PointKey {
        private Point p;
        private boolean deleted;

        public PointKey(Point pc) {
            p = pc;
            deleted = false;
        }

        public String toString() {
            return "(x, y): " + p.x + " , " + p.y;
        }
    }


    public static void main(String[] args) {
        Point pointTest = new Point(1, 2);
        PointKey pointKeyTest = new PointKey(pointTest);

        System.out.println(pointKeyTest);


    }
}
