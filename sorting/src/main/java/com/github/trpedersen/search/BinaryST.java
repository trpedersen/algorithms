package com.github.trpedersen.search;

import java.util.Iterator;

/**
 * Created by timpe_000 on 7/07/2015.
 */
public class BinaryST<Key extends Comparable<? super Key>, Value>
        implements OrderedST<Key, Value> {

    private Key[] keys;
    private Value[] values;
    private int N;
    private int compares;

    public BinaryST(int capacity) {
        keys = (Key[]) new Comparable[capacity];
        values = (Value[]) new Object[capacity];
    }


    public Value get(Key key) {
        // search for key, return associated value
        if (isEmpty()) return null;
        int i = rank(key);
        if (i < N && keys[i].compareTo(key) == 0) return values[i];
        else return null;
    }

    public int rank(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("null key");
        }
        return rank(key, 0, N - 1);
    }

//    private int rank(Key key, int lo, int hi){
//        this.compares++;
//        if(hi < lo) return lo;
//        int mid = lo + (hi-lo)/2;
//        int cmp = key.compareTo(keys[mid]);
//        if( cmp < 0){
//            return rank(key, lo, mid-1);
//        } else if( cmp > 0){
//            return rank( key, mid+1, hi);
//        } else {
//            return mid;
//        }
//    }

    private int rank(Key key, int lo, int hi) {
        while (lo <= hi) {
            compares++;
            int mid = lo + (hi - lo) / 2;
            int cmp = key.compareTo(keys[mid]);
            if (cmp < 0) {
                hi = mid - 1;
            } else if (cmp > 0) {
                lo = mid + 1;
            } else {
                return mid;
            }
        }
        return lo;
    }

    public void put(Key key, Value value) {
        if (key == null) {
            throw new IllegalArgumentException("null key");
        }
        if (value == null) {
            delete(key);
            return;
        }
        int i = rank(key);
        if (i < N && keys[i].compareTo(key) == 0) {
            values[i] = value;
            return;
        }
        for (int j = N; j > i; j--) {
            keys[j] = keys[j - 1];
            values[j] = values[j - 1];
        }
        keys[i] = key;
        values[i] = value;
        N++;
    }


    public void delete(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("null key");
        }
        if (!contains(key)) {
            return;
        }
        int i = rank(key);
        for (int j = i; j < N; j++) {
            keys[j] = keys[j + 1];
            values[j] = values[j + 1];
        }
        keys[N - 1] = null;
        values[N - 1] = null;
        N--;

    }

    public boolean contains(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("null key");
        }
        return get(key) != null;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return N;
    }

    public Iterable<Key> keys() {
        final BinaryST<Key, Value> st = this;
        return new Iterable<Key>() {
            @Override
            public Iterator<Key> iterator() {
                return new Iterator<Key>() {
                    int i = 0;

                    @Override
                    public boolean hasNext() {
                        return i < st.N;
                    }

                    @Override
                    public Key next() {
                        Key key = null;
                        if (N > 0) {
                            key = st.keys[i];
                            i++;
                        }
                        return key;
                    }
                };
            }
        };
    }

    @Override
    public Iterable<Key> keys(Key lo, Key hi) {
        final BinaryST<Key, Value> st = this;
        return new Iterable<Key>() {
            @Override
            public Iterator<Key> iterator() {
                return new Iterator<Key>() {
                    int i = rank(lo);
                    int j = rank(hi);
                    boolean containsHi = contains(hi);

                    @Override
                    public boolean hasNext() {
                        return i < (containsHi ? j+1: j);
                    }

                    @Override
                    public Key next() {
                        Key key = null;
                        if (N > 0) {
                            key = st.keys[i];
                            i++;
                        }
                        ;
                        return key;
                    }
                };
            }
        };
    }

    public int compares() {
        return this.compares;
    }

    public void reset() {
        this.compares = 0;
    }

    @Override
    public Key min() {
        if (N > 0)
            return keys[0];
        else
            return null;
    }

    @Override
    public Key max() {
        if (N > 0)
            return keys[N - 1];
        else
            return null;
    }

    @Override
    public Key floor(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("null key");
        }
        if (N == 0) {
            return null;
        }
        int i = rank(key);
        if (i == N) {
            return keys[N-1];
        } else {
            if (key.compareTo(keys[i]) == 0) {
                return key;
            } else {
                // greater, so return key at that rank-1;
                return keys[i - 1];
            }
        }
    }

    @Override
    public Key ceiling(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("null key");
        }
        int i = rank(key);
        if (i == N) {
            return null;
        } else {
            return keys[i];
        }
    }


    @Override
    public Key select(int k) {
        if (k < N) {
            return keys[k];
        } else
            return null;
    }

    @Override
    public void deleteMin() {
        if (N > 0)
            delete(select(0));
    }

    @Override
    public void deleteMax() {
        if (N > 0)
            delete(select(N - 1));
    }

    @Override
    public int size(Key lo, Key hi) {
        if (N == 0) {
            return 0;
        }
        int i = rank(lo);
        int j = rank(hi);
        int size = 1;
        while (i < j) {
            size++;
            i++;
        }
        return size;
    }
}
