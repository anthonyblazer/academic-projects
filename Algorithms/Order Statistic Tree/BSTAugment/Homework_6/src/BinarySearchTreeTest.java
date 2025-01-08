/**
 * Author: Anthony Blazer
 * Class: CSCI 333 - Algorithms
 * Assignment: OderStatTree
 * 
 * A test class for the BinarySearchTree implementation. This class
 * contains a main method to test various operations of the BinarySearchTree,
 * including insertion, deletion, searching, traversals, and order statistic queries. 
 */
public class BinarySearchTreeTest {

    /**
     * The main method to test the BinarySearchTree class. It performs the following:
     * - Creates a BinarySearchTree and inserts at least 20 elements.
     * - Performs pre-order, in-order, and post-order traversals.
     * - Searches for keys in the tree and keys not in the tree, printing results.
     * - Deletes a handful of elements and prints the tree size and contents.
     * - Tests the select and rank methods with various arguments.
     *
     * @param args Command-line arguments (not used).
     */
	public static void main(String[] args) {
		
		// Create a BinarySearchTree
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();

        // Inserting 22 elements into the tree
        int[] elements = {15, 6, 18, 3, 7, 17, 20, 2, 4, 13, 9, 5, 55, 8, 10, 11, 12, 14, 1, 16, 19, 21};
        for (int element : elements) {
            bst.insert(element);
        }

        // Performing pre-order, in-order, and post-order traversals
        System.out.println("Pre-order traversal:");
        bst.preOrder();

        System.out.println("In-order traversal:");
        bst.inOrder();

        System.out.println("Post-order traversal:");
        bst.postOrder();

        // Print the size of the tree
        System.out.println("Size of the tree: " + bst.getSize());

        // Searching for 10 keys
        int[] keysToSearch = {15, 4, 10, 19, 8, 21, 22, 23, 55, 25};
        for (int key : keysToSearch) {
            boolean found = bst.search(key);
            System.out.println("Search for " + key + ": " + (found ? "Found" : "Not Found"));
        }

        // Deleting 6 keys
        int[] deleteKeys = {15, 3, 10, 19, 8, 55};
        for (int key : deleteKeys) {
            bst.delete(key);
        }

        // Print the size of the tree after deletions
        System.out.println("Size of the tree after deletions: " + bst.getSize());

        // Perform another in-order traversal to print out the contents
        System.out.println("In-order traversal after deletions:");
        bst.inOrder();

        // Testing the select and rank methods
        System.out.println("Testing select method:");
        for (int i = 1; i <= 7; i++) {
            Integer selected = bst.select(i);
            System.out.println("Select " + i + (i<=3 ?(i==2?"-nd":(i==1?"-st":"-rd")):"-th")+ " smallest: " + (selected != null ? selected : "None"));
        }

        System.out.println("Testing rank method:");
        int[] keysForRank = {6, 7, 17, 20, 5};
        for (int key : keysForRank) {
            int rank = bst.rank(key);
            System.out.println("Rank of " + key + ": " + (rank != -1 ? rank : "Not Found"));
        }

	}

}
