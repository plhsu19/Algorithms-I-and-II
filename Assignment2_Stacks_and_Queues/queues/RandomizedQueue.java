/* *****************************************************************************
 *  Name: Pei-Lun
 *  Date: 07022020
 *  Description: implement randomized queue DS through resized array
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] rqueue;
    private int n;
    // number of elements in queueStdOut.println("---------------------------------------------------------------------");

    // construct an empty randomized queue
    public RandomizedQueue() {
        rqueue = (Item[]) new Object[2];
        n = 0;
    }

    // Check if client tries to add a null into rqueue
    private void isValidArgumemt(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("You are trying to add a null into queue!");
        }
    }

    // Check if client tries to call deque(), sample() when deque is empty
    private void checkRemoveEmpty() {
        if (isEmpty()) {
            throw new NoSuchElementException("Cannot deque/sample item from an empty deque");
        }
    }

    // resize the array into target capacity
    private void resize(int capacity) {
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++) {  // ~ cn
            temp[i] = rqueue[i];
        }
        rqueue = temp;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return n == 0;  // constant
    }

    // return the number of items on the randomized queue
    public int size() {
        return n;       // constant
    }

    // add the item
    public void enqueue(Item item) {
        isValidArgumemt(item);
        if (n == rqueue.length) resize(2 * rqueue.length);  // ~ cn
        rqueue[n++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        checkRemoveEmpty();
        int r = StdRandom.uniform(n); // r is a random int within[0, n),    ~ c
        Item item = rqueue[r];        // return a random item at rqueue[r], ~ c
        // move all items with index larger than r to one index left(index - 1)
        for (int i = r; i < (n - 1); i++) rqueue[i] = rqueue[i + 1];    //  ~ cn
        rqueue[--n] = null;
        if (n > 0 && n == rqueue.length / 4) resize(rqueue.length / 2); //  ~ cn
        return item;
    }

    // return a random item (but do not remove it)
    // constant
    public Item sample() {
        checkRemoveEmpty();
        int r = StdRandom.uniform(n);
        return rqueue[r];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RqueueIterator();
    }

    // iterator object for Randomized queue
    private class RqueueIterator implements Iterator<Item> {

        private final int[] idxArray;
        private int i = n;

        public RqueueIterator() {
            idxArray = new int[n];  // n could be 0, which will create an empty int array
            for (int j = 0; j < n; j++) idxArray[j] = j; // ~ cn (linear)
            StdRandom.shuffle(idxArray);                 // ~ cn (linear)
        }

        public boolean hasNext() {
            return i > 0; // constant
        }

        public Item next() {
            if (i <= 0) throw new NoSuchElementException("there is no more item for iteration");
            return rqueue[idxArray[--i]]; // constant
        }

        public void remove() {
            throw new UnsupportedOperationException("don't support remove() for iterator");
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
        RandomizedQueue<Double> rq1 = new RandomizedQueue<Double>();

        StdOut.println("test for continuous enqueue then dequeue");
        for (int i = 0; i < 10; i++) {
            rq.enqueue(i);
        }
        for (int i = 0; i < 10; i++) {
            StdOut.print(rq.dequeue() + " ");
        }
        StdOut.println("size of rqueue = " + rq.size());

        StdOut.println("---------------------------------------------------------------------");
        StdOut.println("test for randomly call one of enqueue, dequeue, sample, "
                               + "isEmpty and size mthods");
        for (int i = 0; i < 1; i++) rq.enqueue(i);
        for (int i = 0; i < 20; i++) {
            StdOut.print(i + ": ");
            int r = StdRandom.uniform(0, 5);
            if (r < 1) rq.enqueue(StdRandom.uniform(30));
            else if (r < 2) StdOut.println("remove randomly: " + rq.dequeue());
            else if (r < 3) StdOut.println("sample randomly: " + rq.sample());
            else if (r < 4) StdOut.println("current size of reque is: " + rq.size());
            else if (r < 5) StdOut.println("reque is empty: " + rq.isEmpty());
        }

        StdOut.println("---------------------------------------------------------------------");
        StdOut.println("test for iterator");
        for (int i = 0; i < 5; i++) {
            double item = i;
            rq1.enqueue(item);
        }
        for (double j : rq1) {
            for (double k : rq1) {
                StdOut.println(j + " - " + k);
            }
        }
    }
}
