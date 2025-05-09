package src.main;

import java.util.Arrays;
import java.util.Random;

public class Q2_RadixSort {

    public static long count = 0; // Counter for number of primitive operations

    // Get largest length of word in the list

    public static int getMaxLength(String[] arr) {

        if (arr == null || arr.length == 0) {
            return 0;
        }

        int maxLength = 0;
        count++; // Assignment

        count++; // Assignment (i = 0)
        for (int i = 0; i < arr.length; i++) {
            count += 2; // 1 length access, 1 comparison

            String str = arr[i];
            count += 2; // 1 array indexing, 1 assignment

            count += 2; // 1 length access, 1 comparison
            if (str.length() > maxLength) {
                count += 2; // 1 length access, 1 assignment
                maxLength = str.length();
            }
            count += 2; // 1 arithmetic, 1 assignment (i++)
        }
        count += 2; // 1 length access, 1 comparison (i < arr.length == false)
        count++; // Return
        return maxLength;
    }

    // Print the buckets row by row

    public static void printPass(int pass, String[][] buckets, int[] bucketSize) {
        System.out.print("After Pass " + pass + ": ");
        for (int i = 0; i < 26; i++)
            for (int j = 0; j < bucketSize[i]; j++)
                System.out.print(buckets[i][j] + " ");
        System.out.println();
    }

    // Copy from source to destination (bucket)

    private static void scatterBuckets(String[][] source, int[] sourceSize, String[][] destination,
            int[] destinationSize,
            int placeValue) {

        // Reset all the counters first

        count++; // Method call
        Arrays.fill(destinationSize, 0);

        count++; // Assignment (i = 0)
        for (int i = 0; i < 26; i++) {
            count++; // Comparison (outer loop)

            count++; // Assignment (j = 0)
            for (int j = 0; j < sourceSize[i]; j++) {

                count += 2; // 1 array indexing, 1 comparison (inner loop)

                String word = source[i][j];
                count += 3; // 2 array indexing, 1 assignment

                int charIndex;
                count += 2; // 1 length access, 1 comparison
                if (placeValue < word.length()) {
                    charIndex = word.charAt(placeValue) - 'a';
                    count += 3; // 1 method call, 1 arithmetic, 1 assignment
                } else {
                    charIndex = -1;
                    count++; // 1 assignment
                }

                count++; // Comparison
                if (charIndex >= 0) {
                    destination[charIndex][destinationSize[charIndex]++] = word;
                    count += 6; // 1 increment, 3 array indexing, 2 assignment
                } else {
                    destination[0][destinationSize[0]++] = word; // Place shorter words in the first bucket
                    count += 6; // 1 increment, 3 array indexing, 2 assignment
                }
                count += 2; // 1 arithmetic, 1 assignment (j++)
            }
            count += 2; // 1 array indexing, 1 comparison (j < sourceSize[i] == false)

            count += 2; // 1 arithmetic, 1 assignment (i++)
        }
        count++; // Comparison (i < 26 == false)
    }

    // Start sorting

    public static void radixSort(String[] list) {

        // Find the maximum length of strings to know the number of passes

        count += 2; // 1 method call, 1 assignment
        int maxLength = getMaxLength(list);

        count += 2; // 1 length access, 1 assignment
        int n = list.length;
        String[][] firstBuckets = new String[26][n];
        int[] firstBucketSize = new int[26];
        String[][] secondBuckets = new String[26][n];
        int[] secondBucketSize = new int[26];

        count++; // Assignment (i = 0)
        for (int i = 0; i < list.length; i++) {
            count += 2; // 1 length access, 1 comparison

            String word = list[i];
            count += 2; // 1 array indexing, 1 assignment

            int charIndex;
            count += 3; // 1 arithmetic, 1 length access, 1 comparison
            if ((maxLength - 1) < word.length()) {
                charIndex = word.charAt(maxLength - 1) - 'a';
                count += 4; // 1 method call, 2 arithmetic, 1 assignment
            } else {
                charIndex = -1;
                count++; // Assignment
            }

            count++; // Comparison
            if (charIndex >= 0) {
                firstBuckets[charIndex][firstBucketSize[charIndex]++] = word;
                count += 6; // 1 increment, 3 array indexing, 2 assignment
            } else {
                firstBuckets[0][firstBucketSize[0]++] = word; // Place shorter words in the first bucket
                count += 6; // 1 increment, 3 array indexing, 2 assignment
            }
            count += 2; // 1 arithmetic, 1 assignment (i++)
        }
        count += 2; // 1 length access, 1 comparison (i < list.length == false)

        count++; // Assignment
        int passNum = 1; // Pass number starts from 1
        printPass(passNum, firstBuckets, firstBucketSize);
        passNum++; // Increment pass number for the next pass
        count += 2; // 1 arithmetic, 1 assignment

        // Remaining passes

        count += 2; // 1 arithmetic, 1 assignment (placeValue = maxLength - 2)
        for (int placeValue = maxLength - 2; placeValue >= 0; placeValue--, passNum++) {
            count++; // Comparison

            count += 2; // 1 arithmetic, 1 comparison
            if (passNum % 2 == 0) { // From odd to even pass
                scatterBuckets(firstBuckets, firstBucketSize,
                        secondBuckets, secondBucketSize,
                        placeValue);
                count++; // 1 method call
                printPass(passNum, secondBuckets, secondBucketSize);
            } else { // From even to odd pass
                scatterBuckets(secondBuckets, secondBucketSize,
                        firstBuckets, firstBucketSize,
                        placeValue);
                count++; // 1 method call
                printPass(passNum, firstBuckets, firstBucketSize);
            }
            count += 4; // 2 arithmetic operations, 2 assignment (passNum++, placeValue--)
        }
        count++; // Comparison (placeValue >= 0 == false)

        // Copy back to the array

        String[][] finalBuckets = (maxLength % 2 == 0) ? secondBuckets : firstBuckets;
        count += 3; // 1 arithmetic operation, 1 comparison, 1 assignment
        int[] finalBucketSize = (maxLength % 2 == 0) ? secondBucketSize
                : firstBucketSize;
        count += 3; // 1 arithmetic operation, 1 comparison, 1 assignment

        int outputIndex = 0;
        count++; // Assignment

        count++; // Assignment (i = 0)
        for (int i = 0; i < 26; i++) {
            count++; // Comparison

            count++; // Assignment (j = 0)
            for (int j = 0; j < finalBucketSize[i]; j++) {
                count += 2; // 1 array indexing, 1 comparison
                list[outputIndex++] = finalBuckets[i][j];
                count += 6; // 1 increment, 3 array indexing, 2 assignment
                count += 2; // 1 arithmetic, 1 assignment (j++)
            }
            count += 2; // 1 array indexing, 1 comparison (j < finalBucketSize[i] == false)
            count += 2; // 1 arithmetic, 1 assignment (i++)
        }
        count++; // Comparison (i < 26 == false)
    }

    public static void main(String[] args) {
        String[] data = { "banana", "grape", "apple", "kiwi", "peach", "apple" };

        System.out.println("Unsorted: " + Arrays.toString(data) + "\n");
        count++; // Method call
        radixSort(data);
        System.out.println("\nSorted: " + Arrays.toString(data));
        System.out.println("\nNumber of primitive operations: " + count + "\n");
        System.out.println("Test cases for different array sizes (n): \n");

        // Initialise the array size for testing
        int[] n = { 1, 3, 5, 10, 20, 40, 60, 80, 100 };
        count = 0;
        for (int i = 0; i < n.length; i++) 
        {
            int arrSize = n[i];
            String[] input = new String[arrSize];
            Random rand = new Random(21);
            StringBuilder alphaBuilder = new StringBuilder();
            for (char c = 'a'; c <= 'z'; c++) 
                alphaBuilder.append(c);
            String alphabet = alphaBuilder.toString();

            // Fill the array with random strings
            for (int j = 0; j < arrSize; j++) 
            {
                int strLen = rand.nextInt(arrSize) + 1; 
                StringBuilder sb = new StringBuilder(strLen);
                for (int k = 0; k < strLen; k++) {
                    sb.append(alphabet.charAt(rand.nextInt(alphabet.length())));
                }
                input[j] = sb.toString();
            }
            System.out.println("Generated Strings:");
            for (String s : input) {
                System.out.print(s + " ");
            }
            System.out.println();
            
            count++; // Method call
            radixSort(input);

            System.out.println("n = " + arrSize);
            System.out.println("Number of primitive operations: " + count + "\n");

            count = 0; // Reset operation counter for next iteration
        }
    }
}
