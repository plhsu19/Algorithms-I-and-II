/* *****************************************************************************
 *  Name: Pei-Lun Hsu
 *  Date: 15.08.2020
 *  Description: Implement a data type SAP to find the shortest ancestral paths
 *               between any 2 vertex (or 2 vertices sets) in the given directed
 *               graph (as argument for constructor)
 **************************************************************************** */

import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class SAP {

    private final Digraph digraph;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        // check if argument is null
        if (G == null) throwArgException();

        // make a defensive copy of Digraph G
        digraph = new Digraph(G);
    }

    private void throwArgException() {
        throw new IllegalArgumentException("The argument cannot be null!");
    }


    private boolean iterableIsEmpty(Iterable<Integer> v) {
        if (v == null) throwArgException();
        return !v.iterator().hasNext();
    }

    // helper function to find the length of shortest path form 2 BFDP objects
    private int shortestLength(BreadthFirstDirectedPaths vBFS, BreadthFirstDirectedPaths wBFS) {
        int shortestPath = Integer.MAX_VALUE;
        for (int i = 0; i < digraph.V(); i++) {
            if (vBFS.hasPathTo(i) && wBFS.hasPathTo(i)) {
                int path = vBFS.distTo(i) + wBFS.distTo(i);
                if (path < shortestPath) shortestPath = path;
            }
        }
        if (shortestPath == Integer.MAX_VALUE) shortestPath = -1;

        return shortestPath;
    }

    // helper function to find the ancestor of shortest path form 2 BFDS objects
    // could be further modularized with shortestLength()!!!!
    private int spa(BreadthFirstDirectedPaths vBFS, BreadthFirstDirectedPaths wBFS) {
        int spa = -1;
        int shortestPath = Integer.MAX_VALUE;

        for (int i = 0; i < digraph.V(); i++) {
            if (vBFS.hasPathTo(i) && wBFS.hasPathTo(i)) {
                int path = vBFS.distTo(i) + wBFS.distTo(i);
                if (path < shortestPath) {
                    shortestPath = path;
                    spa = i;
                }
            }
        }
        return spa;
    }


    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {

        // construct 2 BreadthFirstDirectedPaths objects to conduct the BFS from v and w
        // implicitly check if v and w are null in BreadthFirstDirectedPaths's constructor
        BreadthFirstDirectedPaths vBFS = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths wBFS = new BreadthFirstDirectedPaths(digraph, w);

        return shortestLength(vBFS, wBFS);
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {

        // construct 2 BreadthFirstDirectedPaths objects to conduct the BFS from v and w
        // implicitly check if v and w are null in BreadthFirstDirectedPaths's constructor
        BreadthFirstDirectedPaths vBFS = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths wBFS = new BreadthFirstDirectedPaths(digraph, w);

        return spa(vBFS, wBFS);
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        // check if input iterable argument is null implicitly by BreadthFirstDirectedPaths

        // check if the iterable arguments is null or empty
        // is null: throw exception
        // is empty: return -1
        if (iterableIsEmpty(v) || iterableIsEmpty(w)) return -1;


        // construct 2 BreadthFirstDirectedPaths objects to conduct the BFS from iterable v and iterable w
        // implicitly check if v and w are null or if v and w contains null item in BreadthFirstDirectedPaths's constructor
        BreadthFirstDirectedPaths vBFS = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths wBFS = new BreadthFirstDirectedPaths(digraph, w);

        return shortestLength(vBFS, wBFS);
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        // check if input iterable argument is null implicitly by BreadthFirstDirectedPaths

        // check if the iterable arguments is null or empty
        // is null: throw exception
        // is empty: return -1
        if (iterableIsEmpty(v) || iterableIsEmpty(w)) return -1;

        // construct 2 BreadthFirstDirectedPaths objects to conduct the BFS from v and w
        // implicitly check if v and w are null or if v and w contains null item in BreadthFirstDirectedPaths's constructor
        BreadthFirstDirectedPaths vBFS = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths wBFS = new BreadthFirstDirectedPaths(digraph, w);

        return spa(vBFS, wBFS);
    }


    // do unit testing of this class
    public static void main(String[] args) {
        In in = new In(args[0]);
        // Constructor of Digraph: public Digraph(In in)
        // Initializes a digraph from the specified input stream.
        // The format is the number of vertices V, followed by the number of edges E, followed by E pairs of vertices, with each entry separated by whitespace.
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        Stack<Integer> v = new Stack<>();
        Stack<Integer> w = new Stack<>();

        StdOut.println("Digraph: " + G.toString());

        StdOut.println("enter the vertex num of v set: ");
        int numV = StdIn.readInt();
        for (int i = 0; i < numV; i++) {
            int vertex = StdIn.readInt();
            v.push(vertex);
        }

        StdOut.println("enter the vertex num of w set: ");
        int numW = StdIn.readInt();
        for (int i = 0; i < numW; i++) {
            int vertex = StdIn.readInt();
            w.push(vertex);
        }

        // int v = StdIn.readInt();
        // int w = StdIn.readInt();
        int length = sap.length(v, w);
        int ancestor = sap.ancestor(v, w);
        StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);

        // test unit for testing single int v, w input
        // while (!StdIn.isEmpty()) {
        //     int v = StdIn.readInt();
        //     int w = StdIn.readInt();
        //     int length   = sap.length(v, w);
        //     int ancestor = sap.ancestor(v, w);
        //     StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        // }
    }
}
