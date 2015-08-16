package com.github.trpedersen.util;

/**
 * Created by timpe_000 on 19/06/2015.
 */
public class Stopwatch {

    private final long start;

    /**
     * Create a stopwatch object.
     */
    public Stopwatch() {
        start = System.nanoTime();
    }


    /**
     * Return elapsed time (in milliseconds) since this object was created.
     */
    public double elapsedTime() {
        long now = System.nanoTime();
        return (now - start) / 1000000.0;
    }

}