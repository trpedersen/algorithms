package com.github.trpedersen.sort;

import com.github.trpedersen.util.Stopwatch;
import edu.princeton.cs.introcs.StdIn;
import edu.princeton.cs.introcs.StdOut;

import java.util.Arrays;
import java.util.List;


public class ShellSort extends Sort {


    public void sort(Comparable[] a) {
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

    public static void main(String[] args) {

        Comparable a[] = null;
        boolean showArray = false;
        boolean sortInts = false;

        if (args.length > 0) {
            List<String> opts = Arrays.asList(args);
            if (opts.contains("-i")) {
                sortInts = true;
            }
            if (opts.contains("-p")) {
                showArray = true;
            }
        }
        if (sortInts) {
            StdOut.println("sorting ints");
            a = Arrays.stream(StdIn.readAllInts()).boxed().toArray(Integer[]::new);
        } else {
            StdOut.println("sorting strings");
            a = StdIn.readAllStrings();
        }

        ShellSort s = new ShellSort();

        Stopwatch sw = new Stopwatch();
        s.sort(a);
        double elapsed = sw.elapsedTime();
        assert s.isSorted(a);
        if (showArray) s.show(a);
        s.showStats(a);
        StdOut.printf("elapsed: %f\n", elapsed);


    }
}
