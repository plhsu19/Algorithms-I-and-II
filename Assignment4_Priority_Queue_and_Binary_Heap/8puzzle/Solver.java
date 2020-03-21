/* *****************************************************************************
 *  Name: Pei-Lun Hsu
 *  Date: 18.03.2020
 *  Description: Implement A* algorithm based on piority queue to solve the
 *               8-Puzzle problem
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

public class Solver {

    private Node goalNode = null;
    private boolean solvable = false;
    private int minMoves = -1;


    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null)
            throw new IllegalArgumentException("the arg of class Solve cannot be null");

        // initial the root Node with the initial board and add it to the priority queue
        // initial the root node of the twin game tree and start add it to its corresponding priority queue
        Node rootNode = new Node(initial, null, 0);
        Node twinNode = new Node(rootNode.board.twin(), null, 0);
        NodeComparator c = new NodeComparator();
        MinPQ<Node> piorityQ = new MinPQ<Node>(c);
        MinPQ<Node> twinPQ = new MinPQ<>(c);
        piorityQ.insert(rootNode);
        twinPQ.insert(twinNode);

        // start the exploration of nodes until the PQ is empty or the goalNode is found
        while (!piorityQ.isEmpty() && !twinPQ.isEmpty()) {
            Node currentN = piorityQ.delMin();

            // twin tree
            Node twinCurrent = twinPQ.delMin();

            // check whether the goalNode is dequeued
            // if yes, break the exploration(while loop),
            // and store the dequeued goal Node
            if (currentN.board.isGoal()) {
                solvable = true;
                goalNode = currentN;
                minMoves = currentN.moves;
                break;
            }

            // twin tree: check goal
            if (twinCurrent.board.isGoal()) {
                solvable = false;
                break;
            }

            // if visiting node is not goal, add its neighboring nodes (boards)
            // of current node into tree
            for (Board neighborB : currentN.board.neighbors()) {

                if (currentN.parent != null)
                    if (neighborB.equals(currentN.parent.board)) continue;

                int nMoves = currentN.moves + 1;  // calculate new G
                Node neighborNode = new Node(neighborB, currentN, nMoves);
                piorityQ.insert(neighborNode);
            }

            // twin: add neighnoring nodes(boards) of current node into the twin tree
            for (Board twinNeighborB : twinCurrent.board.neighbors()) {

                if (twinCurrent.parent != null)
                    if (twinNeighborB.equals(twinCurrent.parent.board)) continue;

                int nMoves = twinCurrent.moves + 1;  // calculate new G
                Node twinNeighborNode = new Node(twinNeighborB, twinCurrent, nMoves);
                twinPQ.insert(twinNeighborNode);
            }
        }

    }

    //  Node class for building the game tree
    private class Node implements Comparable<Node> {
        private final Board board; // board in current node
        private final Node parent;
        private final int moves;      // G
        private final int manhattanD; // H
        private final int priority;         // F

        // construct a search node in game tree
        public Node(Board board, Node parent, int moves) {
            this.board = board;
            this.parent = parent;
            this.moves = moves;
            manhattanD = this.board.manhattan();
            priority = this.moves + manhattanD;
        }

        // implement interface Comparable<Node>
        public int compareTo(Node that) {
            return Integer.compare(this.priority, that.priority);
        }
    }

    // build a comparator<Node>
    // constructor is omitted
    private static class NodeComparator implements Comparator<Node> {

        public int compare(Node a, Node b) {
            if (a.priority < b.priority) return -1;
            else if (a.priority > b.priority) return +1;
            else return 0;
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return solvable;
    }

    // min number of moves to solve initial board
    public int moves() {
        return minMoves;
    }

    // sequence of boards in a shortest solution
    public Iterable<Board> solution() {
        Stack<Board> solutionS = new Stack<Board>();
        if (solvable) {
            Node solutionNode = goalNode;
            // enqueue all the boards on the solution path of nodes
            solutionS.push(solutionNode.board);
            while (solutionNode.parent != null) {
                solutionS.push(solutionNode.parent.board);
                solutionNode = solutionNode.parent;
            }
            return solutionS;
        }
        else return null;
    }

    // test client (see below)
    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
