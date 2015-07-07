package com.github.trpedersen.search;

import com.github.trpedersen.util.VisualAccumulator;
import edu.princeton.cs.introcs.StdIn;
import edu.princeton.cs.introcs.StdOut;

/**
 * Created by timpe_000 on 7/07/2015.
 */
public class FrequencyCounter {

    public static void main(String[] args) {

        int minlen = Integer.parseInt(args[0]);   // key-length cutoff

        SequentialSearchST<String, Integer> st = new SequentialSearchST<>();
        VisualAccumulator accum = new VisualAccumulator(8000, 15000);

        while (!StdIn.isEmpty()) {  // Build symbol table and count frequencies.
            String word = StdIn.readString();
            if(word.length() < minlen) continue;  // Ignore short keys.
            if(!st.contains(word)) st.put(word, 1);
            else st.put(word, st.get(word) + 1);
            accum.addDataValue(st.compares);
            st.compares = 0;
        }
        // Find a key with the highest frequency count.
        String max = "";
        st.put(max, 0);
        for(String word:st.keys())
        if(st.get(word) > st.get(max))
        max = word;
        StdOut.println(max + " " + st.get(max));
    }

}
