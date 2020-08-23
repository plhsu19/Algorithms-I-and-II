/* *****************************************************************************
 *  Name: Pei-Lun Hsu
 *  Date: 13.08.2020
 *  Description: Implement a ontological WordNet data-type which relationships of
 *  synsets, ie, sets of nouns with the same meanings, and their hypernyms, ie,
 *  ontological parent. The data-type WordNet also provides instance methods that
 *  help find the shortest ancestral path between any two nouns in a word net.
 **************************************************************************** */

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.HashMap;

public class WordNet {

    private HashMap<String, Stack<Integer>> synsetMap; // noun => [2, 5, 3, 9]
    private HashMap<Integer, String> nounMap; // 3: "A-horizon A_horizon"
    private Digraph wordnet;
    private int[] marked;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {

        // check if arguments are null
        checkArgument(synsets);
        checkArgument(hypernyms);

        // initialize a hash map to store the noun as key and its corresponding synset IDs as value
        synsetMap = new HashMap<>();

        // initialize a hash map to store the synset ID as key and the nouns it includes as value
        nounMap = new HashMap<>();

        // read in synsets file (.txt) and store in a hash map(string, int[]) with (noun, synsetId) as (key, value) pair
        int size = readSynsets(synsets);

        // initialized a Digraph with number of vertex = size
        wordnet = new Digraph(size);

        // read in hypernyms file (.txt) and store the is-a relationships(edges) into wordnet
        readHypernyms(hypernyms);

        // check if wordnet is a single rooted DAG (single rooted + acyclic)
        checkRootedDAG();

    }

    private void checkArgument(Object arg) {
        if (arg == null)
            throw new IllegalArgumentException(
                    "The argument of the method or constructor cannot be null!");
    }

    private void throwNotSingleRootedDAGException() {
        throw new IllegalArgumentException(
                "The wordnet is not a single rooted acyclic directed graph (Not DAG)");
    }

    private void throwNounNotExistException() {
        throw new IllegalArgumentException("The noun argument is not in the wordnet.");
    }

    // helper functions to read synsets
    private int readSynsets(String synsets) {
        int size = 0;

        // read in synsets file (.txt) and store in a hash map(string, int[]) with (noun, synsetId) as (key, value) pair
        In in = new In(synsets);
        // read in each synset line
        while (in.hasNextLine()) {
            size += 1;
            String line = in.readLine();
            String[] synFields = line.split(",");

            int synsetId = Integer.parseInt(synFields[0]);

            // save the (id: string of synset nouns) into hash map
            nounMap.put(synsetId, synFields[1]);

            String[] nouns = synFields[1].split(" ");

            // add (noun: synsetId) of the synset into hash map
            for (String noun : nouns) {
                if (!synsetMap.containsKey(noun)) {
                    synsetMap.put(noun, new Stack<Integer>());
                }
                synsetMap.get(noun).push(synsetId);
                // StdOut.println(noun);
                // StdOut.println(synsetId);
            }
        }
        return size;
    }

    // helper function to read in hypernyms (and build a Digraph)
    private void readHypernyms(String hypernyms) {
        In in = new In(hypernyms);

        // for each line (each synset's edge to its hypernyms), store the edges into the digraph
        while (in.hasNextLine()) {
            String line = in.readLine();
            String[] hyperFields = line.split(",");
            int synset = Integer.parseInt(hyperFields[0]);

            for (int i = 1; i < hyperFields.length; i++) {
                wordnet.addEdge(synset, Integer.parseInt(hyperFields[i]));
            }
        }
    }

    // helper function to check if wordnet is single rooted and acyclic
    private void checkRootedDAG() {
        int rootNum = 0;
        for (int v = 0; v < wordnet.V(); v++) {
            if (wordnet.outdegree(v) == 0) rootNum += 1;
            if (rootNum > 1) throwNotSingleRootedDAGException();
        }
        if (rootNum != 1) throwNotSingleRootedDAGException();
    }


    // helper function DFS used to check if wordnet (digraph) is acyclic
    // private void DFS(int v) {
    //     // if the vertex is already
    //     marked[v] = true;
    //
    //     for (int w : wordnet.adj(v)) {
    //         if (marked[v]) throw new IllegalArgumentException("the input digraph is not acyclic!");
    //         DFS(w);
    //     }
    //
    // }


    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return synsetMap.keySet();
    }

    // is the word a WordNet noun?
    // time complexity: O(1)
    public boolean isNoun(String word) {
        checkArgument(word);
        return synsetMap.containsKey(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        // check if the argument is null
        checkArgument(nounA);
        checkArgument(nounB);

        // Check if any of the noun arguments is not a WordNet noun
        if (!isNoun(nounA) || !isNoun(nounB)) throwNounNotExistException();

        SAP shortestAncestralPath = new SAP(wordnet);
        int d = shortestAncestralPath.length(synsetMap.get(nounA), synsetMap.get(nounB));

        return d;
    }


    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path
    public String sap(String nounA, String nounB) {
        // check if the argument is null
        checkArgument(nounA);
        checkArgument(nounB);

        // Check if any of the noun arguments is not a WordNet noun
        if (!isNoun(nounA) || !isNoun(nounB)) throwNounNotExistException();

        SAP shortestAncestralPath = new SAP(wordnet);
        int ancestor = shortestAncestralPath.ancestor(synsetMap.get(nounA), synsetMap.get(nounB));

        return nounMap.get(ancestor);
    }

    // do unit testing of this class
    public static void main(String[] args) {
        String synsetsFile = args[0];
        String hypernymsFile = args[1];

        WordNet testWordNet = new WordNet(synsetsFile, hypernymsFile);

        // check nouns()
        for (String noun : testWordNet.nouns()) {
            StdOut.println(noun);
        }

        // check isNoun(String noun)
        StdOut.println("Enter the first noun to check if it is in the wordnet: ");
        String noun1 = StdIn.readString();
        StdOut.println(
                String.format("The noun %s is in the wordnet: %b", noun1,
                              testWordNet.isNoun(noun1)));

        StdOut.println("Enter the first noun to check if it is in the wordnet: ");
        String noun2 = StdIn.readString();
        StdOut.println(
                String.format("The noun %s is in the wordnet: %b", noun2,
                              testWordNet.isNoun(noun2)));


        StdOut.println("Enter the first noun: ");
        while (!StdIn.isEmpty()) {
            String nounA = StdIn.readString();
            StdOut.println("Enter the second noun: ");
            String nounB = StdIn.readString();
            StdOut.println(
                    String.format("The distance of SAP between %s and %s: %d",
                                  nounA, nounB, testWordNet.distance(nounA, nounB)));
            StdOut.println(
                    String.format("The common ancestor of SAP between %s and %s: %s",
                                  nounA, nounB, testWordNet.sap(nounA, nounB)));

        }


    }
}















