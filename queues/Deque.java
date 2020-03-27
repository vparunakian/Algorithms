/* *****************************************************************************
 *  Name:              Volodymyr Parunakian
 *  Coursera User ID:  b5c72803b8152684bda8f7a799308782
 *  Last modified:     16/03/2020
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node first = null;
    private Node last = null;
    private int size = 0;

    private class Node {
        Item item;
        Node next = null;
        Node previous = null;
    }

    // construct an empty deque
    public Deque() {

    }

    private void validateItem(Item item) {
        if (item == null) throw new IllegalArgumentException("The added item should not be null");
    }

    private void checkIfEmpty() {
        if (isEmpty()) throw new NoSuchElementException("The deque is empty");
    }

    private void assignFirstAsLastIfNeeded() {
        // if size is 0 the first item should be the same as last
        if (size == 0) first = last;
    }

    private void assignLastAsFirstIfNeeded() {
        // if size is 0 the first item should be the same as last
        if (size == 0) last = first;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return first == null && last == null;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        validateItem(item);
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;

        if (oldFirst != null) {
            oldFirst.previous = first;
        }

        assignLastAsFirstIfNeeded();
        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        validateItem(item);
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.previous = oldLast;

        if (oldLast != null) {
            oldLast.next = last;
        }

        assignFirstAsLastIfNeeded();
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        checkIfEmpty();
        size--;
        Item item = first.item;
        first = first.next;
        if (first != null) {
            first.previous = null;
        }

        assignLastAsFirstIfNeeded();

        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        checkIfEmpty();
        size--;
        Item item = last.item;
        last = last.previous;
        if (last != null) {
            last.next = null;
        }

        assignFirstAsLastIfNeeded();

        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (current == null) {
                throw new NoSuchElementException("No items to return");
            }

            Item item = current.item;
            current = current.next;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException(
                    "This iterator does not support `remove` function");
        }
    }

    // unit testing
    public static void main(String[] args) {
        Deque<String> deque = new Deque<String>();
        StdOut.println(deque.isEmpty()); // should be true
        StdOut.println(deque.size()); // should be 0
        deque.addFirst("First");
        deque.addFirst("NewFirst");
        deque.addLast("Last");
        deque.addLast("NewLast");

        for (String element : deque) {
            StdOut.print(element + " "); // should be NewFirst First Last NewLast
        }
        StdOut.println();
        StdOut.println(deque.size()); // should be 4
        StdOut.println(deque.removeFirst()); // should be "NewFirst"
        StdOut.println(deque.removeLast()); // should be "NewLast"
        StdOut.println(deque.removeLast()); // should be "Last"
        StdOut.println(deque.removeLast()); // should be "First"

        StdOut.println(deque.size()); // should be 0
        StdOut.println(deque.isEmpty()); // should be true
    }
}
