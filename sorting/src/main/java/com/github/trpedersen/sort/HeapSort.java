package com.github.trpedersen.sort;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.github.trpedersen.util.Stopwatch;
import edu.princeton.cs.introcs.StdIn;
import edu.princeton.cs.introcs.StdOut;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//import org.apache.commons.cli.*;


public class HeapSort<Key extends Comparable<Key>> extends Sort {


    @Parameter(names = "-p", description = "print sorted array to stdout")
    private boolean print = false;

    @Parameter(names = "-i", description = "treat input as integers")
    private boolean asInt = false;

    @Parameter(names = "-s", description = "shuffle input before sorting")
    private boolean shuffle = false;

    @Parameter(names = "-f", description = "read from file")
    private String file;

    @Parameter(names = "--help", help = true)
    private boolean help;

    private boolean less(Comparable<Key>[] a, int i, int j) {
        compares++;
        return a[i-1].compareTo((Key) a[j-1]) < 0;
    }

    private void exch(Comparable<Key>[] a, int i, int j) {
        exchanges++;
        Comparable<Key> t = a[i-1];
        a[i-1] = a[j-1];
        a[j-1] = t;
    }

    private void swim(Comparable<Key>[] a, int k) {
        // bubble k up the tree until it is not less that it' parent
        // or until it is at the root
        while (k > 1 && less(k / 2, k)) {
            exch(a, k / 2, k);
            k = k / 2;
        }
    }

    private void sink(Comparable<Key> a[], int k, int N) {
        // send down the tree until it is not greater than any of its children
        // or until it is at the end
        while (2 * k <= N) {
            int j = 2 * k; // lh child
            if (j < N && less(a, j, j + 1)) j++; // get the greatest child
            if (!less(a, k, j)) break; // no child is greater
            exch(a, k, j); // greatest child becomes parent
            k = j; // keep sinking
        }
    }


    public void sort(Comparable[] a) {
        sort(a, a.length);
    }

    public void sort(Comparable<Key>[] a, int N) {
        for (int k = N / 2; k >= 1; k--) {
            sink(a, k, N);
        }
        while (N > 1) {
            exch(a, 1, N--);
            sink(a, 1, N);
        }
    }


    private void shuffle(Comparable[] a, int N) {
        for (int i = 0; i < N; i++) {
            int r = i + (int) (Math.random() * (N - i)); // between i and N-1;
            exchange(a, i, r);
        }
    }
}
