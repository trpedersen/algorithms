package com.github.trpedersen.sort;

import edu.princeton.cs.introcs.StdIn;
import com.github.trpedersen.util.Stopwatch;
import edu.princeton.cs.introcs.StdOut;

import java.util.Arrays;

/**
 * Created by timpe_000 on 19/06/2015.
 */
public class SelectionSort<Key extends Comparable<? super Key>> extends Sort<Key> {

    void sort(Key[] a) {
        int N = a.length;
        for (int i = 0; i < N; i++) {
            // exchange a[i] with smallest entry in a[i+1..N]
            int min = i;
            for (int j = i + 1; j < N; j++) {
                if (less(a[j], a[min])) min = j;
            }
            exchange(a, i, min);
        }
    }
}
