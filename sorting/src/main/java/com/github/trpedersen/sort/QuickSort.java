package com.github.trpedersen.sort;

import com.github.trpedersen.util.Stopwatch;
import edu.princeton.cs.introcs.StdIn;
import edu.princeton.cs.introcs.StdOut;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;


public class QuickSort extends Sort {


    public void sort(Comparable[] a) {
        int N = a.length;
        sort(a, 0, N - 1);
    }

    public void sort(Comparable[] a, int size) {
        int N = size;
        sort(a, 0, N - 1);
    }

    private void sort(Comparable[] a, int lo, int hi) {
        if (hi <= lo + 15){
            insertionSort(a, lo, hi);
            return;
        }
        int j = partition(a, lo, hi);
        sort(a, lo, j - 1);
        sort(a, j + 1, hi);
    }

    private void insertionSort(Comparable[] a, int lo, int hi){
        for (int i = lo; i <= hi; i++){
            for (int j = i; j > lo && less(a[j], a[j-1]); j--){
                exchange(a, j, j-1);
            }
        }
    }

    private int partition(Comparable[] a, int lo, int hi) {
        // partition into a[lo..j-1], a[j], a[j+1..hi] and return j
        int i = lo, j = hi + 1; // left and right scan indices
        Comparable v = a[lo];
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

    public static void main(String[] args) throws IOException {

        Comparable a[] = null;
        boolean showArray = false;
        boolean sortInts = false;
        boolean useStdIn = true;
        int size = 0;
        String file;

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

        if (sortInts) {
            StdOut.println("sorting ints");
            a = Arrays.stream(StdIn.readAllInts()).boxed().toArray(Integer[]::new);
            size = a.length;
        } else {
            StdOut.println("sorting strings");
            //a = StdIn.readAllStrings();
//            Scanner scanner = new Scanner(new java.io.BufferedInputStream(System.in), "UTF-8");
//            //ArrayList<String> tokens = new ArrayList<String>(30000000);
//            String[] tokens = new String[30000000];
//            int tokenCount = 0;
//            while(scanner.hasNext()){
//                String token = scanner.next();
//                //StdOut.println(token);
//                tokens[tokenCount] = token;
//                tokenCount++;
////                if(tokenCount % 10000 == 0){
////                    StdOut.println(tokenCount);
////                }
//                if(tokenCount == 30000000){
//                    break;
//                }
//            }

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
            size = tokenCount; // == 0 ? 0  : tokenCount;
        }

        StdOut.printf("data loaded, items: %d\n", size);

        QuickSort s = new QuickSort();

        Stopwatch sw = new Stopwatch();
        s.sort(a, size);
        double elapsed = sw.elapsedTime();
        assert s.isSorted(a);
        if (showArray) s.show(a, size);
        s.showStats(a, size);
        StdOut.printf("elapsed: %f\n", elapsed);


    }
}
