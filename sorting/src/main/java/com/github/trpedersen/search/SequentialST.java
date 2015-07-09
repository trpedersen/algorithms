package com.github.trpedersen.search;

import java.util.Iterator;

/**
 * Created by timpe_000 on 7/07/2015.
 */
public class SequentialST<Key extends Comparable<? super Key>, Value>
        implements ST<Key, Value> {

    private Node first;
    public int compares = 0;

    private class Node {
        Key key;
        Value value;
        Node next;
        Node prev;
        public Node(Key key, Value value, Node next){
            this.key = key;
            this.value = value;
            this.next = next;
            if(next != null){
                next.prev = this;
            }
        }
    }

    public SequentialST(){

    }

    private Node getNode(Key key){
        for(Node node = first; node != null; node = node.next){
            compares++;
            if(key.equals(node.key)){
                return node;
            }
        }
        return null;
    }

    public Value get(Key key){
    // search for key, return associated value
        Node node = getNode(key);
        return node != null ? node.value : null;
    }

    public void put(Key key, Value value){
        if( value == null){
            delete(key);
            return;
        }
        Node node = getNode(key);
        if(node != null){
            node.value = value;
        } else {
            first = new Node(key, value, first);
        }
    }


    public void delete(Key key){
        Node node = getNode(key);
        if(node == null){
            return;
        }
        if(node.next != null){
            node.next.prev = node.prev;
        }
        if(node.prev != null){
            node.prev.next = node.next;
        }
        if(node == this.first){
            this.first = this.first.next;
        }
        node.next = null;
        node.prev = null;
    }

    public boolean contains(Key key){
        return get(key) != null;
    }

    public boolean isEmpty(){
        return size() == 0;
    }

    public int size(){
        int size = 0;
        for(Node node = first; node != null; node = node.next){
            size ++;
        }
        return size;
    }

    public Iterable<Key> keys(){
        final SequentialST<Key, Value> st = this;
        return new Iterable<Key>() {
            @Override
            public Iterator<Key> iterator() {
                return new Iterator<Key>() {
                    Node node = st.first;
                    @Override
                    public boolean hasNext() {
                        return node != null;
                    }

                    @Override
                    public Key next() {
                        Key key = null;
                        if(node != null){
                            key = node.key;
                            node = node.next;
                        };
                        return key;
                    }
                };
            }
        };
    }

    public int compares(){
        return this.compares;
    }

    public void reset(){
        this.compares = 0;
    }
}
