/* *****************************************************************************
 *  Name:              Volodymyr Parunakian
 *  Coursera User ID:  b5c72803b8152684bda8f7a799308782
 *  Last modified:     16/03/2020
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private int endIndex;
    private Item[] data;

    // construct an empty randomized queue
    public RandomizedQueue() {
        endIndex = 0;
        data = (Item[]) new Object[1];
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < endIndex; i++)
            copy[i] = data[i];
        data = copy;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return endIndex == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return endIndex;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException("The added item should not be null");

        if (endIndex == data.length)
            resize(2 * data.length);
        data[endIndex++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException(
                    "Can not remove an element from an empty queue");
        }

        int randomIdx = StdRandom.uniform(endIndex);
        Item item = data[randomIdx];
        data[randomIdx] = data[--endIndex];
        data[endIndex] = null;
        if (endIndex > 0 && endIndex == data.length / 4)
            resize(data.length / 2);
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException(
                    "Can not return an element from an empty queue");
        }

        int randomIdx = StdRandom.uniform(endIndex);
        return data[randomIdx];
    }

    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private int current = 0;
        private final int[] shuffledIndexes = StdRandom.permutation(endIndex);

        public boolean hasNext() {
            return current < endIndex && data[current] != null;
        }

        public Item next() {
            if (current >= endIndex)
                throw new NoSuchElementException("No items to return");

            return data[shuffledIndexes[current++]];
        }

        public void remove() {
            throw new UnsupportedOperationException(
                    "This iterator does not support `remove` function");
        }
    }

    public static void main(String[] args) {
        RandomizedQueue<Integer> rQueue = new RandomizedQueue<Integer>();
        StdOut.println(rQueue.isEmpty()); // should be true
        StdOut.println(rQueue.size()); // should be 0
        for (int i = 0; i < 10; i++) {
            rQueue.enqueue(i);
        }
        StdOut.println(rQueue.isEmpty()); // should be false
        StdOut.println(rQueue.size()); // should be 10
        for (int element : rQueue) {
            StdOut.print(element + " ");
        }

        StdOut.println("\n" + rQueue.sample()); // any random
        for (int i = 0; i < 10; i++) {
            StdOut.print(rQueue.dequeue() + " ");
        }
        StdOut.println("\n" + rQueue.size()); // should be 0
        StdOut.println(rQueue.isEmpty()); // should be true
    }
}
