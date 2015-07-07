package com.github.trpedersen.search;

import com.github.trpedersen.util.VisualAccumulator;
import edu.princeton.cs.introcs.StdOut;
import edu.princeton.cs.introcs.StdRandom;

/**
 * Created by timpe_000 on 7/07/2015.
 */
public class SearchClient {

    public static void main(String[] args){
//        SequentialSearchST<String, String> search = new SequentialSearchST<String, String>();

//        search.put("a", "1");
//        search.put("b", "1");
//        search.put("c", "1");
//
//        assert(search.get("a").equals("1"));
//        assert(search.get("b").equals("1"));
//        assert(search.get("c").equals("1"));

//        search.keys().forEach( key ->
//                StdOut.println(key)
//        );
//
//        search.delete("b");
//        assert(search.get("b") == null);
//
//        search.keys().forEach( key ->
//                        StdOut.println(key)
//        );

        SequentialSearchST<Integer, Integer> searchInt; // = new SequentialSearchST<Integer, Integer>();
        searchInt = new SequentialSearchST<Integer, Integer>();
        VisualAccumulator accum = new VisualAccumulator(11000, 200);
        for(int i = 0; i < 10000; i++){
            //searchInt = new SequentialSearchST<Integer, Integer>();
           // for( int j = 0; j < i; j++) {
                searchInt.put(StdRandom.uniform(100), i);
                accum.addDataValue(searchInt.compares);
            StdOut.printf("i: %d, i^2/2: %d, compares: %d\n", i, (i*i)/2, searchInt.compares);
                searchInt.compares = 0;

          //  }
        }

//        searchInt.keys().forEach( key ->
//                        StdOut.printf("%d ", key)
//        );

//        StdOut.println();

//        for(int i = 0; i < 1000; i++){
//            searchInt.delete(i);
//        }

//        while(!searchInt.isEmpty()){
//            int i = StdRandom.uniform(1000);
//            searchInt.delete(i);
//            StdOut.printf("i: %d, size: %d\n", i, searchInt.size());
////            searchInt.keys().forEach( key ->
////                            StdOut.printf("%d ", key)
////            );
//           // StdOut.println();
//        }
//        StdOut.println();
//        searchInt.keys().forEach( key ->
//                        StdOut.printf("%d ", key)
//        );

    }
}
