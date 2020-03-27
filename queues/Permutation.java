/* *****************************************************************************
 *  Name:              Volodymyr Parunakian
 *  Coursera User ID:  b5c72803b8152684bda8f7a799308782
 *  Last modified:     16/03/2020
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Permutation {
    public static void main(String[] args) {
        int n = 0;
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> rQueue = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            n++;
            String readStr = StdIn.readString();
            // we use reservoir sampling to swap items to maintain constant size
            if (n > k)
                if (StdRandom.uniform(n) < k)
                    rQueue.dequeue();
                else
                    continue;

            rQueue.enqueue(readStr);
        }
        for (int i = 0; i < k; i++) {
            StdOut.println(rQueue.dequeue());
        }
    }
}
