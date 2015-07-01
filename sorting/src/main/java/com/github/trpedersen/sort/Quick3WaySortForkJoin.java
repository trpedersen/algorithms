package com.github.trpedersen.sort;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.github.trpedersen.util.Stopwatch;
import edu.princeton.cs.introcs.StdIn;
import edu.princeton.cs.introcs.StdOut;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;

//import org.apache.commons.cli.*;


public class Quick3WaySortForkJoin<Key extends Comparable<? super Key>> extends Sort<Key> {

    private class QuicksortTask extends RecursiveAction {


        private final Key[] a;
        private final int lo;
        private final int hi;
        // private final int size;

        public QuicksortTask(Key[] a) {
            this(a, 0, a.length - 1);
        }

        public QuicksortTask(Key[] a, int size) {
            this(a, 0, size - 1);
        }

        public QuicksortTask(Key[] a, int lo, int hi) {
            this.a = a;
            this.lo = lo;
            this.hi = hi;
        }

        @Override
        protected void compute() {

            if (hi - lo < 15) {
                insertionSort(a, lo, hi);
                //Arrays.sort(a, lo, hi+1);
                return;
            }

            if (hi <= lo) return;

            int lt = lo, i = lo + 1, gt = hi;
            Key v = a[lo];
            while (i <= gt) {
                int cmp = a[i].compareTo(v);
                if (cmp < 0) exchange(a, lt++, i++);
                else if (cmp > 0) exchange(a, i, gt--);
                else i++;
                compares++;
            } // now a[lo..lt-1] < v = a[lt..gt] < a[gt+1..hi]
            //invokeAll(new QuicksortTask(a, lo, lt - 1), new QuicksortTask(a, gt + 1, hi));
            ForkJoinTask t1 = new QuicksortTask(a, lo, lt - 1).fork();
            new QuicksortTask(a, gt + 1, hi).invoke();
            if (t1 != null) {
                t1.join();
            }
        }

        private void insertionSort(Key[] a, int lo, int hi) {
            for (int i = lo; i <= hi; i++) {
                for (int j = i; j > lo && less(a[j], a[j - 1]); j--) {
                    exchange(a, j, j - 1);
                }
            }
        }

    }

    void sort(Key[] a) {
        sort(a, a.length);
    }

    private void sort(Key[] a, int size) {
        ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
        pool.invoke(new QuicksortTask(a, size));
    }

}
