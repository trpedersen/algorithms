package com.github.trpedersen.sort;

public class HeapSort<Key extends Comparable<? super Key>> extends Sort<Key> {

    private boolean less(Key[] a, int i, int j) {
        compares++;
        return a[i-1].compareTo(a[j-1]) < 0;
    }

    @Override
    protected void exchange(Key[] a, int i, int j) {
        exchanges++;
        Key t = a[i-1];
        a[i-1] = a[j-1];
        a[j-1] = t;
    }

    private void swim(Key[] a, int k) {
        // bubble k up the tree until it is not less that it' parent
        // or until it is at the root
        while (k > 1 && less(a, k / 2, k)) {
            exchange(a, k / 2, k);
            k = k / 2;
        }
    }

    private void sink(Key a[], int k, int N) {
        // send down the tree until it is not greater than any of its children
        // or until it is at the end
        while (2 * k <= N) {
            int j = 2 * k; // lh child
            if (j < N && less(a, j, j + 1)) j++; // get the greatest child
            if (!less(a, k, j)) break; // no child is greater
            exchange(a, k, j); // greatest child becomes parent
            k = j; // keep sinking
        }
    }

    public void sort(Key[] a) {
        sort(a, a.length);
    }

    public void sort(Key[] a, int N) {
        for (int k = N / 2; k >= 1; k--) {
            sink(a, k, N);
        }
        while (N > 1) {
            exchange(a, 1, N--);
            sink(a, 1, N);
        }
    }
}
