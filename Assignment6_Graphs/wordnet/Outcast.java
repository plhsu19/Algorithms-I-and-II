/* *****************************************************************************
 *  Name: Pei-Lun Hsu
 *  Date: 23.08.2020
 *  Description: find the outcast, i.e., the noun has most far relationship with
 *               other nouns, among a set of nouns in the wordnet by using
 *               brute-force methods (O(n^2))
 *               Noted: Algorithm could be improved if necessary (eg. the input
 *                      size n is large.
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {

    private final WordNet wordnet;

    // constructor takes a WordNet object
    public Outcast(WordNet wordnet) {
        checkArgument(wordnet);
        this.wordnet = wordnet;
    }

    private void checkArgument(Object arg) {
        if (arg == null)
            throw new IllegalArgumentException(
                    "The argument of the method or constructor cannot be null!");
    }

    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns) {
        int maxDistance = 0;
        String outcastNoun = null;
        for (String noun : nouns) {
            int distance = 0;
            for (int i = 0; i < nouns.length; i++) {
                distance += wordnet.distance(noun, nouns[i]);
            }
            if (distance > maxDistance) {
                maxDistance = distance;
                outcastNoun = noun;
            }
        }
        return outcastNoun;
    }

    // see test client below
    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
}
