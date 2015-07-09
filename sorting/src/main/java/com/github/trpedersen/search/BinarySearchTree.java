package com.github.trpedersen.search;

/**
 * Created by timpe_000 on 9/07/2015.
 */
public class BinarySearchTree <Key extends Comparable<? super Key>, Value>
        implements OrderedST<Key, Value> {

    private Node root;

    private class Node {
        private Key key;
        private Value value;
        private Node left, right;
        private int N;              // # nodes in subtree rooted here

        public Node( Key key, Value value, int N){
            this.key = key;
            this.value = value;
            this.N = N;
        }
    }

    private int compares = 0;

    @Override
    public Value get(Key key) {
        return get(root, key);
    }

    private Value get(Node node, Key key){
        if( node == null) return null;
        int cmp = key.compareTo(node.key);
        compares++;
        if (cmp < 0) return get(node.left, key);
        else if (cmp > 0) return get(node.right, key);
        else return node.value;
    }


    private Node getNode(Key key) {
        return getNode(root, key);
    }

    private Node getNode(Node node, Key key){
        if( node == null) return null;
        int cmp = key.compareTo(node.key);
        if (cmp < 0) return getNode(node.left, key);
        else if (cmp > 0) return getNode(node.right, key);
        else return node;
    }

    @Override
    public boolean contains(Key key) {
        return get(key) != null;
    }

    @Override
    public void put(Key key, Value value) {
        // search for key, update value if found, grow table if new
        root = put(root, key, value);
    }

    private Node put(Node node, Key key, Value value){
        // change key's value to value if key in subtree rooted at node
        // otherwise add a new node to subtree associating key with value.
        if( node == null) return new Node(key, value, 1);
        int cmp = key.compareTo(node.key);
        if (cmp < 0) node.left = put(node.left, key, value);
        else if (cmp > 0) node.right = put(node.right, key, value);
        else node.value = value;
        node.N = size(node.left) + size(node.right) + 1;
        return node;
    }

    @Override
    public int size() {
        return size(root);
    }

    private int size(Node node) {
        if(node == null) return 0;
        else return node.N;
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }

    @Override
    public void delete(Key key) {
        // todo
        Node node = getNode(key);
        if(node == null){
            return;
        }
        return;
    }

    @Override
    public Iterable<Key> keys() {
        return null;
    }

    @Override
    public Iterable<Key> keys(Key lo, Key hi) {
        return null;
    }

    @Override
    public int compares() {
        return compares;
    }

    @Override
    public void reset() {
        compares = 0;
    }

    @Override
    public Key min() {
        return null;
    }

    @Override
    public Key max() {
        return null;
    }

    @Override
    public Key floor(Key key) {
        return null;
    }

    @Override
    public Key ceiling(Key key) {
        return null;
    }

    @Override
    public int rank(Key key) {
        //return rank(root, key);
        return 0;
    }

//    private int rank(Node node, Key key){
//        if(node == null){
//            return 0;
//        }
//        if(key.compareTo(node.key) > 0){
//            return node.N;
//        }
//        int rank = 0;
//
//        if(node.right != null){
//            int rightCmp = key.compareTo(node.right.key);
//            if(rightCmp > 0) {
//                rank = node.right.N + node.left.N;
//            } else if (rightCmp == 0){
//                rank = node.N - 1;
//            }
//        }
//        if(node.left != null && key.compareTo(node.left.key) > 0){
//            rank += node.left.N;
//        }
//        return rank(node.left, key);
//    }

    @Override
    public Key select(int k) {
        return null;
    }

    @Override
    public void deleteMin() {

    }

    @Override
    public void deleteMax() {

    }

    @Override
    public int size(Key lo, Key hi) {
        return 0;
    }
}
