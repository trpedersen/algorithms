package com.github.trpedersen.sort;

import edu.princeton.cs.introcs.StdIn;
import edu.princeton.cs.introcs.StdOut;

public class MaxPQ<Key extends Comparable<? super Key>> {

    private Key[] pq; // heap ordered complete binary tree
    private int N = 0; // in pq[1..N] with pq[0] unused

    public MaxPQ(int maxN) {
        pq = (Key[]) new Comparable[maxN + 1];
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public int size() {
        return N;
    }

    public void insert(Key v) {
        pq[++N] = v;
        swim(N);
    }

    public Key delMax() {
        Key max = pq[1]; // retrieve max key from top
        exch(1, N--);   // exchange with last item
        pq[N + 1] = null;  // avoid loitering
        sink(1);        // restore heap property
        return max;
    }

    private boolean less(int i, int j) {
        return pq[i].compareTo(pq[j]) < 0;
    }

    private void exch(int i, int j) {
        Key t = pq[i];
        pq[i] = pq[j];
        pq[j] = t;
    }

    private void swim(int k){
        // bubble k up the tree until it is not less that it' parent
        // or until it is at the root
        while (k > 1 && less(k/2, k)){
            exch(k/2, k);
            k = k/2;
        }
    }

    private void sink(int k){
        // send down the tree until it is not greater than any of its children
        // or until it is at the end
        while (2*k <= N){
            int j = 2 * k; // lh child
            if (j < N && less(j, j+1)) j++; // get the greatest child
            if(!less(k, j)) break; // no child is greater
            exch(k, j); // greatest child becomes parent
            k = j; // keep sinking
        }
    }

    public static void main(String[] args) {
        MaxPQ<String> pq = new MaxPQ<String>(30000000);
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-")) pq.insert(item);
            else if (!pq.isEmpty()) StdOut.print(pq.delMax() + " ");
        }
        StdOut.println("(" + pq.size() + " left on pq)");
    }
}
