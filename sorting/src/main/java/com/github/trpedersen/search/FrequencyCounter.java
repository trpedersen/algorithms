package com.github.trpedersen.search;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.github.trpedersen.util.Stopwatch;
import com.github.trpedersen.util.VisualAccumulator;
import edu.princeton.cs.introcs.StdIn;
import edu.princeton.cs.introcs.StdOut;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by timpe_000 on 7/07/2015.
 */
public class FrequencyCounter {

    @Parameter(names = "-f", description = "read from inputFile")
    private String inputFile;

    @Parameter(names = "-l", description = "word length")
    int minlen = 0;   // word-length cutoff

    InputStream inputStream = null;

    public static void main(String[] args) throws IOException {

        FrequencyCounter client = new FrequencyCounter();
        new JCommander(client, args);
        int trials = 1;
//        VisualAccumulator accum = new VisualAccumulator(trials, 3);
        for(int i = 0; i< trials; i++) {
            client.run(null);
            StdOut.println("-------------");
        }
    }


    public void run(VisualAccumulator accum) throws IOException {

        OrderedST<String, Integer> st1 = new RedBlackTree<String, Integer>();

        Stopwatch sw = new Stopwatch();
        int wordCount = getStringData(st1);
        double elapsed = sw.elapsedTime();
        double e1 = elapsed;
        StdOut.printf("st1 N: %d, compares: %d, time: %f\n", st1.size(), st1.compares(), elapsed);
        st1.reset();

        OrderedST<String, Integer> st2 = new RedBlackTree<String, Integer>();
        // accum = new VisualAccumulator(20000, 50);

//        sw = new Stopwatch();
//        getStringData(st2);
//        elapsed = sw.elapsedTime();
//        double e2 = elapsed;
//
//        StdOut.printf("st2 N: %d, compares: %d, time: %f\n", st2.size(), st2.compares(), elapsed);
//        st2.reset();

       // accum.addDataValue(e1/e2);

//         Find a key with the highest frequency count.

//        String max = "";
//        sw = new Stopwatch();
//        st1.put(max, 0);
//        for(String word:st1.keys()) {
//            if (st1.get(word) > st1.get(max)) {
//                max = word;
//            }
//        }
//        StdOut.println(max + " " + st1.get(max));
//        elapsed = sw.elapsedTime();
//        StdOut.printf("st1 N: %d, compares: %d, time: %f\n", st1.size(), st1.compares(), elapsed);


//        max = "";
//        sw = new Stopwatch();
//        st2.put(max, 0);
//        for(String word:st2.keys()) {
//            if (st2.get(word) > st2.get(max)) {
//                max = word;
//            }
//        }
//        StdOut.println(max + " " + st2.get(max));
//        elapsed = sw.elapsedTime();
//        StdOut.printf("st2 N: %d, compares: %d, time: %f\n", st2.size(), st2.compares(), elapsed);
//
//        StdOut.printf("st1/st2: %f\n", (e1/e2));


//        int count = 0;
        sw = new Stopwatch();
        for(String word: st1.keys()){
            st2.put(word, st1.get(word));
//            count++;
//            if(count % 1000 == 0){
//                StdOut.printf("count: %d, compares: %d\n", count, st2.compares());
//            }
        }
        elapsed = sw.elapsedTime();
        StdOut.printf("st2 N: %d, compares: %d, time: %f\n", st2.size(), st2.compares(), elapsed);

    }

    private int getStringData(OrderedST<String, Integer> st) throws IOException {

        if (inputFile != null) {
            inputStream = new FileInputStream(inputFile);
        } else {
            inputStream = System.in;
        }
        int count = 0;
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StreamTokenizer tokenizer = new StreamTokenizer(reader);
        String token = "";
        while (tokenizer.nextToken() != StreamTokenizer.TT_EOF) {

            switch (tokenizer.ttype) {
                case StreamTokenizer.TT_NUMBER:
                    token = Double.toString(tokenizer.nval);
                    break;
                case StreamTokenizer.TT_WORD:
                    token = tokenizer.sval;
                    break;
            }
            if (token.length() >= minlen) { // Ignore short keys.
                if (!st.contains(token)) st.put(token, 1);
                else st.put(token, st.get(token) + 1);
                count++;
                if (count % 1000000 == 0) {
                    StdOut.printf("count: %d, compares: %d\n", count, st.compares());
                }
            }
        }
        StdOut.printf("count: %d, compares: %d\n", count, st.compares());
        return count;
    }

    private void save( OrderedST<String, Integer> st, String word) {
        if (word.length() < minlen) return;  // Ignore short keys.
        if (!st.contains(word)) st.put(word, 1);
        else st.put(word, st.get(word) + 1);
      //  accum.addDataValue(st.compares());
       // st.reset();
    }
}
