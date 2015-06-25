package com.github.trpedersen.sort;

import edu.princeton.cs.introcs.*;

public abstract class Sort {

    abstract void sort(Comparable[] a);

    protected long compares = 0;
    protected long exchanges = 0;

    protected boolean less(Comparable v, Comparable w){
        compares++;
        return v.compareTo(w) < 0;
    }

    public long getCompares() {
        return compares;
    }

    protected void exchange(Comparable[] a, int i, int j){
        exchanges++;
        Comparable t = a[i]; a[i] = a[j]; a[j] = t;
    }

    public long getExchanges() {
        return exchanges;
    }

    public void reset(){
        exchanges = 0;
        compares = 0;
    }

    public void show(Comparable[] a){
        for(Comparable i : a){
            StdOut.print(i + " ");
        }
        StdOut.println();

    }

    public void show(Comparable[] a, int size){
        for(int i = 0; i < size; i++){
            StdOut.print(a[i] + " ");
        }
        StdOut.println();

    }

    public void dump(Comparable[] a, int size){
        for(int i = 0; i < size; i++){
            StdOut.println(a[i]);
        }
        StdOut.println();

    }


    public void showStats(Comparable[] a){
        StdOut.printf("items: %d, compares: %d, exchanges: %d\n", a.length, compares, exchanges);
    }

    public void showStats(Comparable[] a, int size){
        StdOut.printf("items: %d, compares: %d, exchanges: %d\n", size, compares, exchanges);
    }


    public boolean isSorted(Comparable[] a){
        for(int i = 1; i < a.length; i++){
            if(less(a[i], a[i-1])) return false;
        }
        return true;
    }




}
