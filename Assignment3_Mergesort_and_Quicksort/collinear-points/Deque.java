/* *****************************************************************************
 *  Name: Pei-Lun Hsu
 *  Date: 06022020
 *  Description: Implement double-enede queue data structure by using double-
 * direction linked list
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node first, last;
    private int size = 0;


    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
    }

    // (private) node object for storing items in linked list
    // Question: why don't need to define class as Node<Item> ?
    private class Node {
        // Q: if I set item to private can other instance methods in Deque access it? e.g. node1.item = someItem
        Item item;
        Node pre;
        Node next;
    }

    // Check if client tries to add a null
    private void isValidArgumemt(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("the item to add cannot be null");
        }
    }

    // Check if client tries to call remove() when deque is empty
    private void checkRemoveEmpty() {
        if (isEmpty()) {
            throw new NoSuchElementException("cannot remove item from empty deque");
        }
    }

    // is the deque empty?
    public boolean isEmpty() {
        return (first == null) || (last == null);
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        isValidArgumemt(item);
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.next = oldfirst;
        first.pre = null;
        if (oldfirst == null) last = first;
        else oldfirst.pre = first;
        size += 1;
    }

    // add the item to the back
    public void addLast(Item item) {
        isValidArgumemt(item);
        Node oldlast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        last.pre = oldlast;
        if (oldlast == null) first = last;
        else oldlast.next = last;
        size += 1;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        checkRemoveEmpty();
        Item item = first.item;
        first = first.next;
        if (isEmpty()) last = null;
        else first.pre = null;
        size -= 1;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        checkRemoveEmpty();
        Item item = last.item;
        last = last.pre;
        if (isEmpty()) first = null;
        else last.next = null;
        size -= 1;
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    // Q: why I don't have to use InteratorDeque<Item> to specify the generic but need to specify Iterator<Item>? OK
    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException("don't support remove() for iterator");
        }

        public Item next() {
            if (current == null)
                throw new NoSuchElementException("there is no more item in iteration");
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {

        Deque<Integer> deque = new Deque<Integer>();
        Deque<Integer> deque1 = new Deque<Integer>();

        // call addFirst 10 times and then call removeLast 10 times and print the result
        // to check two functions. The output should be 0, 1, 2, 3, ..., 9
        for (int i = 0; i < 10; i++) {
            deque.addFirst(i);
        }
        while (!deque.isEmpty()) {
            StdOut.println(deque.removeLast());
        }
        StdOut.println("-------------------------------------------------------");

        // randomly call addFirst, addLast, removeFirst, removeLast, size and isEmpty
        // to check the combination of various functions
        deque.addFirst(StdRandom.uniform(30));
        deque.addLast(StdRandom.uniform(30));
        for (int i = 0; i < 10; i++) {
            int r = StdRandom.uniform(0, 8);
            if (r < 2) deque.addFirst(StdRandom.uniform(30));
            else if (r < 3) StdOut.println("remove from last: " + deque.removeLast());
            else if (r < 5) deque.addLast(StdRandom.uniform(30));
            else if (r < 6) StdOut.println("remove from first: " + deque.removeFirst());
            else if (r < 7) StdOut.println("current size of deque is: " + deque.size());
            else if (r < 8) StdOut.println("deque is empty: " + deque.isEmpty());
            StdOut.println(i);
        }
        StdOut.println("-------------------------------------------------------");

        // test iterator() to print all items in a Deque<Double>
        for (int i = 0; i < 5; i++) {
            deque1.addFirst(StdRandom.uniform(5));
        }
        for (int j : deque1) {
            for (int k : deque1) {
                StdOut.print(j + " - " + k);
                StdOut.println();
            }
        }
    }

}
