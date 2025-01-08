import java.util.Arrays;

/**
 * Author: Anthony Blazer
 * Date: 09/03/2024
 * Class: CSCI Algorithms
 * Assignment: MaxHeap Implementation
 * 
 * MaxHeap class that implements a zero-base indexed max-heap data structure using an array.
 * The heap stores elements of generic type T, where T extends Comparable<T>.
 * This implementation provides methods to build a max-heap, maintain the heap property,
 * and sort the array using heapsort.
 *
 * @param <T> the type of elements in the heap, which must extend Comparable<T>
 */

public class MaxHeap<T extends Comparable<T>> {
    private T[] heap; //array representing a heap
    private int heapsize; //size of the heap, or the length of the array

    
    /**
     * Constructs a MaxHeap from the given array of elements.
     * This constructor copies the elements from the input array to the heap array
     * and then builds a max-heap from the copied elements.
     *
     * @param array the array of elements to be used to build the heap
     */
    public MaxHeap(T[] array) {
        this.heap = (T[]) new Comparable[array.length];
        System.arraycopy(array, 0, this.heap, 0, array.length);
        this.heapsize = array.length;
        buildMaxHeap();
    }

    
    /**
     * Returns the index of the left child of the given index.
     * If the left child index is out of bounds, returns -1.
     *
     * @param index the index of the parent node
     * @return the index of the left child, or -1 if out of bounds
     */
    private int leftChildOf(int i) {
        int leftIndex = 2 * i + 1; // zero based indexing
        return leftIndex < heapsize ? leftIndex : -1;
    }

    
    /**
     * Returns the index of the right child of the given index.
     * If the right child index is out of bounds, returns -1.
     *
     * @param index the index of the parent node
     * @return the index of the right child, or -1 if out of bounds
     */
    private int rightChildOf(int i) {
        int rightIndex = 2 * i + 2; //zero based indexing
        return rightIndex < heapsize ? rightIndex : -1;
    }

    
    /**
     * Prints the current state of the heap array.
     * The method prints only the elements that are part of the heap (from index 0 to heapSize - 1).
     */
    public void printMaxHeap() {
        // Print the size of the heap
        System.out.println("Heap size: " + heapsize);
        
        // Print the elements of the heap
        System.out.println("Heap elements: " + Arrays.toString(Arrays.copyOfRange(heap, 0, heapsize)));
        //using Arrays.() as instructed
    }
    
    
    /**
     * Maintains the max-heap property at the given index.
     * This method ensures that the subtree rooted at the specified index is a max-heap.
     * If the node at the index is smaller than its children, it swaps the node with the larger child
     * and recursively calls maxHeapify on the affected subtree.
     *
     * @param index the index of the node to heapify
     */
    private void maxHeapify(int i) {
        int left = leftChildOf(i);
        int right = rightChildOf(i);
        int largest = i;

        // Compare the current node with its left child
        if (left != -1 && heap[left].compareTo(heap[largest]) > 0) {
            largest = left;
        }

        // Compare the current largest with the right child
        if (right != -1 && heap[right].compareTo(heap[largest]) > 0) {
            largest = right;
        }

        // If the largest is not the current node, swap and recurse
        if (largest != i) {
            exchange(i, largest);
            maxHeapify(largest);
        }
    }

    
    /**
     * Swaps the elements at the two specified indices in the heap array.
     * This helper method is used within the maxHeapify and heapsort methods
     * to reorder elements and maintain the heap property.
     *
     * @param i the index of the first element to swap
     * @param j the index of the second element to swap
     */
    private void exchange(int i, int j) {
        T temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    
    /**
     * Builds a max-heap from the elements in the heap array.
     * This method transforms the array into a max-heap by calling maxHeapify
     * on each non-leaf node, starting from the middle of the array and working backwards.
     */
    private void buildMaxHeap() {
        // Start from the last non-leaf node and move upwards
        for (int i = (heapsize / 2) - 1; i >= 0; i--) {
            maxHeapify(i);
        }
    }

    
    /**
     * Sorts the elements of the heap array in ascending order using the heapsort algorithm.
     * This method repeatedly swaps the root of the heap with the last element,
     * reduces the heap size, and calls maxHeapify to restore the max-heap property.
     */
    public void heapsort() {
        // Save the original heap size
        int originalHeapSize = heapsize;

        // Build the max heap
        buildMaxHeap();

        // Perform the sorting
        for (int i = heapsize - 1; i > 0; i--) {
            // Move the current max to the end
            exchange(0, i);
            // Reduce the heap size
            heapsize--;
            // Restore the max-heap property
            maxHeapify(0);
        }

        // Restore the original heap size
        heapsize = originalHeapSize;
    }
}
