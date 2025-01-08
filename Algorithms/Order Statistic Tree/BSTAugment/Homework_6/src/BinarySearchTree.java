
/**
 * Author: Anthony Blazer
 * Class: CSCI 333 - Algorithms
 * Assignment: OrderStatTree
 * 
 * A Binary Search Tree (BST) implementation with augmentation to support
 * order statistic queries. It includes methods for insertion, deletion,
 * searching, and traversals, as well as select and rank operations.
 *
 * @param <T> The type of elements stored in the tree, which must be comparable.
 */

public class BinarySearchTree<T extends Comparable<T>> {
    private BSTNode<T> root;
    private int size;

    /**
     * Constructor to initialize an empty Binary Search Tree.
     */
    public BinarySearchTree() {
        this.root = null;
        this.size = 0;
    }

    
    /**
     * Returns the number of elements in the tree.
     *
     * @return The size of the tree.
     */
    public int getSize() {
        return size;
    }

    // Private helper method: Transplant
    private void transplant(BSTNode<T> u, BSTNode<T> v) {
        if (u.getParent() == null) {
            root = v;
        } else if (u == u.getParent().getLeft()) {
            u.getParent().setLeft(v);
        } else {
            u.getParent().setRight(v);
        }
        if (v != null) {
            v.setParent(u.getParent());
        }
    }

    // Private method: Search
    private BSTNode<T> search(BSTNode<T> x, T key) {
        if (x == null || key.equals(x.getData())) {
            return x;
        }
        if (key.compareTo(x.getData()) < 0) {
            return search(x.getLeft(), key);
        } else {
            return search(x.getRight(), key);
        }
    }

    /**
     * Searches for a key in the tree.
     *
     * @param key The key to search for.
     * @return True if the key is found, false otherwise.
     */
    public boolean search(T key) {
        return search(root, key) != null;
    }

    
    
    // Private method: Minimum
    private BSTNode<T> minimum(BSTNode<T> x) {
        while (x.getLeft() != null) {
            x = x.getLeft();
        }
        return x;
    }
    
    /**
     * Finds the minimum element in the tree.
     *
     * @return The minimum element, or null if the tree is empty.
     */
    public T minimum() {
        if (root == null) return null;
        return minimum(root).getData();
    }

    
    
    // Private method: Maximum
    private BSTNode<T> maximum(BSTNode<T> x) {
        while (x.getRight() != null) {
            x = x.getRight();
        }
        return x;
    }

    /**
     * Finds the maximum element in the tree.
     *
     * @return The maximum element, or null if the tree is empty.
     */
    public T maximum() {
        if (root == null) return null;
        return maximum(root).getData();
    }

    
    
    
    // Private method: Successor
    private BSTNode<T> successor(BSTNode<T> x) {
        if (x.getRight() != null) {
            return minimum(x.getRight());
        }
        BSTNode<T> y = x.getParent();
        while (y != null && x == y.getRight()) {
            x = y;
            y = y.getParent();
        }
        return y;
    }

    /**
     * Finds the successor of a given key in the tree.
     *
     * @param key The key whose successor is to be found.
     * @return The successor of the key, or null if no successor exists.
     */

    public T successor(T key) {
        BSTNode<T> node = search(root, key);
        if (node == null) return null;
        BSTNode<T> succ = successor(node);
        return (succ != null) ? succ.getData() : null;
    }

    
    
    
    // Private method: Predecessor
    private BSTNode<T> predecessor(BSTNode<T> x) {
        if (x.getLeft() != null) {
            return maximum(x.getLeft());
        }
        BSTNode<T> y = x.getParent();
        while (y != null && x == y.getLeft()) {
            x = y;
            y = y.getParent();
        }
        return y;
    }

    /**
     * Finds the predecessor of a given key in the tree.
     *
     * @param key The key whose predecessor is to be found.
     * @return The predecessor of the key, or null if no predecessor exists.
     */
    public T predecessor(T key) {
        BSTNode<T> node = search(root, key);
        if (node == null) return null;
        BSTNode<T> pred = predecessor(node);
        return (pred != null) ? pred.getData() : null;
    }

    
    
    // Private method: Insert
    private void insert(BSTNode<T> z) {
        BSTNode<T> y = null;
        BSTNode<T> x = root;
        while (x != null) {
            y = x;
            x.setSize(x.getSize() + 1); // Increment size on the path to the root
            if (z.getData().compareTo(x.getData()) < 0) {
                x = x.getLeft();
            } else {
                x = x.getRight();
            }
        }
        z.setParent(y);
        if (y == null) {
            root = z; // Tree was empty
        } else if (z.getData().compareTo(y.getData()) < 0) {
            y.setLeft(z);
        } else {
            y.setRight(z);
        }
        size++;
    }

    /**
     * Inserts a new key into the tree.
     *
     * @param key The key to be inserted.
     */
    public void insert(T key) {
        BSTNode<T> newNode = new BSTNode<>(key);
        insert(newNode);
    }

    
    
    // Private method: Delete
    private void delete(BSTNode<T> z) {
        BSTNode<T> q = null;
        if (z.getLeft() == null || z.getRight() == null) {
            q = z.getParent();
        } else {
            BSTNode<T> y = minimum(z.getRight());
            if (y.getParent() != z) {
                q = y.getParent();
            } else {
                q = y;
            }
        }

        // Normal delete operation
        if (z.getLeft() == null) {
            transplant(z, z.getRight());
        } else if (z.getRight() == null) {
            transplant(z, z.getLeft());
        } else {
            BSTNode<T> y = minimum(z.getRight());
            if (y.getParent() != z) {
                transplant(y, y.getRight());
                y.setRight(z.getRight());
                y.getRight().setParent(y);
            }
            transplant(z, y);
            y.setLeft(z.getLeft());
            y.getLeft().setParent(y);
        }
        size--;

        // Recalculate sizes
        while (q != null) {
            q.setSize((q.getLeft() != null ? q.getLeft().getSize() : 0) + (q.getRight() != null ? q.getRight().getSize() : 0) + 1);
            q = q.getParent();
        }
    }
    
    /**
     * Deletes a key from the tree.
     *
     * @param key The key to be deleted.
     */
    public void delete(T key) {
        BSTNode<T> node = search(root, key);
        if (node != null) {
            delete(node);
        }
    }

    
    
    // Private method: Select
    private BSTNode<T> select(BSTNode<T> x, int i) {
        if (x == null) return null;
        int r = (x.getLeft() != null ? x.getLeft().getSize() : 0) + 1;
        if (i == r) {
            return x;
        } else if (i < r) {
            return select(x.getLeft(), i);
        } else {
            return select(x.getRight(), i - r);
        }
    }

    /**
     * Selects the i-th smallest element in the tree.
     *
     * @param i The rank of the element to select.
     * @return The i-th smallest element, or null if it does not exist.
     */
    public T select(int i) {
        BSTNode<T> node = select(root, i);
        return (node != null) ? node.getData() : null;
    }

    
    
    // Private method: Rank
    private int rank(BSTNode<T> x) {
        int r = (x.getLeft() != null ? x.getLeft().getSize() : 0) + 1;
        BSTNode<T> y = x;
        while (y != root) {
            if (y == y.getParent().getRight()) {
                r += (y.getParent().getLeft() != null ? y.getParent().getLeft().getSize() : 0) + 1;
            }
            y = y.getParent();
        }
        return r;
    }

    /**
     * Finds the rank of a given key in the tree.
     *
     * @param key The key whose rank is to be found.
     * @return The rank of the key, or -1 if the key is not found.
     */
    public int rank(T key) {
        BSTNode<T> node = search(root, key);
        return (node != null) ? rank(node) : -1; // Return -1 if the node is not found
    }

    
    
    // Private method: Pre-order traversal
    private void preOrder(BSTNode<T> node) {
        if (node != null) {
            System.out.print(node.getData() + " ");
            preOrder(node.getLeft());
            preOrder(node.getRight());
        }
    }

    /**
     * Performs a pre-order traversal of the tree and prints the elements.
     */
    public void preOrder() {
        preOrder(root);
        System.out.println(); // For a new line after traversal
    }

    
    
    // Private method: In-order traversal
    private void inOrder(BSTNode<T> node) {
        if (node != null) {
            inOrder(node.getLeft());
            System.out.print(node.getData() + " ");
            inOrder(node.getRight());
        }
    }

    /**
     * Performs an in-order traversal of the tree and prints the elements.
     */
    public void inOrder() {
        inOrder(root);
        System.out.println(); // For a new line after traversal
    }

    
    
    // Private method: Post-order traversal
    private void postOrder(BSTNode<T> node) {
        if (node != null) {
            postOrder(node.getLeft());
            postOrder(node.getRight());
            System.out.print(node.getData() + " ");
        }
    }

    /**
     * Performs a post-order traversal of the tree and prints the elements.
     */
    public void postOrder() {
        postOrder(root);
        System.out.println(); // For a new line after traversal
    }
}