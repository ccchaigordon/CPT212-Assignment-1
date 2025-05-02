package src.test;

import java.util.Arrays;

import src.main.Q1_RadixSort;

public class TestCases {
    private static void check(int[] original, int caseNo) {
        int[] expected = original.clone();
        Arrays.sort(expected);

        int[] actual = original.clone();
        Q1_RadixSort.radixSort(actual);

        assert Arrays.equals(expected, actual) : "\n❌  Case #" + caseNo +
                " failed\n   expected " + Arrays.toString(expected) +
                "\n   actual   " + Arrays.toString(actual);
    }

    public static void main(String[] args) {

        int[][] fixed = {
                {}, // Empty array
                { 42 }, // Single element
                { 7, 7, 7, 7, 7 }, // Duplicates
                { 1, 2, 3, 4, 5, 6, 7, 8, 9 }, // Already sorted
                { 9, 8, 7, 6, 5, 4, 3, 2, 1 }, // Reverse order
                { 275, 87, 426, 61, 409, 170 }, // Mixed
                { 0, 5, 50, 500, 5000, 45, 450, 0 }, // Zeros + different length
                { 1_000_000, 0, 999_999 }, // Very large digits
        };

        // Run test cases

        int caseNo = 1;
        for (int[] data : fixed)
            check(data, caseNo++);

        System.out.println("\n✅  All " + (caseNo - 1) + " test cases passed!");
    }
}
