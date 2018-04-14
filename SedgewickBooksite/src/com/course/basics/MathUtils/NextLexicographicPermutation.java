package com.course.basics.MathUtils;

// the algorithm is inspired from
// https://www.nayuki.io/page/next-lexicographical-permutation-algorithm

import java.util.Arrays;

/**
 * Since this is a permutation, the elements are reordered in such a way that next
 * lexicographic sequence is observed
 */
public class NextLexicographicPermutation {
    public static void main(String[] args) {
        Integer[] arr = {0, 1, 2, 5, 3, 3, 0};
        getNextPermutation(arr);
        System.out.println(Arrays.toString(arr));
    }

    // modifies input arr in place to reflect next lexicographic permutation
    static void getNextPermutation(Integer[] arr) {
        // step 1. find longest non-increasing suffix(right end)
        // the reason for this step is that we want to change as little as possible from the left hand digits

        int N = arr.length;
        int suffixIndex = N-1;

        while(suffixIndex > 0 && arr[suffixIndex-1] >= arr[suffixIndex])  {
            suffixIndex--;
        }

        // have we reached last permutation?
        if(suffixIndex <= 0) {
            return;
        }

        // step2. Identify pivot
        // pivot is the element whose contents will be exchanged with the next largest in the suffix
        int pivotIndex = suffixIndex - 1;
        System.out.println("element at pivot = "+arr[pivotIndex]);

        // 3. find smallest element larger than pivot content, to the right of pivot
        int pivotSuccessorIndex = N-1;
        while(pivotSuccessorIndex > pivotIndex && arr[pivotSuccessorIndex] < arr[pivotIndex]) {
            pivotSuccessorIndex--;
        }
        System.out.println("pivotsuccessor = "+arr[pivotSuccessorIndex]);

        // 4. swap pivot contents and pivot successor contents.
        swap(arr, pivotIndex, pivotSuccessorIndex);

        // 5. reverse the suffix
        int endIndex = N-1;
        while (endIndex > suffixIndex) {
            swap(arr, suffixIndex, endIndex);
            endIndex--;
            suffixIndex++;
        }

        return;
    }

    static void swap(Integer[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
