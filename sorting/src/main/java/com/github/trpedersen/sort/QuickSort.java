package com.github.trpedersen.sort;

import com.github.trpedersen.util.Stopwatch;
import edu.princeton.cs.introcs.StdIn;
import edu.princeton.cs.introcs.StdOut;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;


public class QuickSort<Key extends Comparable<Key>> extends Sort<Key> {


    public void sort(Key[] a) {
        int N = a.length;
        sort(a, 0, N - 1);
    }

    public void sort(Key[] a, int size) {
        int N = size;
        sort(a, 0, N - 1);
    }

    private void sort(Key[] a, int lo, int hi) {
        if (hi <= lo + 15){
            insertionSort(a, lo, hi);
            return;
        }
        int j = partition(a, lo, hi);
        sort(a, lo, j - 1);
        sort(a, j + 1, hi);
    }

    private void insertionSort(Key[] a, int lo, int hi){
        for (int i = lo; i <= hi; i++){
            for (int j = i; j > lo && less(a[j], a[j-1]); j--){
                exchange(a, j, j-1);
            }
        }
    }

    private int partition(Key[] a, int lo, int hi) {
        // partition into a[lo..j-1], a[j], a[j+1..hi] and return j
        int i = lo, j = hi + 1; // left and right scan indices
        Key v = a[lo];
        while (true) {
            // scan right, scan left, check for scan complete, and exchange
            while (less(a[++i], v)) if (i == hi) break;
            while (less(v, a[--j])) if (j == lo) break;
            if(i>=j) break;
            exchange(a, i, j);
        }
        exchange(a, lo, j); // put partitioning item v into a[j];
        return j;
    }
}
