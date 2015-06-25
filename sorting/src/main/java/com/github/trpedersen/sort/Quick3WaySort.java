package com.github.trpedersen.sort;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.github.trpedersen.util.Stopwatch;
import edu.princeton.cs.introcs.StdIn;
import edu.princeton.cs.introcs.StdOut;
//import org.apache.commons.cli.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;


public class Quick3WaySort extends Sort {


    @Parameter(names = "-p", description = "print sorted array to stdout")
    private boolean print = false;

    @Parameter(names = "-i", description = "treat input as integers")
    private boolean asInt = false;

    @Parameter(names = "-s", description = "shuffle input before sorting")
    private boolean shuffle = false;

    @Parameter(names = "--help", help = true)
    private boolean help;


    public void sort(Comparable[] a) {
        int N = a.length;
        sort(a, 0, N - 1);
    }

    public void sort(Comparable[] a, int size) {
        int N = size;
        sort(a, 0, N - 1);
    }


    private void sort(Comparable[] a, int lo, int hi) {
//        if (hi <= lo + 15) {
//            insertionSort(a, lo, hi);
//            return;
//        }
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
        sort(a, lo, lt - 1);
        sort(a, gt + 1, hi);
    }

    private void insertionSort(Comparable[] a, int lo, int hi) {
        for (int i = lo; i <= hi; i++) {
            for (int j = i; j > lo && less(a[j], a[j - 1]); j--) {
                exchange(a, j, j - 1);
            }
        }
    }

    public static void main(String[] args) throws IOException {

        Comparable a[] = null;
        boolean showArray = false;
        boolean sortInts = false;
        boolean useStdIn = true;
        int size = 0;
        String file;

        Quick3WaySort s = new Quick3WaySort();
        new JCommander(s, args);

//        Options options = new Options();
//        options.addOption("p", false, "print sorted output");
//        options.addOption("i", false, "treat input as integer data");
//        options.addOption("s", false, "shuffle input");
//        CommandLineParser parser = new DefaultParser();
//        CommandLine cmd = null;
//        try {
//            cmd = parser.parse(options, args);
//        } catch (ParseException e) {
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
        if(s.shuffle){
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
        for (int i = 0; i < N; i++){
            int r = i + (int) (Math.random() * (N-i)); // between i and N-1;
            exchange(a, i, r);
        }
    }
}
