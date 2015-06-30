package com.github.trpedersen.sort;

public class InsertionSort<Key extends Comparable<Key>> extends Sort<Key> {

    public void sort1(Key[] a) {
        int N = a.length;
        for (int i = 1; i < N; i++) {
            // insert a[i] among a[i-1], a[i-2], a[i-3], ...
            for (int j = i; j > 0 && less(a[j], a[j - 1]); j--) {
                exchange(a, j, j - 1);
            }
        }
    }

    public void sort2(Key[] a) {
        int N = a.length;
        for (int i = 1; i < N; i++) {
            // insert a[i] among a[i-1], a[i-2], a[i-3], ...
            Key x = a[i];
            int index = i;
            for (int j = i; j > 0 && less(x, a[j - 1]); j--) {
                a[j] = a[j - 1];
                index = j - 1;
                exchanges++;
            }
            a[index] = x;
        }
    }

    public void sort(Key[] a) {
        int N = a.length;

        // set sentinel
        for (int i = N-1; i > 0; i--){
            if(less(a[i], a[i-1])){
                exchange(a, i, i-1);
            }
        }

        for (int i = 2; i < N; i++) {
            // insert a[i] among a[i-1], a[i-2], a[i-3], ...
            Key x = a[i];
            int j = i;
            while(less(x, a[j-1])){
                a[j] = a[j-1];
                exchanges++;
                j--;
            }
            a[j] = x;
        }
    }

}
