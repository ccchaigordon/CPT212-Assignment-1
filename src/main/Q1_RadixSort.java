package src.main;

import java.util.Arrays;

public class Q1_RadixSort {

    public static long count = 0; // Counter for number of primitive operations

    // Get largest value to get the max digit count

    public static int getMax(int[] values) {

        if (values == null || values.length == 0) {
            return 0;
        }

        int max = values[0];
        count += 2; // 1 array indexing, 1 assignment

        count++; // Assignment (i = 0)
        for (int i = 0; i < values.length; i++) {
            count += 2; // 1 length access, 1 comparison

            int val = values[i];
            count += 2; // 1 array indexing, 1 assignment

            count++; // Comparison
            if (val > max) {
                count++; // 1 assignment
                max = val;
            }
            count += 2; // 1 arithmetic, 1 assignment (i++)
        }
        count += 2; // 1 length access, 1 comparison (i < values.length == false)
        count++; // Return
        return max;
    }

    // Print the buckets row by row

    public static void printPass(int pass, int[][] buckets, int[] bucketSize) {
        System.out.print("After Pass " + pass + ": ");
        for (int i = 0; i < 10; i++)
            for (int j = 0; j < bucketSize[i]; j++)
                System.out.print(buckets[i][j] + " ");
        System.out.println();
    }

    // Copy from source to destination (bucket)

    private static void scatterBuckets(int[][] source, int[] sourceSize, int[][] destination, int[] destinationSize,
            int placeValue) {

        // Reset all the counters first

        count++; // Method call
        Arrays.fill(destinationSize, 0);

        count++; // Assignment (i = 0)
        for (int i = 0; i < 10; i++) {
            count++; // Comparison (outer loop)

            count++; // Assignment (j = 0)
            for (int j = 0; j < sourceSize[i]; j++) {

                count += 2; // 1 array indexing, 1 comparison (inner loop)

                int value = source[i][j]; // "value" is the elements in the passed array
                count += 3; // 2 array indexing, 1 assignment

                int destinationRow = (value / placeValue) % 10;
                count += 3; // 2 arithmetic operations, 1 assignment

                destination[destinationRow][destinationSize[destinationRow]++] = value;
                count += 6; // 1 increment, 3 array indexing, 2 assignment

                count += 2; // 1 arithmetic, 1 assignment (j++)
            }
            count += 2; // 1 array indexing, 1 comparison (j < sourceSize[i]) == false)
 
            count += 2; // 1 arithmetic, 1 assignment (i++)
        }
        count++; // Comparison (i < 10 == false)
    }

    // Start sorting

    public static void radixSort(int[] list) {

        int n = list.length;
        count += 2; // 1 length access, 1 assignment
        int[][] firstBuckets = new int[10][n];
        int[] firstBucketSize = new int[10];
        int[][] secondBuckets = new int[10][n];
        int[] secondBucketSize = new int[10];

        count++; // Assignment
        int placeValue = 1; // 1 = 1st digit, 2 = 2nd digit, etc.

        count++; // Assignment (i = 0)
        for (int i = 0; i < list.length; i++) {
            count += 2; // 1 length access, 1 comparison

            int key = list[i];
            count += 2; // 1 array indexing, 1 assignment

            int row = (key / placeValue) % 10;
            count += 3; // 2 arithmetic operations, 1 assignment
            firstBuckets[row][firstBucketSize[row]++] = key;
            count += 6; // 1 increment, 3 array indexing, 2 assignment

            count += 2; // 1 arithmetic, 1 assignment (i++)
        }
        count += 2; // 1 length access, 1 comparison (key < list.length == false)
        printPass(1, firstBuckets, firstBucketSize);
        placeValue *= 10;
        count += 2; // 1 arithmetic, 1 assignment

        // Remaining passes

        int maxDigits = Integer.toString(getMax(list)).length(); // Get the max digit from max value
        count += 4; // 2 method call, 1 length access, 1 assignment

        count++; // Assignment (pass = 2)
        for (int pass = 2; pass <= maxDigits; pass++, placeValue *= 10) {
            count++; // Comparison

            count += 2; // 1 arithmetic operation, 1 comparison
            if (pass % 2 == 0) { // From odd to even pass
                scatterBuckets(firstBuckets, firstBucketSize,
                        secondBuckets, secondBucketSize,
                        placeValue);
                count++; // 1 method call
                printPass(pass, secondBuckets, secondBucketSize);
            } else { // From even to odd pass
                scatterBuckets(secondBuckets, secondBucketSize,
                        firstBuckets, firstBucketSize,
                        placeValue);
                count++; // 1 method call
                printPass(pass, firstBuckets, firstBucketSize);
            }
            count += 4; // 2 arithmetic operations, 2 assignment (pass++, placeValue *= 10)
        }

        count++; // Comparison (pass <= maxDigits == false)

        // Copy back to the array

        int[][] finalBuckets = (maxDigits % 2 == 0) ? secondBuckets : firstBuckets;
        count += 3; // 1 arithmetic operation, 1 comparison, 1 assignment
        int[] finalBucketSize = (maxDigits % 2 == 0) ? secondBucketSize
                : firstBucketSize;
        count += 3; // 1 arithmetic operation, 1 comparison, 1 assignment

        int outputIndex = 0;
        count++; // Assignment

        count++; // Assignment (i = 0)
        for (int i = 0; i < 10; i++) {
            count++; // Comparison (outer loop)

            count++; // Assignment (j = 0)
            for (int j = 0; j < finalBucketSize[i]; j++) {
                count += 2; // 1 array indexing, 1 comparison (inner loop)
                list[outputIndex++] = finalBuckets[i][j];
                count += 6; // 1 increment, 3 array indexing, 2 assignment
                count += 2; // 1 arithmetic, 1 assignment (j++)
            }
            count += 2; // 1 array indexing, 1 comparison (j < finalBucketSize[i]) == false)
            count += 2; // 1 arithmetic, 1 assignment (i++)
        }
        count++; // Comparison (i < 10 == false)
    }

    public static void main(String[] args) {
        int[] data = { 275, 87, 426, 61, 409, 170, 677, 503 };

        System.out.println("Unsorted: " + Arrays.toString(data) + "\n");
        count++; // Method call
        radixSort(data);
        System.out.println("\nSorted: " + Arrays.toString(data));
        System.out.println("\nNumber of primitive operations: " + count);
    }
}