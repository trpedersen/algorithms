package com.github.trpedersen.sort;

import edu.princeton.cs.introcs.StdOut;

public abstract class Sort<Key extends Comparable<Key>> {

    abstract void sort(Key[] a);

    protected long compares = 0;
    protected long exchanges = 0;

    protected boolean less(Key v, Key w){
        compares++;
        return v.compareTo((Key) w) < 0;
    }

    public long getCompares() {
        return compares;
    }

    protected void exchange(Key[] a, int i, int j){
        exchanges++;
        if(a[i] == null || a[j] == null){
            return;
        }
        Key t = a[i]; a[i] = a[j]; a[j] = t;
    }

    public long getExchanges() {
        return exchanges;
    }

    private void shuffle(Key[] a, int N) {
        for (int i = 0; i < N; i++) {
            int r = i + (int) (Math.random() * (N - i)); // between i and N-1;
            exchange(a, i, r);
        }
    }

    public void reset(){
        exchanges = 0;
        compares = 0;
    }

    public void showStats(Key[] a){
        StdOut.printf("items: %d, compares: %d, exchanges: %d\n", a.length, compares, exchanges);
    }

    public void showStats(Key[] a, int size){
        StdOut.printf("items: %d, compares: %d, exchanges: %d\n", size, compares, exchanges);
    }

    public boolean isSorted(Key[] a){
        for(int i = 1; i < a.length; i++){
            if(less(a[i], a[i-1])) {
                return false;
            }
        }
        return true;
    }

}
