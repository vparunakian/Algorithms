/* *****************************************************************************
 *  Name:              Volodymyr Parunakian
 *  Coursera User ID:  b5c72803b8152684bda8f7a799308782
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private static final byte OPEN = 1;  // Binary 0001
    private static final byte CONNECTEDTOTOP = 2;  // Binary 0010
    private static final byte CONNECTEDTOBOTTOM = 4;  // Binary 0100

    private final WeightedQuickUnionUF unionUF;
    private boolean percolatesFlag;
    private byte[] statuses;
    private final int n;
    private int openCounter;

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be larger than 0");
        }

        this.n = n;
        percolatesFlag = false;
        // we store cell statuses in an array N * N
        statuses = new byte[n * n];
        // creating union N * N
        unionUF = new WeightedQuickUnionUF(n * n);
    }

    private boolean checkBit(int value, int bit) {
        return (value & 1 << bit) != 0;
    }

    private boolean statusIsOpen(int status) {
        return checkBit(status, 0);
    }

    private boolean statusIsFull(int status) {
        return statusIsOpen(status) && checkBit(status, 1);
    }

    private boolean statusPercolates(int status) {
        return statusIsFull(status) && checkBit(status, 2);
    }

    private void validateIndices(int row, int col) {
        if (row <= 0 || row > n) {
            throw new IllegalArgumentException("index " + row + " is not between 1 and " + n);
        }
        if (col <= 0 || col > n) {
            throw new IllegalArgumentException("index " + col + " is not between 1 and " + n);
        }
    }

    private int indexFor(int row, int col) {
        return (row - 1) * n + (col - 1);
    }

    private void checkNeighborsFor(int row, int col) {
        openCounter += 1;
        byte combinedStatus = OPEN;
        int currentIndex = indexFor(row, col);
        // If first row, the site will be full immediately
        if (row == 1) {
            combinedStatus |= CONNECTEDTOTOP;
        }
        else {
            int neighboorIndex = indexFor(row - 1, col);
            combinedStatus = uniteNeighboor(neighboorIndex, currentIndex, combinedStatus);
        }
        // if last row, the site is open and connected to bottom
        if (row == n) {
            combinedStatus |= CONNECTEDTOBOTTOM;
        }
        else {
            int neighboorIndex = indexFor(row + 1, col);
            combinedStatus = uniteNeighboor(neighboorIndex, currentIndex, combinedStatus);
        }
        // check horizontally placed sites
        if (col > 1) {
            int neighboorIndex = indexFor(row, col - 1);
            combinedStatus = uniteNeighboor(neighboorIndex, currentIndex, combinedStatus);
        }
        if (col < n) {
            int neighboorIndex = indexFor(row, col + 1);
            combinedStatus = uniteNeighboor(neighboorIndex, currentIndex, combinedStatus);
        }

        statuses[currentIndex] = combinedStatus;
        int rootIndex = unionUF.find(currentIndex);
        statuses[rootIndex] = combinedStatus;
        if (statusPercolates(statuses[rootIndex])) {
            percolatesFlag = true;
        }
    }

    private byte uniteNeighboor(int neighboorIndex, int currentIndex, byte combinedStatus) {
        int rootIndex = unionUF.find(neighboorIndex);
        int status = statuses[rootIndex];
        if (statusIsOpen(status)) {
            combinedStatus |= status;
            unionUF.union(neighboorIndex, currentIndex);
        }
        return combinedStatus;
    }

    public void open(int row, int col) {
        validateIndices(row, col);
        if (isOpen(row, col)) {
            return;
        }

        checkNeighborsFor(row, col);
    }

    public boolean isOpen(int row, int col) {
        validateIndices(row, col);
        int status = statuses[indexFor(row, col)];
        return statusIsOpen(status);
    }

    public boolean isFull(int row, int col) {
        validateIndices(row, col);
        int rootIndex = unionUF.find(indexFor(row, col));
        int status = statuses[rootIndex];
        return statusIsFull(status);
    }

    public int numberOfOpenSites() {
        return openCounter;
    }

    public boolean percolates() {
        return percolatesFlag;
    }

    public static void main(String[] args) {
        Percolation perc = new Percolation(3);
        perc.open(1, 3);
        perc.open(2, 3);
        perc.open(3, 3);
        perc.open(3, 1);
        StdOut.println(perc.percolates());
        StdOut.println(perc.isOpen(3, 1));
        StdOut.println(perc.isFull(3, 1));
    }
}
