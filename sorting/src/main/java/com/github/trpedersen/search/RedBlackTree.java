package com.github.trpedersen.search;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by timpe_000 on 9/07/2015.
 */
public class RedBlackTree<Key extends Comparable<? super Key>, Value>
        implements OrderedST<Key, Value> {

    private Node root;
    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private boolean isRed(Node h) {
        return h != null && h.colour == RED;
    }

    private Node rotateLeft(Node h) {
        Node x = h.right;
        h.right = x.left;
        x.left = h;
        x.colour = h.colour;
        h.colour = RED;
        x.N = h.N;
        h.N = 1 + size(h.left) + size(h.right);
        rotateLefts++;
        return x;
    }

    private Node rotateRight(Node h) {
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        x.colour = h.colour;
        h.colour = RED;
        x.N = h.N;
        h.N = 1 + size(h.left) + size(h.right);
        rotateRights++;
        return x;
    }

    private void flipColours(Node h) {
        h.colour = RED;
        h.left.colour = BLACK;
        h.right.colour = BLACK;
        flipColours++;
    }

    private class Node {
        private Key key;
        private Value value;
        private Node left, right;
        private int N;              // # nodes in subtree rooted here
        private boolean colour;

        public Node(Key key, Value value, int N, boolean colour) {
            this.key = key;
            this.value = value;
            this.N = N;
            this.colour = colour;
        }
    }

    private int compares = 0;
    private int rotateLefts = 0;
    private int rotateRights = 0;
    private int flipColours = 0;

    @Override
    public Value get(Key key) {
        Node node = get(root, key);
        if (node != null) return node.value;
        else return null;
    }

    private Node get(Node node, Key key) {
        if (node == null) return null;
        int cmp = key.compareTo(node.key);
        compares++;
        if (cmp < 0) return get(node.left, key);
        else if (cmp > 0) return get(node.right, key);
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
        root.colour = BLACK;
    }

    private Node put(Node node, Key key, Value value) {
        // change key's value to value if key in subtree rooted at node
        // otherwise add a new node to subtree associating key with value.
        if (node == null) return new Node(key, value, 1, RED);
        int cmp = key.compareTo(node.key);
        compares++;
        if (cmp < 0) node.left = put(node.left, key, value);
        else if (cmp > 0) node.right = put(node.right, key, value);
        else node.value = value;

        if (isRed(node.right) && !isRed(node.left)) node = rotateLeft(node);
        if (isRed(node.left) && isRed(node.left.left)) node = rotateRight(node);
        if (isRed(node.left) && isRed(node.right)) flipColours(node);
        node.N = size(node.left) + size(node.right) + 1;
        return node;
    }

    @Override
    public int size() {
        return size(root);
    }

    private int size(Node node) {
        if (node == null) return 0;
        else return node.N;
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }

    @Override
    public void delete(Key key) {
        root = delete(root, key);
    }

    private Node delete(Node node, Key key) {
        if (node == null) return null;
        int cmp = key.compareTo(node.key);
        if (cmp < 0) node.left = delete(node.left, key);
        else if (cmp > 0) node.right = delete(node.right, key);
        else {
            // replace node with its successor
            if (node.right == null) return node.left;
            if (node.left == null) return node.right;
            Node t = node;
            node = min(t.right);
            node.right = deleteMin(t.right);
            node.left = t.left;
        }
        node.N = size(node.left) + size(node.right) + 1;
        return node;
    }

    @Override
    public Iterable<Key> keys() {
        return keys(min(), max());
    }

    @Override
    public Iterable<Key> keys(Key lo, Key hi) {
        Queue<Key> queue = new LinkedList<Key>();
        keys(root, queue, lo, hi);
        return queue;
    }

    private void keys(Node node, Queue<Key> queue, Key lo, Key hi) {
        if (node == null) return;
        int cmplo = lo.compareTo(node.key);
        int cmphi = hi.compareTo(node.key);
        if (cmplo < 0) keys(node.left, queue, lo, hi);
        if (cmplo <= 0 && cmphi >= 0) queue.add(node.key);
        if (cmphi > 0) keys(node.right, queue, lo, hi);
    }

    @Override
    public int compares() {
        return compares;
    }

    @Override
    public void reset() {
        rotateLefts = 0;
        rotateRights = 0;
        flipColours = 0;
        compares = 0;
    }

    @Override
    public Key min() {
        Node node = min(root);
        if (node != null) {
            return node.key;
        } else {
            return null;
        }
    }

    private Node min(Node node) {
        if (node.left == null) return node;
        else return min(node.left);
    }

    @Override
    public Key max() {
        return max(root);
    }

    private Key max(Node node) {
        if (node.right == null) return node.key;
        else return max(node.right);
    }

    @Override
    public Key floor(Key key) {
        Node node = floor(root, key);
        if (node == null) return null;
        return node.key;
    }

    private Node floor(Node node, Key key) {
        if (node == null) return null;
        int cmp = key.compareTo(node.key);
        if (cmp == 0) return node;
        if (cmp < 0) return floor(node.left, key);
        Node t = floor(node.right, key);
        if (t != null) return t;
        else return node;
    }

    @Override
    public Key ceiling(Key key) {
        Node node = ceiling(root, key);
        if (node == null) return null;
        return node.key;
    }

    private Node ceiling(Node node, Key key) {
        if (node == null) return null;
        int cmp = key.compareTo(node.key);
        if (cmp == 0) return node;
        if (cmp > 0) return ceiling(node.right, key);
        Node t = ceiling(node.left, key);
        if (t != null) return t;
        else return node;
    }

    @Override
    public int rank(Key key) {
        return rank(root, key);
    }

    private int rank(Node node, Key key) {
        if (node == null) return 0;
        int cmp = key.compareTo(node.key);
        if (cmp == 0) {
            return size(node.left);
        } else if (cmp < 0) {
            return rank(node.left, key);
        } else {
            return size(node.left) + 1 + rank(node.right, key);
        }
    }

    @Override
    public Key select(int k) {
        Node node = select(root, k);
        if (node != null) {
            return node.key;
        } else {
            return null;
        }
    }

    private Node select(Node node, int k) {
        if (node == null) return null;
        int t = size(node.left);
        if (t > k) return select(node.left, k);
        else if (t < k) return select(node.right, k - t - 1);
        else return node;
    }

    @Override
    public void deleteMin() {
        root = deleteMin(root);
    }

    private Node deleteMin(Node node) {
        if (node.left == null) return node.right;
        node.left = deleteMin(node.left);
        node.N = size(node.left) + size(node.right) + 1;
        return node;
    }

    @Override
    public void deleteMax() {
        root = deleteMax(root);
    }

    private Node deleteMax(Node node) {
        if (node.right == null) return node.left;
        node.right = deleteMax(node.right);
        node.N = size(node.left) + size(node.right) + 1;
        return node;
    }

    @Override
    public int size(Key lo, Key hi) {
        return 0;
    }

    public int getRotateLefts(){
        return rotateLefts;
    }

    public int getRotateRights(){
        return rotateRights;
    }

    public int getFlipColours(){
        return flipColours;
    }
}