/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

public class Test {
    public static void main(String[] args) {

        int[][] testPuzzle1 = { { 8, 1, 3 }, { 4, 0, 2 }, { 7, 6, 5 } };
        int[][] testPuzzle2 = { { 8, 1, 3 }, { 4, 0, 2 }, { 7, 6, 5 } };
        int[][] testPuzzle3 = { { 8, 1, 3 }, { 4, 5, 2 }, { 7, 6, 0 } };
        Board testBoard1 = new Board(testPuzzle1);

        StdOut.println("move of testBoard1: " + testBoard1.move);

    }
}
