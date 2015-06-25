package com.github.trpedersen.sort;

import com.github.trpedersen.util.Stopwatch;
import edu.princeton.cs.introcs.StdIn;
import edu.princeton.cs.introcs.StdOut;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class MergeSort extends Sort {

    private class BigArray<T extends Comparable> {
        public int length = 0;
        public Object[] a = new Object[30000000];
    }

    private static Comparable[] aux;

    public void merge(Comparable[] a, int lo, int mid, int hi) {
        int i = lo, j = mid + 1;

        for (int k = lo; k <= hi; k++) {
            aux[k] = a[k];
            //StdOut.print(aux[k]);
        }
        //StdOut.println();

        for (int k = lo; k <= hi; k++) {
            if (i > mid)
                a[k] = aux[j++];
            else if (j > hi)
                a[k] = aux[i++];
            else if (less(aux[j], aux[i]))
                a[k] = aux[j++];
            else
                a[k] = aux[i++];
            exchanges++;
//            if(exchanges % 1000000 == 0){
//                StdOut.println(exchanges);
//            }
        }


    }

    public void sort(Comparable[] a) {
        int N = a.length;
        aux = new Comparable[a.length];
        sort(a, 0, N-1);
    }

    public void sort(Comparable[] a, int size) {
        aux = new Comparable[size];
        sort(a, 0, size-1);
    }

    private void sort(Comparable[] a, int lo, int hi){
        if(hi <= lo)
            return;
        int mid = lo + (hi-lo)/2;
        sort(a, lo, mid);
        sort(a, mid+1, hi);
//        for(Comparable item: a){
//            StdOut.print(item + " ");
//        }
       // StdOut.printf("%d %d %d \n", lo, mid, hi);
        merge(a, lo, mid, hi);
//        for(Comparable item: a){
//            StdOut.print(item + " ");
//        }
//        StdOut.println();
    }


    public static void main(String[] args) throws IOException {

        Comparable a[] = null;
        boolean showArray = false;
        boolean sortInts = false;
        boolean useStdIn = true;
        int size = 0;
        String file;
//        try {
//            Thread.sleep(20000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
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
                file = opts.get(i+1);
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
            while( (token = reader.readLine()) != null ){
                tokens[tokenCount] = token;
                tokenCount++;
                if(tokenCount == 30000000){
                    break;
                }
            }


            a = tokens;
            size = tokenCount; // == 0 ? 0  : tokenCount;
        }

        StdOut.printf("data loaded, items: %d\n", size);

        MergeSort s = new MergeSort();

        Stopwatch sw = new Stopwatch();
        s.sort(a, size);
        double elapsed = sw.elapsedTime();
        assert s.isSorted(a);
        if (showArray) s.show(a, size);
        s.showStats(a, size);
        StdOut.printf("elapsed: %f\n", elapsed);


    }
}
