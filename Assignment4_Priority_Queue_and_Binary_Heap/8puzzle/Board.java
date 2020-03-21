/* *****************************************************************************
 *  Name: Pei-Lun Hsu
 *  Date: 16.03.2020
 *  Description: Create a Board data-type that models an n-by-n board with sliding tiles
 **************************************************************************** */

import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Board {

    private final int n;
    private final int[][] tiles;
    private final int iB, jB;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        n = tiles.length;
        this.tiles = new int[n][n];
        // defensively copy the values of tiles from arg to private variable tiles
        int iOfBlank = 0;
        int jOfBlank = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                this.tiles[i][j] = tiles[i][j];
                if (tiles[i][j] == 0) {
                    iOfBlank = i;
                    jOfBlank = j;
                }
            }
        }
        iB = iOfBlank;
        jB = jOfBlank;

    }

    // string representation of this board
    public String toString() {
        StringBuilder board = new StringBuilder();
        board.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                board.append(String.format("%2d ", tiles[i][j]));
            }
            board.append("\n");
        }
        return board.toString();
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of tiles out of place
    public int hamming() {
        int hDistance = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] != 0 && (tiles[i][j] != i * n + j + 1)) {
                    hDistance += 1;
                }
            }
        }
        return hDistance;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int mDistance = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] != 0 && (tiles[i][j] != i * n + j + 1)) {
                    int iGoal = (tiles[i][j] - 1) / n;
                    int jGoal = (tiles[i][j] - 1) % n;
                    mDistance += (Math.abs(iGoal - i) + Math.abs(jGoal - j));
                }
            }
        }
        return mDistance;
    }


    // is this board the goal board?
    public boolean isGoal() {

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] != 0 && (tiles[i][j] != i * n + j + 1)) return false;
            }
        }
        return true;
    }

    // does this board equal y?
    // Implement equals() according to Java's conventions
    public boolean equals(Object y) {
        if (this == y) return true;
        if (y == null) return false;
        if (this.getClass() != y.getClass()) return false;
        Board that = (Board) y;
        
        if (this.dimension() != that.dimension()) return false;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] != that.tiles[i][j]) return false;
            }
        }
        return true;
    }

    // all neighboring boards
    // Details: return a Stack<Board> object (which implements Interable<Board>) that
    // contains neighbor boards w.r.t. current board
    public Iterable<Board> neighbors() {

        Stack<Board> neighborBoards = new Stack<Board>();

        // move the left tile to the blank (if exits)
        if (jB - 1 >= 0) {
            tiles[iB][jB] = tiles[iB][jB - 1];
            tiles[iB][jB - 1] = 0;
            Board leftTile = new Board(tiles);
            neighborBoards.push(leftTile);
            tiles[iB][jB - 1] = tiles[iB][jB];
            tiles[iB][jB] = 0;
        }

        // move the right tile to the blank (if exits)
        if (jB + 1 < n) {
            tiles[iB][jB] = tiles[iB][jB + 1];
            tiles[iB][jB + 1] = 0;
            Board rightTile = new Board(tiles);
            neighborBoards.push(rightTile);
            tiles[iB][jB + 1] = tiles[iB][jB];
            tiles[iB][jB] = 0;
        }

        // move the up tile to the blank (if exits)
        if (iB - 1 >= 0) {
            tiles[iB][jB] = tiles[iB - 1][jB];
            tiles[iB - 1][jB] = 0;
            Board upTile = new Board(tiles);
            neighborBoards.push(upTile);
            tiles[iB - 1][jB] = tiles[iB][jB];
            tiles[iB][jB] = 0;
        }

        // move the down tile to the blank (if exits)
        if (iB + 1 < n) {
            tiles[iB][jB] = tiles[iB + 1][jB];
            tiles[iB + 1][jB] = 0;
            Board downTile = new Board(tiles);
            neighborBoards.push(downTile);
            tiles[iB + 1][jB] = tiles[iB][jB];
            tiles[iB][jB] = 0;
        }

        return neighborBoards;
    }


    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {

        int[] idx = new int[4]; // exchangable indices for twin
        boolean foundFirst = false;
        boolean foundSecond = false;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == 0) continue;
                else if (!foundFirst) {
                    idx[0] = i;
                    idx[1] = j;
                    foundFirst = true;
                }
                else if (!foundSecond) {
                    idx[2] = i;
                    idx[3] = j;
                    foundSecond = true;
                }
                else break;
            }
            if (foundSecond) break;
        }

        // exchange the tiles
        int temp = tiles[idx[0]][idx[1]];
        tiles[idx[0]][idx[1]] = tiles[idx[2]][idx[3]];
        tiles[idx[2]][idx[3]] = temp;
        Board twin = new Board(tiles);

        // change back to original tiles
        tiles[idx[2]][idx[3]] = tiles[idx[0]][idx[1]];
        tiles[idx[0]][idx[1]] = temp;

        return twin;
    }


    // unit testing (not graded)
    public static void main(String[] args) {
        int[][] testPuzzle1 = { { 0, 1, 3 }, { 4, 8, 2 }, { 7, 6, 5 } };
        int[][] testPuzzle2 = { { 8, 1, 3 }, { 4, 0, 2 }, { 7, 6, 5 } };
        int[][] testPuzzle3 = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 0 } };
        Board testBoard1 = new Board(testPuzzle1);
        Board testBoard2 = new Board(testPuzzle2);
        Board testBoard3 = new Board(testPuzzle3);
        StdOut.println("testBoard1: ");
        StdOut.println(testBoard1);
        StdOut.println("Hamming distance: " + testBoard1.hamming());
        StdOut.println("Manhattan distance: " + testBoard1.manhattan());
        StdOut.println("testBoard1 reaches Goal: " + testBoard1.isGoal());
        StdOut.println("testBoard3 reaches Goal: " + testBoard3.isGoal());
        StdOut.println("testBoard3's Manhattan Distance: " + testBoard3.manhattan());
        StdOut.println("testBoard1 is equal to testBoard2: " + testBoard1.equals(testBoard2));
        StdOut.println("testBoard1 is equal to testBoard3: " + testBoard1.equals(testBoard3));
        StdOut.println("--------------------------------------------------------------------");
        StdOut.println("neighbors of testBoard1: ");
        Iterable<Board> neighborsOfTB1 = testBoard1.neighbors();
        for (Board neighbor : neighborsOfTB1) {
            StdOut.println(neighbor);
        }
        StdOut.println("testBoard1 after calling neighbors(): ");
        StdOut.println(testBoard1);
        StdOut.println("--------------------------------------------------------------------");
        StdOut.println("the twin of testBoard1: ");
        StdOut.println(testBoard1.twin());
        StdOut.println(testBoard1.twin());
        StdOut.println("testBoard1 after calling twin(): ");
        StdOut.println(testBoard1);

    }
}
