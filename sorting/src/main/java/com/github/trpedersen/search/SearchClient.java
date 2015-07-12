package com.github.trpedersen.search;

import edu.princeton.cs.introcs.StdOut;
import edu.princeton.cs.introcs.StdRandom;
import org.StructureGraphic.v1.DSutils;

/**
 * Created by timpe_000 on 7/07/2015.
 */
public class SearchClient {

    public static void main(String[] args) {
//        OrderedST<String, Integer> search = new BinarySearchTree<String, Integer>();
//
//        search.put("f", 1);
//        search.put("d", 2);
//        // search.put("c", 3);
//        search.put("g", 3);
//        search.put("a", 4);
//        search.put("b", 5);
//
////        search.deleteMin();
////        assert (search.get("a") == null);
////        assert (search.get("b").equals(2));
////        assert (search.get("c").equals(3));
////        search.deleteMax();
////        assert (search.get("d") == null);
//
//        assert (search.ceiling("c").equals("d"));
//        assert (search.floor("c").equals("b"));

        // assert(search.select(0).equals("a"));
        // assert(search.select(1).equals("b"));
        // assert(search.select(3).equals("f"));

//        String key = search.select(0);
//        key = search.select(1);
//        key = search.select(2);
//        key = search.select(3);
//        key = search.select(4);

//        int rank = search.rank("a");
//        rank = search.rank("b");
//        rank = search.rank("c");
//        rank = search.rank("d");
//        rank = search.rank("e");
//        rank = search.rank("z");

//        key = search.select(search.rank("z"));
//
//        search.keys().forEach(k ->
//                        StdOut.printf("%s: %d, ", k, search.get(k))
//        );

//        search.delete("c");
//        assert(search.ceiling("c")== null);
//        assert(search.floor("c").equals("b"));

//        assert(search.ceiling("b").equals("b"));
//        assert(search.floor("b").equals("b"));
//        search.delete("a");
//        search.delete("b");
//        assert(search.ceiling("b") == null);
//        assert(search.floor("b") == null);

//
//        search.keys().forEach(key ->
////                        StdOut.printf("%s: %d, ", key, search.get(key))
////        );
//        search.delete("b");
//        assert (search.get("b") == null);

//        search.keys().forEach(key ->
//                        StdOut.printf("%s: %d, ", key, search.get(key))
//        );

        RedBlackTree<Integer, Integer> searchInt; // = new SequentialST<Integer, Integer>();
////        //searchInt = new SequentialST<Integer, Integer>(); //
        searchInt = new RedBlackTree<Integer, Integer>();
//        // VisualAccumulator accum = new VisualAccumulator(11000, 200);
        for (int i = 0; i < 10; i++) {
            //searchInt = new SequentialST<Integer, Integer>();
            // for( int j = 0; j < i; j++) {
            searchInt.put(StdRandom.uniform(20), i);
            //searchInt.put(i, i);
            //  accum.addDataValue(searchInt.compares());
            if (i % 10 == 0) {
                StdOut.printf("key %d: N: %d, cmp: %d rl: %d, rr: %d, fc: %d\n", i, searchInt.size()
                        , searchInt.compares()
                        , searchInt.getRotateLefts()
                        , searchInt.getRotateRights()
                        , searchInt.getFlipColours());
            }
            //searchInt.reset();

        }

        StdOut.printf("N: %d, cmp: %d rl: %d, rr: %d, fc: %d\n", searchInt.size()
                , searchInt.compares()
                , searchInt.getRotateLefts()
                , searchInt.getRotateRights()
                , searchInt.getFlipColours());

        DSutils.show(searchInt.getRoot(), 20, 15);

        int count = 0;
//        for (int i = 0; i < 1000000; i++) {
//            int key = StdRandom.uniform(2000000);
//            Integer value = searchInt.get(key);
//            if(value == null) StdOut.printf("miss: %d\n", key);
//            if (count % 10000 == 0) {
//                StdOut.printf("key %d:, value: %d, N: %d, cmp: %d\n", key, value
//                        , searchInt.size()
//                        , searchInt.compares()
//                );
//            }
//            searchInt.reset();
//            count++;
//        }


//
//        StdOut.println();
//
//        searchInt.keys(560, 602).forEach(key ->
//                        StdOut.printf("%d: %d, ", key, searchInt.get(key))
//        );
//
//        StdOut.println();

//        for(int i = 0; i < 1000; i++){
//            searchInt.delete(i);
//        }

//        while (!searchInt.isEmpty()) {
//            int i = StdRandom.uniform(200);
//            try {
//                searchInt.delete(i);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            //StdOut.printf("i: %d, size: %d\n", i, searchInt.size());
////            searchInt.keys().forEach( key ->
////                            StdOut.printf("%d ", key)
////            );
//            // StdOut.println();
//        }
        StdOut.println();
        searchInt.keys().forEach(key -> {
                    StdOut.printf("%d ", key);
                    searchInt.delete(key);
                    DSutils.show(searchInt.getRoot(), 20, 15);
                }
        );

    }
}
