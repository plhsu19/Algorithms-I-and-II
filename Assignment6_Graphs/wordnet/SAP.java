/* *****************************************************************************
 *  Name: Pei-Lun Hsu
 *  Date: .08.2020
 *  Description: implement data type SAP to find the shortest ancestral paths
 *               between any 2 vertex (or 2 vertices sets) in the given directed
 *               graph (as argument for constructor)
 **************************************************************************** */

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class SAP {

    private Digraph digraph;

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

    private void checkVertexArgument(int v) {
        if (v < 0 || v >= digraph.V()) {
            throw new IllegalArgumentException("The vertex argument is out of range!");
        }
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        // Check vertex arguments
        checkVertexArgument(w);
        checkVertexArgument(v);
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        // Check vertex arguments
        checkVertexArgument(w);
        checkVertexArgument(v);

    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        // check input iterable argument
        if (v == null || w == null) throwArgException();
        // check the range of vertex in v and w
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        // check input iterable argument
        if (v == null || w == null) throwArgException();
        // check the range of vertex in v and w

    }


    // do unit testing of this class
    public static void main(String[] args) {
        In in = new In(args[0]);
        // Constructor of Digraph: public Digraph(In in)
        // Initializes a digraph from the specified input stream.
        // The format is the number of vertices V, followed by the number of edges E, followed by E pairs of vertices, with each entry separated by whitespace.
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}
