package com.github.trpedersen.search;

import com.github.trpedersen.util.Stopwatch;
import edu.princeton.cs.introcs.StdIn;
import edu.princeton.cs.introcs.StdOut;
import edu.princeton.cs.introcs.StdRandom;
import org.StructureGraphic.v1.DSutils;

/**
 * Created by timpe_000 on 7/07/2015.
 */
public class SearchClient {

    public static class StringKey implements Comparable<StringKey>{

        private String key;
        private String value;

        public StringKey(String key, String value){
            this.key = key;
            this.value = value;
        }

        @Override
        public int compareTo(StringKey other) {
            return this.key.compareTo(other.key);
        }
    }

    public static class IntegerKey implements Comparable<IntegerKey>{

        private Integer key;
        private Integer value;

        public IntegerKey(Integer key, Integer value){
            this.key = key;
            this.value = value;
        }

        @Override
        public int compareTo(IntegerKey other) {
            return this.key.compareTo(other.key);
        }
    }

    static final int trials = 1000000;

    public static void main(String[] args) {


        //DSutils.show(searchInt.getRoot(), 20, 15);


        for(int j = 0; j < 10; j++) {

            Stopwatch sw = new Stopwatch();
            RedBlackTree<String, Integer> s1 = new RedBlackTree<>();

            for (int i = 0; i < trials; i++) {
                // int key = StdRandom.uniform(2000000);
                String key = String.format("d", i);
                s1.put(key, i);
                Integer value = s1.get(key);
                if (value != i) {
                    StdOut.printf("boom: value: %d, i: %d", value, i);
                }
            }
            double elapsed = sw.elapsedTime();
            StdOut.printf("s1 done, size: %d, height: %d, time: %f\n", s1.size(), s1.getHeight(), elapsed);

            sw = new Stopwatch();
            RedBlackTree<IntegerKey, Integer> searchInt = new RedBlackTree<>();

            for (int i = 0; i < trials; i++) {
                // int key = StdRandom.uniform(2000000);
                IntegerKey key = new IntegerKey(i, i);
                searchInt.put(key, i);
                Integer value = searchInt.get(key);
                if (value != i) {
                    StdOut.printf("boom: value: %d, i: %d", value, i);
                }
            }
            elapsed = sw.elapsedTime();
            StdOut.printf("ints done, size: %d, height: %d, time: %f\n", searchInt.size(), searchInt.getHeight(), elapsed);


            sw = new Stopwatch();
            RedBlackTree<StringKey, String> strings = new RedBlackTree<>();

            for (Integer i = 0; i < trials; i++) {
                // int key = StdRandom.uniform(2000000);
                String s = i.toString();
                StringKey key = new StringKey(s, s);
                strings.put(key, s);
                String value = strings.get(key);
                if (!value.equals(s)) {
                    StdOut.printf("boom: value: %s, i: %s", value, s);
                }
            }
            elapsed = sw.elapsedTime();
            StdOut.printf("strings done, size: %d, height: %d, time: %f\n", strings.size(), strings.getHeight(), elapsed);
        }
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

//        searchInt.keys().forEach(key -> {
//                    StdOut.printf("%d ", key);
//                    searchInt.delete(key);
//                    DSutils.show(searchInt.getRoot(), 20, 15);
//                }
//        );

//        while(!searchInt.isEmpty()){
//            int key = StdIn.readInt();
//            searchInt.delete(key);
//            DSutils.show(searchInt.getRoot(), 20, 15);
//        }

//        RedBlackTree<Character, Character> st2 = new RedBlackTree<>();
//
//        for(Character c: "flower".toCharArray()){
//            st2.put(c, c);
//        }
//
//        DSutils.show(st2.getRoot(), 20, 15);
//
//        while(!st2.isEmpty()){
//            Character key = StdIn.readChar();
//            st2.delete(key);
//            DSutils.show(st2.getRoot(), 20, 15);
//        }
    }
}
