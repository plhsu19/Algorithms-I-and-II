/* *****************************************************************************
 *  Name: Pei-Lun Hsu
 *  Date: 13.08.2020
 *  Description: Use a DAG data structure to implement a linguistic WordNet which specifies
 *  the relations between synsets, ie, sets of nouns with the same meanings, and hypernyms
 **************************************************************************** */

public class WordNet {

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        checkArgument(synsets);
        checkArgument(hypernyms);
        // The input to the constructor does not correspond to a rooted DAG: check through datatype DAG's methods


    }

    private void checkArgument(Object arg) {
        if (arg == null)
            throw new IllegalArgumentException("the argument of this method cannot be null!");
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {

    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        checkArgument((word));
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        checkArgument(nounA);
        checkArgument(nounB);
        // Any of the noun arguments in distance() or sap() is not a WordNet noun
    }


    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        checkArgument(nounA);
        checkArgument(nounB);
        // Any of the noun arguments in distance() or sap() is not a WordNet noun
    }

    // do unit testing of this class
    public static void main(String[] args) {

    }
}
