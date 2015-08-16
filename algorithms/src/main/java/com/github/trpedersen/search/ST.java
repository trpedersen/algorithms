package com.github.trpedersen.search;

/**
 * Created by timpe_000 on 9/07/2015.
 */
public interface ST<Key extends Comparable<? super Key>, Value> {

    public Value get(Key key);

    public boolean contains(Key key);

    public void put(Key key, Value value);

    public int size();

    public boolean isEmpty();

    public void delete(Key key);

    public Iterable<Key> keys();

    public int compares();

    public void reset();
}
