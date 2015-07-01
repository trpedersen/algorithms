package com.github.trpedersen.sort;

public class Quick3WaySort<Key extends Comparable<? super Key>> extends Sort<Key> {

    public void sort(Key[] a) {
        int N = a.length;
        sort(a, 0, N - 1);
    }

    public void sort(Key[] a, int size) {
        sort(a, 0, size - 1);
    }


    private void sort(Key[] a, int lo, int hi) {
        if (hi <= lo + 15) {
            insertionSort(a, lo, hi);
            return;
        }
//        if (hi <= lo) return;
        int lt = lo, i = lo + 1, gt = hi;
        Key v = a[lo];
        while (i <= gt) {
            int cmp = a[i].compareTo(v);
            if (cmp < 0) exchange(a, lt++, i++);
            else if (cmp > 0) exchange(a, i, gt--);
            else i++;
        }
        // now a[lo..lt-1] < v = a[lt..gt] < a[gt+1..hi]
        sort(a, lo, lt - 1);
        sort(a, gt + 1, hi);
    }

    private void insertionSort(Key[] a, int lo, int hi) {
        for (int i = lo; i <= hi; i++) {
            for (int j = i; j > lo && less(a[j], a[j - 1]); j--) {
                exchange(a, j, j - 1);
            }
        }
    }

}
