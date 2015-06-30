package com.github.trpedersen.sort;

import com.github.trpedersen.util.Stopwatch;
import edu.princeton.cs.introcs.StdIn;
import edu.princeton.cs.introcs.StdOut;

import java.util.Arrays;
import java.util.List;


public class ShellSort<Key extends Comparable<Key>> extends Sort<Key> {

    public void sort(Key[] a) {
        int N = a.length;
        int h = 1;

        while (h < N / 3) h = 3 * h + 1; // 1, 4, 13, 40, 121, 364, 1093

        while (h >= 1) {
            for (int i = h; i < N; i++) {
                // insert a[i] among a[i-h], a[i-2*h], a[i-3*h], ...
                for (int j = i; j >= h && less(a[j], a[j - h]); j -= h) {
                    exchange(a, j, j - h);
                }
            }
            h = h / 3;
        }
    }
}
