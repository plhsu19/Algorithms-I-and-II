/* *****************************************************************************
 *  Name: Pei-Lun Hsu
 *  Date: 13.08.2020
 *  Description: Use a DAG data structure to implement a linguistic WordNet which specifies
 *  the relations between synsets, ie, sets of nouns with the same meanings, and hypernyms
 **************************************************************************** */

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.HashMap;

public class WordNet {

    private HashMap<String, Stack<Integer>> synsetMap;
    private Digraph wordnet;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        // The input to the constructor does not correspond to a rooted DAG: check through datatype DAG's methods

        synsetMap = new HashMap<>();
        Digraph wordnet;

        // read in synsets file (.txt) and store in a hash map(string, int[] with (noun, synsetId) as (key, value) pair
        In in = new In(synsets);
        // read in each synset line
        while (in.hasNextLine()) {
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
                StdOut.println(noun);
                StdOut.println(synsetMap.get(noun));
            }
        }

    }

    private void checkArgument(Object arg) {
        if (arg == null)
            throw new IllegalArgumentException("the argument of this method cannot be null!");
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        Stack<String> testStack = new Stack<>();

        return testStack;
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
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















