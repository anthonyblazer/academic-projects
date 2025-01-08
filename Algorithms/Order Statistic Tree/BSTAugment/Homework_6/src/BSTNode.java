
/**
 * Author: Anthony Blazer
 * Class: CSCI 333 - Algorithms
 * Assignment: OrderStatTree
 * 
 * A node in a Binary Search Tree (BST), which stores a data element and
 * references to its parent, left child, and right child. It also maintains
 * the size of the subtree rooted at this node.
 *
 * @param <T> The type of data stored in the node, which must be comparable.
 */

public class BSTNode<T extends Comparable<T>> {
    private T data;
    private BSTNode<T> p; // Parent
    private BSTNode<T> left; // Left child
    private BSTNode<T> right; // Right child
    private int size; // Number of nodes in the subtree rooted at this node

    
    /**
     * Constructor to initialize a new BSTNode with the given data.
     *
     * @param data The data to be stored in the node.
     */
    public BSTNode(T data) {
        this.data = data;
        this.p = null;
        this.left = null;
        this.right = null;
        this.size = 1; // Initialize size to 1 for a new leaf node
    }

    // Getter methods
    
    /**
     * Returns the data stored in the node.
     *
     * @return The data stored in the node.
     */
    public T getData() {
        return data;
    }

    /**
     * Returns the parent of the node.
     *
     * @return The parent node, or null if this node is the root.
     */
    public BSTNode<T> getParent() {
        return p;
    }

    /**
     * Returns the left child of the node.
     *
     * @return The left child node, or null if there is no left child.
     */
    public BSTNode<T> getLeft() {
        return left;
    }

    /**
     * Returns the right child of the node.
     *
     * @return The right child node, or null if there is no right child.
     */
    public BSTNode<T> getRight() {
        return right;
    }

    /**
     * Returns the size of the subtree rooted at this node.
     *
     * @return The size of the subtree.
     */
    public int getSize() {
        return size;
    }

    // Setter methods
    
    /**
     * Sets the data stored in the node.
     *
     * @param data The data to be stored in the node.
     */
    public void setData(T data) {
        this.data = data;
    }

    /**
     * Sets the parent of the node.
     *
     * @param p The parent node to be set.
     */
    public void setParent(BSTNode<T> p) {
        this.p = p;
    }

    /**
     * Sets the left child of the node.
     *
     * @param left The left child node to be set.
     */
    public void setLeft(BSTNode<T> left) {
        this.left = left;
    }

    /**
     * Sets the right child of the node.
     *
     * @param right The right child node to be set.
     */
    public void setRight(BSTNode<T> right) {
        this.right = right;
    }

    /**
     * Sets the size of the subtree rooted at this node.
     *
     * @param size The size to be set for the subtree.
     */
    public void setSize(int size) {
        this.size = size;
    }
}

