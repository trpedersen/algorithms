package com.github.trpedersen.sort;

import com.github.trpedersen.util.Stopwatch;
import edu.princeton.cs.introcs.StdIn;
import edu.princeton.cs.introcs.StdOut;

import java.util.Arrays;


public class InsertionSort extends Sort {

    public void sort1(Comparable[] a) {
        int N = a.length;
        for (int i = 1; i < N; i++) {
            // insert a[i] among a[i-1], a[i-2], a[i-3], ...
            for (int j = i; j > 0 && less(a[j], a[j - 1]); j--) {
                exchange(a, j, j - 1);
            }
        }
    }

    public void sort2(Comparable[] a) {
        int N = a.length;
        for (int i = 1; i < N; i++) {
            // insert a[i] among a[i-1], a[i-2], a[i-3], ...
            Comparable x = a[i];
            int index = i;
            for (int j = i; j > 0 && less(x, a[j - 1]); j--) {
                a[j] = a[j - 1];
                index = j - 1;
                exchanges++;
            }
            a[index] = x;
        }
    }

    public void sort(Comparable[] a) {
        int N = a.length;

        // set sentinel
        for (int i = N-1; i > 0; i--){
            if(less(a[i], a[i-1])){
                exchange(a, i, i-1);
            }
        }

        for (int i = 2; i < N; i++) {
            // insert a[i] among a[i-1], a[i-2], a[i-3], ...
            Comparable x = a[i];
            int j = i;
            while(less(x, a[j-1])){
                a[j] = a[j-1];
                exchanges++;
                j--;
            }
            a[j] = x;
        }
    }

//    public void sort(Comparable[] a, int lo, int hi) {
//
//        // set sentinel
//        for (int i = hi-1; i > lo; i--){
//            if(less(a[i], a[i-1])){
//                exchange(a, i, i-1);
//            }
//        }
//
//        for (int i = 2; i < hi; i++) {
//            // insert a[i] among a[i-1], a[i-2], a[i-3], ...
//            Comparable x = a[i];
//            int j = i;
//            while(less(x, a[j-1])){
//                a[j] = a[j-1];
//                exchanges++;
//                j--;
//            }
//            a[j] = x;
//        }
//    }

    public static void main(String[] args) {

        Comparable a[];
        Comparable b[];
        Comparable c[];

        if (args.length > 0 && args[0].equals("-i")) {
            StdOut.println("sorting ints");
            a = Arrays.stream(StdIn.readAllInts()).boxed().toArray(Integer[]::new);
        } else {
            StdOut.println("sorting strings");
            a = StdIn.readAllStrings();
        }
        b = a.clone();
        c = a.clone();



        InsertionSort s = new InsertionSort();

        Stopwatch sw = new Stopwatch();
        s.sort1(a);
        double elapsed = sw.elapsedTime();
        assert s.isSorted(a);
       //s.show(a);
        s.showStats(a);
        StdOut.printf("elapsed: %f\n", elapsed);

        s.reset();
        sw = new Stopwatch();
        s.sort2(b);
        elapsed = sw.elapsedTime();
        assert s.isSorted(b);
        // s.show(a);
        s.showStats(b);
        StdOut.printf("elapsed: %f\n", elapsed);

        s.reset();
        sw = new Stopwatch();
        s.sort(c);
        elapsed = sw.elapsedTime();
        assert s.isSorted(c);
        // s.show(a);
        s.showStats(c);
        StdOut.printf("elapsed: %f\n", elapsed);
    }
}
