/* *****************************************************************************
 *  Name: Pei-Lun Hsu
 *  Date: 15012020
 *  Description: First assignment Union Find of algorithms
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final int size;
    private final int numOfSites;
    private int numOfOpenSites = 0;
    private boolean[] openArray; // close: 0 (initial), open = 1
    private final WeightedQuickUnionUF id;


    // creates n-by-n grid, with all sites initially blocked
    // test first
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException("index n out of bound");
        size = n;
        numOfSites = n * n; // eg: 6 * 6 = 36
        openArray = new boolean[numOfSites + 2]; // n * n + up root + bottom root
        openArray[numOfSites + 1] = true; // open at initial
        openArray[0] = true;              // open at initial
        id = new WeightedQuickUnionUF((numOfSites + 2));
    }

    // mapping 2D indices to 1D index
    private int intxyTo1D(int r, int c) {
        int idx = (r - 1) * size + c; // eg, (3, 3) with size 6 -> 2 * 6 + 3 = 15
        return idx;
    }

    // indices validation
    private void idxValidarion(int row, int col) {
        if (row <= 0 || row > size || col <= 0 || col > size)
            throw new IllegalArgumentException("index row or col out of bounds");
    }

    // opens the site (row, col) if it is not open already
    // validata + open + union
    // test first
    public void open(int row, int col) {
        idxValidarion(row, col);
        if (!isOpen(row, col)) {
            numOfOpenSites += 1;
            int idx = intxyTo1D(row, col);
            openArray[idx] = true;
            if (row == 1) {
                id.union(0, idx);
            }
            if (row == size) {
                id.union((numOfSites + 1), idx);
            }

            // connect neighboring open sites
            if ((row - 1) > 0 && isOpen(row - 1, col)) {
                int idxUp = intxyTo1D(row - 1, col);
                id.union(idx, idxUp);
            }
            if ((row + 1) <= size && isOpen(row + 1, col)) {
                int idxDown = intxyTo1D(row + 1, col);
                id.union(idx, idxDown);
            }
            if ((col - 1) > 0 && isOpen(row, col - 1)) {
                id.union(idx, idx - 1);

            }
            if ((col + 1) <= size && isOpen(row, col + 1)) {
                id.union(idx, idx + 1);
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        idxValidarion(row, col);
        int idx = intxyTo1D(row, col);
        return (openArray[idx]);
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        idxValidarion(row, col);
        int idx = intxyTo1D(row, col);
        boolean checkFull = false;
        checkFull = id.connected(idx, 0);
        return checkFull;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return numOfOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        boolean checkPercolates = false;
        checkPercolates = id.connected(0, numOfSites + 1);
        return checkPercolates;
    }

    public static void main(String[] args) {

        int inputSize = 6;
        Percolation exp = new Percolation(inputSize);
        System.out.println("input size = " + inputSize);


        // test open() with index out of range
        System.out.println(("test the open() with indices out of ranges"));
        // exp.open(0, 0);
        // exp.open(33, 22);

        // test open() with correct and boundary case
        System.out.println(("test the open() with correct and boundary cases"));
        exp.open(1, 6);
        exp.open(2, 6);
        exp.open(3, 5);

        // check isOpen() function
        System.out.println("site(1, 6) is open: " + exp.isOpen(1, 6));
        System.out.println("site(2, 6) is open: " + exp.isOpen(2, 6));
        System.out.println("site(3, 6) is open: " + exp.isOpen(3, 6));
        System.out.println("site(3, 5) is open: " + exp.isOpen(3, 5));
        // System.out.println("site(300, 200) is open: " + exp.isOpen(300, 200));


        // check isFull()
        System.out.println("site(1,6) is full:" + exp.isFull(1, 6));
        System.out.println("site(2,6) is full:" + exp.isFull(2, 6));
        System.out.println("site(3,6) is full:" + exp.isFull(3, 6));
        System.out.println("site(3,5) is full:" + exp.isFull(3, 5));
        // System.out.println("site(300,200) is full:" + exp.isFull(300, 200));

        // check numberOfOpenSites()
        System.out.println("number of open sites: " + exp.numberOfOpenSites());

        for (int i = 1; i <= inputSize; i++) {
            exp.open(i, 6); // j = 6
        }

        // check percolates()
        System.out.println("Whether the map is percolate: " + exp.percolates());
    }
}
