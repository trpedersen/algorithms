package com.github.trpedersen.search;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
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
        client.run();
    }

    OrderedST<String, Integer> st;
    VisualAccumulator accum;

    public void run() throws IOException {

        st = new BinarySearchTree<String, Integer>();
       // accum = new VisualAccumulator(20000, 50);

        getStringData();

        // Find a key with the highest frequency count.
//        String max = "";
//        st.put(max, 0);
//        for(String word:st.keys())
//        if(st.get(word) > st.get(max))
//        max = word;
//        StdOut.println(max + " " + st.get(max));
    }

    private void getStringData() throws IOException {

        if (inputFile != null) {
            inputStream = new FileInputStream(inputFile);
        } else {
            inputStream = System.in;
        }
        int count = 0;
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StreamTokenizer tokenizer = new StreamTokenizer(reader);
        while (tokenizer.nextToken() != StreamTokenizer.TT_EOF) {
            switch (tokenizer.ttype) {
                case StreamTokenizer.TT_NUMBER:
                    save(Double.toString(tokenizer.nval));
                    break;
                case StreamTokenizer.TT_WORD:
                    save(tokenizer.sval);
                    break;
            }
            count++;
            if(count % 1000 == 0){
                StdOut.printf("count: %d, compares: %d\n", count, st.compares());
            }
        }
    }

    private void save( String word) {
        if (word.length() < minlen) return;  // Ignore short keys.
        if (!st.contains(word)) st.put(word, 1);
        else st.put(word, st.get(word) + 1);
      //  accum.addDataValue(st.compares());
       // st.reset();
    }
}
