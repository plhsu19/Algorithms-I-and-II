/* *****************************************************************************
 *  Name: Pei-Lun Hsu
 *  Date: 07022020
 *  Description: The test client of data structures RandomizedQueue
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {

        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> rdnQueue = new RandomizedQueue<String>();

        // add n string tokens from stdInput into randomized queue
        // running time ~ cn (linear to input n)
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            rdnQueue.enqueue(item);
        }
        // prints k items from the queue, uniformly at random
        // implementation: print first k items when iteration
        // total running time ~ cn : a. iterator index array construction: ~cn
        //                           b. iterator index array shuffle: ~cn
        //                           c. iterate k items: ~ck < ~cn
        int i = 0;
        for (String s : rdnQueue) {
            if (i < k) {   // e.g., k = 3, print first 3 iteration items in rqueue[idxArray[i]],
                // i = 0, 1, 2
                StdOut.println(s);
                i += 1;
            }
            else break;
        }

    }
}
