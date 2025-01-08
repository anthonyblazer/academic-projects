/**
 * @author Anthony Blazer
 * Class: CSCI 333 - Algorithms
 * Term: 09-27-2024
 * Homework: RamSortSelect
 * 
 * A main class that tests QUickselect of type T but also implements the counting Sort
 * algorithm. Counting Sort is a non-comparison-based sorting algorithm that counts 
 * the occurrences of each unique element in an input array, with this using 0s 
 * to determine the position of each element in the sorted output array.
 */



import java.util.Arrays;

public class Main {

    /**
     * Private helper method that implements the Counting Sort algorithm.
     * 
     * @param A The input array to be sorted.
     * @param B The output array to store the sorted result.
     * @param k The maximum value in the input array.
     */
    private static void countingSort(int[] A, int[] B, int k) {
        int[] C = new int[k + 1]; // Create the count array with a size of (k + 1)

        // Step 1: Initialize count array C
        for (int i = 0; i <= k; i++) {
            C[i] = 0;
        }

        // Step 2: Count the occurrences of each value in A
        for (int j = 0; j < A.length; j++) {
            C[A[j]]++;
        }

        // Step 3: Update C to store cumulative counts
        for (int i = 1; i <= k; i++) {
            C[i] += C[i - 1];
        }

        // Step 4: Place the elements in sorted order in B
        for (int j = A.length - 1; j >= 0; j--) {
            B[C[A[j]] - 1] = A[j]; // Place A[j] in its correct position in B
            C[A[j]]--; // Decrease count for the value A[j]
        }
    }

    /**
     * Public method that performs Counting Sort on an input array.
     * 
     * @param A The input array to be sorted.
     * @return A sorted copy of the input array.
     */
    public static int[] countingSort(int[] A) {
        // Find the maximum value in A to determine the size of the count array
        int k = Arrays.stream(A).max().getAsInt();

        // Allocate output array B
        int[] B = new int[A.length];

        // Call the private helper method to perform counting sort
        countingSort(A, B, k);

        // Return the sorted array
        return B;
    }

    /**
     * Main method to test both Counting Sort and Randomized Quickselect.
     */
    public static void main(String[] args) {

        int[] arr1 = {4, 2, 3, 3, 1};
        int[] arr2 = {9, 7, 2, 5, 3, 1, 6, 8, 4, 0};
        int[] arr3 = {1, 4, 1, 2, 7, 5, 2};

        System.out.println("Original array 1: " + Arrays.toString(arr1));
        System.out.println("Sorted array 1: " + Arrays.toString(countingSort(arr1)));

        System.out.println("Original array 2: " + Arrays.toString(arr2));
        System.out.println("Sorted array 2: " + Arrays.toString(countingSort(arr2)));

        System.out.println("Original array 3: " + Arrays.toString(arr3));
        System.out.println("Sorted array 3: " + Arrays.toString(countingSort(arr3)));

        // Test Randomized Quickselect
        Integer[] array = {3, 6, 2, 8, 4, 7, 5, 1};
        Quickselect<Integer> quickselect = new Quickselect<>(array);

        System.out.println("\nTesting Randomized Quickselect:");
        System.out.println("3rd smallest element: " + quickselect.randomizedQuickselect(3));
        System.out.println("5th smallest element: " + quickselect.randomizedQuickselect(5));
        System.out.println("1st smallest element: " + quickselect.randomizedQuickselect(1));

        // The original array should remain unchanged
        System.out.println("Original array (unchanged): " + Arrays.toString(array));
    }
}