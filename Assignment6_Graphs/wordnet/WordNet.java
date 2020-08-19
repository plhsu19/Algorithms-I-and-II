/* *****************************************************************************
 *  Name: Pei-Lun Hsu
 *  Date: 13.08.2020
 *  Description: Use a DAG data structure to implement a linguistic WordNet which specifies
 *  the relations between synsets, ie, sets of nouns with the same meanings, and hypernyms
 **************************************************************************** */

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;

import java.util.HashMap;

public class WordNet {

    private HashMap<String, Stack<Integer>> synsetMap;
    private Digraph wordnet;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {

        // check if arguments are null
        checkArgument(synsets);
        checkArgument(hypernyms);

        // initialize a hash map to store the nouns in synsets and their corresponding id
        synsetMap = new HashMap<>();

        // read in synsets file (.txt) and store in a hash map(string, int[]) with (noun, synsetId) as (key, value) pair
        int size = readSynsets(synsets);

        // initialized a Digraph with number of vertex = size
        wordnet = new Digraph(size);

        // read in hypernyms file (.txt) and store the is-a relationships(edges) into wordnet
        readHypernyms(hypernyms);

        // StdOut.println(wordnet.V());
        // StdOut.println(wordnet.E());


        // check if wordnet is acyclic: use simple DFS

    }

    private void checkArgument(Object arg) {
        if (arg == null)
            throw new IllegalArgumentException(
                    "the argument of the method or constructor cannot be null!");
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
            String[] nouns = synFields[1].split(" ");

            // add (noun, synsetId) of the synset into hash map
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


    // returns all WordNet nouns
    public Iterable<String> nouns() {
        Stack<String> testStack = new Stack<>();

        return testStack;
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        checkArgument(word);
        return true;
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        // Any of the noun arguments in distance() or sap() is not a WordNet noun

        return 2;
    }


    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        // Any of the noun arguments in distance() or sap() is not a WordNet noun
        return "test";
    }

    // do unit testing of this class
    public static void main(String[] args) {
        String synsetsFile = args[0];
        String hypernymsFile = args[1];

        WordNet wordnet = new WordNet(synsetsFile, hypernymsFile);


    }
}















