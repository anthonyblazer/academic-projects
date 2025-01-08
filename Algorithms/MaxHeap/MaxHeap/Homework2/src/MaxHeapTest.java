import java.util.Arrays;

/**
 * Author: Anthony Blazer
 * Date: 09/03/2024
 * Class: CSCI Algorithms
 * Assignment: MaxHeap Implementation
 * 
 * This class tests the MaxHeap implementation by creating MaxHeap objects
 * with different arrays, printing the original array, the heap after buildMaxHeap,
 * invoking heapsort, and printing the sorted array.
 */

public class MaxHeapTest {

    public static void main(String[] args) {
        // Test 1: Randomized array
        Integer[] array1 = {7, 2, 5, 10, 6, 9};
        System.out.println("Original array: " + Arrays.toString(array1));
        MaxHeap<Integer> maxHeap1 = new MaxHeap<>(array1);
        System.out.println("Heap after buildMaxHeap:");
        maxHeap1.printMaxHeap();
        maxHeap1.heapsort();
        System.out.println("Heap after heapsort:");
        maxHeap1.printMaxHeap();
        System.out.println();

        // Test 2: Already sorted array
        Integer[] array2 = {1, 2, 3, 4, 5, 6};
        System.out.println("Original array: " + Arrays.toString(array2));
        MaxHeap<Integer> maxHeap2 = new MaxHeap<>(array2);
        System.out.println("Heap after buildMaxHeap:");
        maxHeap2.printMaxHeap();
        maxHeap2.heapsort();
        System.out.println("Heap after heapsort:");
        maxHeap2.printMaxHeap();
        System.out.println();

        // Test 3: Reverse sorted array
        Integer[] array3 = {6, 5, 4, 3, 2, 1};
        System.out.println("Original array: " + Arrays.toString(array3));
        MaxHeap<Integer> maxHeap3 = new MaxHeap<>(array3);
        System.out.println("Heap after buildMaxHeap:");
        maxHeap3.printMaxHeap();
        maxHeap3.heapsort();
        System.out.println("Heap after heapsort:");
        maxHeap3.printMaxHeap();
        System.out.println();

        // Test 4: Array with duplicates
        Integer[] array4 = {20, 16, 36, 9, 2, 20};
        System.out.println("Original array: " + Arrays.toString(array4));
        MaxHeap<Integer> maxHeap4 = new MaxHeap<>(array4);
        System.out.println("Heap after buildMaxHeap:");
        maxHeap4.printMaxHeap();
        maxHeap4.heapsort();
        System.out.println("Heap after heapsort:");
        maxHeap4.printMaxHeap();
        System.out.println();

        // Test 5: Array with a single element
        Integer[] array5 = {42};
        System.out.println("Original array: " + Arrays.toString(array5));
        MaxHeap<Integer> maxHeap5 = new MaxHeap<>(array5);
        System.out.println("Heap after buildMaxHeap:");
        maxHeap5.printMaxHeap();
        maxHeap5.heapsort();
        System.out.println("Heap after heapsort:");
        maxHeap5.printMaxHeap();
        System.out.println();
        
        // Test 6: Array with larger elements
        Integer[] array6 = {12,33,22,54,39,89,60};
        System.out.println("Original array: " + Arrays.toString(array6));
        MaxHeap<Integer> maxHeap6 = new MaxHeap<>(array6);
        System.out.println("Heap after buildMaxHeap:");
        maxHeap6.printMaxHeap();
        maxHeap6.heapsort();
        System.out.println("Heap after heapsort:");
        maxHeap6.printMaxHeap();
        System.out.println();
    }
}
