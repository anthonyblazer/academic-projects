/**
 * @author Anthony Blazer
 * Class: CSCI 333 - Algorithms
 * Term: 09-27-2024
 * Homework: RamSortSelect
 * 
 * A QuickSelect class that implemented the randomized alogrithm from class lectures
 * and the provided textbook. QuickSelect is an efficient selection algorithm that 
 * finds the k-th smallest (or largest) element in an unordered list by partitioning 
 * the array around a randomly chosen pivot and recursively narrowing the search to 
 * the relevant subarray.
 */


import java.util.Arrays;
import java.util.Random;

public class Quickselect<T extends Comparable<T>> {

    private T[] data;

    /**
     * Constructor to initialize the Quickselect object with a shallow copy of the input array.
     * @param array The array to be copied into the data field.
     */
    public Quickselect(T[] array) {
        this.data = Arrays.copyOf(array, array.length);
    }

    /**
     * Private helper method for partitioning the array, used by Quickselect and Quicksort algorithms.
     * @param start The starting index of the subarray.
     * @param end The ending index of the subarray.
     * @param arr The array to be partitioned.
     * @return The index of the pivot after partitioning.
     */
    private int partition(T[] arr, int start, int end) {
        T pivot = arr[end];
        int i = start - 1;

        for (int j = start; j < end; j++) {
            if (arr[j].compareTo(pivot) <= 0) {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, end);
        return i + 1;
    }

    /**
     * Private helper method to swap elements in an array.
     * @param arr The array where the swap is to be made.
     * @param i The index of the first element.
     * @param j The index of the second element.
     */
    private void swap(T[] arr, int i, int j) {
        T temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    /**
     * Private method implementing the Randomized Quickselect algorithm to find the i-th order statistic.
     * This method operates on the subarray defined by the inclusive bounds [start, end].
     * @param arr The array to search within (can be a deep copy to preserve the original array).
     * @param start The starting index of the subarray.
     * @param end The ending index of the subarray.
     * @param i The order statistic to find (1-based index).
     * @return The element in the i-th position when sorted.
     */
    private T randomizedQuickselect(T[] arr, int start, int end, int i) {
        // Base case: if the subarray has only one element
        if (start == end) {
            return arr[start];
        }

        // Choose a random pivot index between start and end
        Random random = new Random();
        int pivotIndex = random.nextInt(end - start + 1) + start;

        // Swap the random pivot with the last element and partition the array
        swap(arr, pivotIndex, end);
        int q = partition(arr, start, end);

        // The position of the pivot element in the sorted array
        int k = q - start + 1;

        // If the pivot is the i-th order statistic, return it
        if (i == k) {
            return arr[q];
        }
        // If the i-th order statistic is in the left part of the array
        else if (i < k) {
            return randomizedQuickselect(arr, start, q - 1, i);
        }
        // If the i-th order statistic is in the right part of the array
        else {
            return randomizedQuickselect(arr, q + 1, end, i - k);
        }
    }

    /**
     * Public wrapper method for Randomized Quickselect.
     * This method ensures that the search occurs non-destructively by making a deep copy of the data field array.
     * @param i The order statistic to find (1-based index).
     * @return The element in the i-th position when sorted.
     */
    public T randomizedQuickselect(int i) {
        // Make a deep copy of the data array
        T[] copy = Arrays.copyOf(data, data.length);
        // Perform randomized quickselect on the copy
        return randomizedQuickselect(copy, 0, copy.length - 1, i);
    }
}