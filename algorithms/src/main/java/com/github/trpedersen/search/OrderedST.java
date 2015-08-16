package com.github.trpedersen.search;

/**
 * Created by timpe_000 on 9/07/2015.
 */
public interface OrderedST <Key extends Comparable<? super Key>, Value> {
    public Value get(Key key);

    public boolean contains(Key key);

    public void put(Key key, Value value);

    public int size();

    public boolean isEmpty();

    public void delete(Key key);

    public Iterable<Key> keys();

    public Iterable<Key> keys(Key lo, Key hi);

    public int compares();

    public void reset();

    public Key min();

    public Key max();

    public Key floor(Key key);

    public Key ceiling(Key key);

    public int rank(Key key);

    public Key select(int k);

    public void deleteMin();

    public void deleteMax();

    int size(Key lo, Key hi);


}
