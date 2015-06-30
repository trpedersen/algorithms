package com.github.trpedersen.sort;

public class MergeSort<Key extends Comparable<Key>> extends Sort<Key> {

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
        sort(a, 0, N-1);
    }

    public void sort(Key[] a, int size) {
        aux = (Key[]) new Comparable[size];
        sort(a, 0, size-1);
    }

    private void sort(Key[] a, int lo, int hi){
        if(hi <= lo)
            return;
        int mid = lo + (hi-lo)/2;
        sort(a, lo, mid);
        sort(a, mid+1, hi);
        merge(a, lo, mid, hi);

    }
}
