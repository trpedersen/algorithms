package com.github.trpedersen.sort;

import java.util.Comparator;

public class MergeBUSort<Key extends Comparable<Key>> extends Sort<Key> {

    private Key[] aux;

    public void merge(Key[] a, int lo, int mid, int hi) {
        int i = lo, j = mid + 1;

        System.arraycopy(a, lo, aux, lo, (hi-lo+1));

        for (int k = lo; k <= hi; k++) {
            if (i > mid)
                a[k] = aux[j++];
            else if (j > hi)
                a[k] = aux[i++];
            else if (less(aux[j], aux[i]))
                a[k] = aux[j++];
            else
                a[k] = aux[i++];
            exchanges++;
        }
    }

    public void sort(Key[] a) {
        int N = a.length;
        sort(a, N);
    }

    public void sort(Key[] a, int N) {
        aux = (Key[]) new Comparable[a.length];
        for (int sz = 1; sz < N; sz = sz + sz) {
            for (int lo = 0; lo < N - sz; lo += sz + sz) {
                merge(a, lo, lo+sz-1, Math.min(lo+sz+sz-1, N-1));
            }
        }
    }

    private void sort(Key[] a, int lo, int hi) {
        if (hi <= lo)
            return;
        int mid = lo + (hi - lo) / 2;
        sort(a, lo, mid);
        sort(a, mid + 1, hi);
        merge(a, lo, mid, hi);
    }

}
