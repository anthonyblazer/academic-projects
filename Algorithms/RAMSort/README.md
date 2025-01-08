# RAMSort Implementation


**Name:** RAM Model Sorting and Randomized Quickselect
**Description:** The provided Quickselect class is a Java implementation of the Randomized Quickselect algorithm, designed to efficiently find the k-th smallest or largest element in an unordered array. The class works by partitioning the array around a randomly chosen pivot, recursively narrowing the search to the relevant subarray. It includes methods for partitioning the array, swapping elements, and performing the quickselect operation on subarrays. The randomizedQuickselect method handles the core algorithm, while a public wrapper method ensures non-destructive behavior by operating on a deep copy of the input data. This implementation is generic, supporting any type that implements Comparable.
Along with the Quickselect is a main class that tests all the methods in the Quickselect class.


**Languages:** Java
**Version:** Java 11 JRE
**IDE:** Eclipse