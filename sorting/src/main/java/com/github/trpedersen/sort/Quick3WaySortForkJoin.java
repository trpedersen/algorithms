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


public class Quick3WaySortForkJoin extends Sort {


    @Parameter(names = "-p", description = "print sorted array to stdout")
    private boolean print = false;

    @Parameter(names = "-i", description = "treat input as integers")
    private boolean asInt = false;

    @Parameter(names = "-s", description = "shuffle input before sorting")
    private boolean shuffle = false;

    @Parameter(names = "--help", help = true)
    private boolean help;

    private class QuicksortTask extends RecursiveAction {


        private final Comparable[] a;
        private final int lo;
        private final int hi;
        // private final int size;

        public QuicksortTask(Comparable[] a) {
            this(a, 0, a.length - 1);
        }

        public QuicksortTask(Comparable[] a, int size) {
            this(a, 0, size - 1);
        }

        public QuicksortTask(Comparable[] a, int lo, int hi) {
            this.a = a;
            this.lo = lo;
            this.hi = hi;
        }

        @Override
        protected void compute() {

            if (hi - lo < 5000) {
                insertionSort(a, lo, hi);
                //Arrays.sort(a, lo, hi+1);
                return;
            }

            // StdOut.printf("compute: lo: %d, hi:%d\n", lo, hi);

            if (hi <= lo) return;

            int lt = lo, i = lo + 1, gt = hi;
            Comparable v = a[lo];
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

        private void insertionSort(Comparable[] a, int lo, int hi) {
            for (int i = lo; i <= hi; i++) {
                for (int j = i; j > lo && less(a[j], a[j - 1]); j--) {
                    exchange(a, j, j - 1);
                }
            }
        }

    }

    void sort(Comparable[] a) {
        sort(a, a.length);
    }

    private void sort(Comparable[] a, int size) {
        ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
        pool.invoke(new QuicksortTask(a, size));
    }

    public static void main(String[] args) throws IOException {

        Comparable a[] = null;
        boolean showArray = false;
        boolean sortInts = false;
        boolean useStdIn = true;
        int size = 0;
        String file;

        Quick3WaySortForkJoin s = new Quick3WaySortForkJoin();
        new JCommander(s, args);


        if (args.length > 0) {
            List<String> opts = Arrays.asList(args);
            if (opts.contains("-i")) {
                sortInts = true;
            }
            if (opts.contains("-p")) {
                showArray = true;
            }
            if (opts.contains("-f")) {
                int i = opts.indexOf("-f");
                file = opts.get(i + 1);
                useStdIn = false;
            }
        }

        if (s.asInt) {
            StdOut.println("sorting ints");
            a = Arrays.stream(StdIn.readAllInts()).boxed().toArray(Integer[]::new);
            size = a.length;
        } else {
            StdOut.println("sorting strings");

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String[] tokens = new String[30000000];
            int tokenCount = 0;
            String token;
            while ((token = reader.readLine()) != null) {
                tokens[tokenCount] = token;
                tokenCount++;
                if (tokenCount == 30000000) {
                    break;
                }
            }

            a = tokens;
            size = tokenCount;
        }

        StdOut.printf("data loaded, items: %d\n", size);
        if (s.shuffle) {
            StdOut.print("shuffling...");
            s.shuffle(a, size);
            StdOut.println("done");
        }


        Stopwatch sw = new Stopwatch();
        s.sort(a, size);
        double elapsed = sw.elapsedTime();
        assert s.isSorted(a);
        if (s.print) s.dump(a, size);
        s.showStats(a, size);
        StdOut.printf("elapsed: %f\n", elapsed);


    }

    private void shuffle(Comparable[] a, int N) {
        for (int i = 0; i < N; i++) {
            int r = i + (int) (Math.random() * (N - i)); // between i and N-1;
            exchange(a, i, r);
        }
    }
}
