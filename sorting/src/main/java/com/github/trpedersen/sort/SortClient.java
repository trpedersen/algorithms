package com.github.trpedersen.sort;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.github.trpedersen.util.Stopwatch;
import edu.princeton.cs.introcs.StdOut;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by timpe_000 on 28/06/2015.
 */
public class SortClient {

    @Parameter(names = "-a", description = "algorithm: insertion, selection, merge, quick, shell, heap")
    private List<String> algorithms = new ArrayList<String>();

    @Parameter(names = "-p", description = "print sorted array to stdout")
    private boolean print = false;

    @Parameter(names = "-i", description = "treat input as integers")
    private boolean asInt = false;

    @Parameter(names = "-s", description = "shuffle input before sorting")
    private boolean shuffle = false;

    @Parameter(names = "-f", description = "read from inputFile")
    private String inputFile;

    @Parameter(names = "-o", description = "write to output file")
    private String outputFile;

    @Parameter(names = "--help", help = true)
    private boolean help;

    InputStream inputStream = null;

    Comparable[] data = null;

    private String[] getStringData(int initialSize) throws IOException {
        String[] result = new String[0];

        if (inputFile != null) {
            inputStream = new FileInputStream(inputFile);
        } else {
            inputStream = System.in;
        }
        List<String> list = new ArrayList<String>(initialSize);

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StreamTokenizer tokenizer = new StreamTokenizer(reader);
        while (tokenizer.nextToken() != StreamTokenizer.TT_EOF) {
            switch (tokenizer.ttype) {
                case StreamTokenizer.TT_NUMBER:
                    list.add(Double.toString(tokenizer.nval));
                    break;
                case StreamTokenizer.TT_WORD:
                    list.add(tokenizer.sval);
                    break;
            }
        }
        return list.toArray(result);
    }

    private Integer[] getIntegerData(int initialSize) throws IOException {
        Integer[] result = new Integer[0];

        if (inputFile != null) {
            inputStream = new FileInputStream(inputFile);
        } else {
            inputStream = System.in;
        }
        List<Integer> list = new ArrayList<Integer>(initialSize);

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StreamTokenizer tokenizer = new StreamTokenizer(reader);
        while (tokenizer.nextToken() != StreamTokenizer.TT_EOF) {
            switch (tokenizer.ttype) {
                case StreamTokenizer.TT_NUMBER:
                    list.add((int) tokenizer.nval);
                    break;
                default:
                    break;
            }
        }
        return list.toArray(result);
    }

//    private Comparable[] getData2(int initialSize) throws IOException {
//
//        Comparable[] a = new Comparable[0];
//        String token;
//        Writer fw = null;
//        BufferedWriter writer = null;
//
//        if (inputFile != null) {
//            inputStream = new FileInputStream(inputFile);
//        } else {
//            inputStream = System.in;
//        }
//
//        if(outputFile != null){
//            fw = new FileWriter(outputFile);
//            writer = new BufferedWriter(fw);
//        }
//        List<Comparable> list = new ArrayList<Comparable>();
//
//        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
//        StandardTokenizer tokenizer = new StandardTokenizer();
//        tokenizer.setReader(reader);
//        tokenizer.reset();
//
//        while(tokenizer.incrementToken()){
//            token = ((CharTermAttribute)tokenizer.getAttribute(CharTermAttribute.class)).toString();
//            if(writer != null){
//                writer.write(token);
//                writer.write("\n");
//            }
//            list.add(token);
//        }
//        tokenizer.end();
//        tokenizer.close();
//
//        a = list.toArray(a);
//        return a;
//    }

    public void run() {

        if (algorithms.size() == 0) {
            StdOut.println("Must specify at least one algorithm");
            return;
        }

        if (asInt) {
            sortIntegerFile();
        } else {
            sortStringFile();
        }

    }

    private void sortStringFile(){
        try {
            String[] data = null;
            String[] a = null;
            StdOut.printf("loading data...");
            Stopwatch sw = new Stopwatch();
            data = getStringData(2500000);
            double elapsed = sw.elapsedTime();
            StdOut.printf("done, items: %d, time: %f\n", data.length, elapsed);

            for (String algorithm : algorithms) {
                Sort<String> sort = null;
                a = new String[data.length];
                System.arraycopy(data, 0, a, 0, data.length);
                sw = new Stopwatch();
                switch (algorithm.toLowerCase()) {
                    case "quick3waysort":
                        StdOut.printf("quick 3 way sort: sorting...");
                        sort = new Quick3WaySort<String>();
                        sort.sort(a);
                        break;
                    case "quicksort":
                        StdOut.printf("quick sort: sorting...");
                        sort = new QuickSort<String>();
                        sort.sort(a);
                        break;
                    case "quicksortforkjoin":
                        StdOut.printf("quick sort: sorting...");
                        sort = new Quick3WaySortForkJoin<String>();
                        sort.sort(a);
                        break;
                    case "mergesort":
                        StdOut.printf("merge sort: sorting...");
                        sort = new MergeSort<String>();
                        sort.sort(a);
                        break;
                    case "mergebusort":
                        StdOut.printf("mergeBU sort: sorting...");
                        sort = new MergeBUSort<String>();
                        sort.sort(a);
                        break;
                    case "heapsort":
                        StdOut.printf("heap sort: sorting...");
                        sort = new HeapSort<String>();
                        sort.sort(a);
                        break;
                    case "shellsort":
                        StdOut.printf("shell sort: sorting...");
                        sort = new ShellSort<String>();
                        sort.sort(a);
                        break;
                    case "selectionsort":
                        StdOut.printf("selection sort: sorting...");
                        sort = new SelectionSort<String>();
                        sort.sort(a);
                        break;
                    case "insertionsort":
                        StdOut.printf("insertion sort: sorting...");
                        sort = new InsertionSort<String>();
                        sort.sort(a);
                        break;
                    default:
                        StdOut.println("Unknown algorithm: " + algorithm);
                        break;
                }
//
                if (sort != null) {
                    elapsed = sw.elapsedTime();
                    StdOut.printf("done, items: %d, time: %f, compares: %d, exchanges: %d\n"
                            , a.length, elapsed, sort.compares, sort.exchanges);
                    if (print) {
                        dump(a);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }

    }

    private void sortIntegerFile(){
        try {
            Integer[] data = null;
            Integer[] a = null;
            StdOut.printf("loading data...");
            Stopwatch sw = new Stopwatch();
            data = getIntegerData(2500000);
            double elapsed = sw.elapsedTime();
            StdOut.printf("done, items: %d, time: %f\n", data.length, elapsed);

            for (String algorithm : algorithms) {
                Sort<Integer> sort = null;
                a = new Integer[data.length];
                System.arraycopy(data, 0, a, 0, data.length);
                sw = new Stopwatch();
                switch (algorithm.toLowerCase()) {
                    case "quick3waysort":
                        StdOut.printf("quick 3 way sort: sorting...");
                        sort = new Quick3WaySort<Integer>();
                        sort.sort(a);
                        break;
                    case "quicksort":
                        StdOut.printf("quick sort: sorting...");
                        sort = new QuickSort<Integer>();
                        sort.sort(a);
                        break;
                    case "quicksortforkjoin":
                        StdOut.printf("quick sort fork/join: sorting...");
                        sort = new Quick3WaySortForkJoin<Integer>();
                        sort.sort(a);
                        break;
                    case "mergesort":
                        StdOut.printf("merge sort: sorting...");
                        sort = new MergeSort<Integer>();
                        sort.sort(a);
                        break;
                    case "mergebusort":
                        StdOut.printf("mergeBU sort: sorting...");
                        sort = new MergeBUSort<Integer>();
                        sort.sort(a);
                        break;
                    case "heapsort":
                        StdOut.printf("heap sort: sorting...");
                        sort = new HeapSort<Integer>();
                        sort.sort(a);
                        break;
                    case "shellsort":
                        StdOut.printf("shell sort: sorting...");
                        sort = new ShellSort<Integer>();
                        sort.sort(a);
                        break;
                    case "selectionsort":
                        StdOut.printf("selection sort: sorting...");
                        sort = new SelectionSort<Integer>();
                        sort.sort(a);
                        break;
                    case "insertionsort":
                        StdOut.printf("insertion sort: sorting...");
                        sort = new InsertionSort<Integer>();
                        sort.sort(a);
                        break;
                    default:
                        StdOut.println("Unknown algorithm: " + algorithm);
                        break;
                }
//
                if (sort != null) {
                    elapsed = sw.elapsedTime();
                    StdOut.printf("done, items: %d, time: %f, compares: %d, exchanges: %d\n"
                            , a.length, elapsed, sort.compares, sort.exchanges);
                    if (print) {
                        dump(a);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }

    }


    public void show(Comparable[] a) {
        for (Comparable i : a) {
            StdOut.print(i + " ");
        }
        StdOut.println();

    }

    public void show(Comparable[] a, int size) {
        for (int i = 0; i < size; i++) {
            StdOut.print(a[i] + " ");
        }
        StdOut.println();

    }

    public void dump(Comparable[] a) {
        dump(a, a.length);
    }

    public void dump(Comparable[] a, int size) {
        for (int i = 0; i < size; i++) {
            StdOut.println(a[i]);
        }
        StdOut.println();
    }

    public SortClient() {
    }

    public static void main(String[] args) throws IOException {

        SortClient client = new SortClient();
        new JCommander(client, args);
        client.run();

    }
}
