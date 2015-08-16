package com.github.trpedersen.search;

import java.awt.*;
import java.util.LinkedList;
import java.util.Queue;
import org.StructureGraphic.v1.*;

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
//        h.colour = RED;
//        h.left.colour = BLACK;
//        h.right.colour = BLACK;
        h.colour = !h.colour;
        h.left.colour = !h.left.colour;
        h.right.colour = !h.right.colour;
        flipColours++;
    }

    private class Node implements DSTreeNode{
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

        @Override
        public DSTreeNode[] DSgetChildren() {
            return new DSTreeNode[]{left, right};
        }

        @Override
        public Object DSgetValue() {
            return key.toString();
        }

        @Override
        public Color DSgetColor() {
            if(colour == RED){
                return Color.RED;
            } else {
                return Color.BLACK;
            }
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
        if(!contains(key)){
            return;
        }
        // if both children red, set root black
        if(!isRed(root.left) && !isRed(root.right)){
            root.colour = RED;
        }
        root = delete(root, key);
        if(!isEmpty()){
            root.colour = BLACK;
        }
    }

    private Node delete(Node node, Key key) {
        if (node == null) return null;
        if(key.compareTo(node.key) < 0) {
            try {
                if (!isRed(node.left) && !isRed(node.left.left)) {
                    node = moveRedLeft(node);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            node.left = delete(node.left, key);
        } else {
            if(isRed(node.left)){
                node = rotateRight(node);
            }
            if(key.compareTo(node.key) == 0 && (node.right == null)){
                return null;
            }
            try {
                if(!isRed(node.right) && !isRed(node.right.left)){
                    node = moveRedRight(node);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(key.compareTo(node.key) == 0){
                Node x = min(node.right);
                node.key = x.key;
                node.value = x.value;
                node.right = deleteMin(node.right);
            } else {
                node.right = delete(node.right, key);
            }
        }
        return balance(node);
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
        if(isEmpty()) return null;
        Node node = min(root);
        if (node != null) {
            return node.key;
        } else {
            return null;
        }
    }

    private Node min(Node node) {
        if(node == null) return null;
        if (node.left == null) return node;
        else return min(node.left);
    }

    @Override
    public Key max() {
        if(root == null) return null;
        return max(root).key;
    }

    private Node max(Node node) {
        if(node == null) return null;
        if (node.right == null) return node;
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
        if(k < 0 || k >= size()) return null;
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
        if(isEmpty()){
            return;
        }
        // if both children of root are black, set root to red
        if( !isRed(root.left) && !isRed(root.right)){
            root.colour = RED;
        }
        root = deleteMin(root);
        if(!isEmpty()) root.colour = BLACK;
    }

    private Node deleteMin(Node node) {

        if(node.left == null) return null;
        if(!isRed(node.left) && !isRed(node.left.left)){
            node = moveRedLeft(node);
        }
        node.left = deleteMin(node.left);
        return balance(node);
    }

    private Node balance(Node h){
        if(isRed(h.right))
            h = rotateLeft(h);
        if(isRed(h.left) && isRed(h.left.left))
            h = rotateRight(h);
        if(isRed(h.left) && isRed(h.right))
            flipColours(h);
        h.N = size(h.left) + size(h.right) + 1;
        return h;
    }

    private Node moveRedLeft(Node h){
        // assuming that h is readn and both h.left and h.left.left are black
        // make h.left or one of its children red
        flipColours(h);
        if(isRed(h.right.left)){
            h.right = rotateRight(h.right);
            h = rotateLeft(h);
            flipColours(h);
        }
        return h;
    }

    @Override
    public void deleteMax() {
        if(isEmpty()){
            return;
        }
        // if both children black, set root red
        if(!isRed(root.left) && !isRed(root.right)){
            root.colour = RED;
        }
        root = deleteMax(root);
        if(!isEmpty())
            root.colour = BLACK;
    }

    private Node deleteMax(Node node) {
        if(isRed(node.left)){
            node = rotateRight(node);
        }
        if(node.right == null){
            return  null;
        }
        if(!isRed(node.right) && !isRed(node.right.left)){
            node = moveRedRight(node);
        }
        return balance(node);
    }

    private Node moveRedRight(Node h){
        // assuming that h is red and both h.right and h.right.left are black,
        // make h.right or one of its children red
        flipColours(h);
        if(isRed(h.left.left)) {
            h = rotateRight(h);
            flipColours(h);
        }
        return h;
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

    public int getHeight(){
        return getHeight(root);
    }

    public int getHeight(Node node){
        if(node == null) return -1;
        return 1 + Math.max(getHeight(node.left), getHeight(node.right));
    }

    public Node getRoot(){
        return root;
    }
}