package src.test;

import java.util.Arrays;
import src.main.Q2_RadixSort;

public class Q2_TestCases {

    private static void check(String[] original, int caseNo) {
        String[] expected = original.clone();
        Arrays.sort(expected); // Java's reference sort

        String[] actual = original.clone();
        Q2_RadixSort.radixSort(actual);

        assert Arrays.equals(expected, actual) : "\n❌  Case #" + caseNo +
                " failed\n   expected " + Arrays.toString(expected) +
                "\n   actual   " + Arrays.toString(actual);
    }

    public static void main(String[] args) {

        String[][] fixed = {
                {}, // Empty
                { "a" }, // Single character
                { "cat", "cat", "cat" }, // Duplicates
                { "ant", "bee", "cat", "dog", "eel" }, // Already sorted
                { "zoo", "yak", "xray", "wolf", "vole" }, // Reverse
                { "banana", "grape", "apple", "kiwi",
                        "peach", "appla" }, // Mixed
                { "pay", "payback", "payment", "payout",
                        "pay" }, // Same prefixes
                { "", "a", "ab", "abc", "abcd" }, // Different length + empty
        };

        int num = 1;
        for (String[] arr : fixed)
            check(arr, num++);

        System.out.println("\n✅  All " + (num - 1) + " string test cases passed!");
    }
}
