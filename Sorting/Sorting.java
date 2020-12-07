import java.util.Comparator;
import java.util.LinkedList;
import java.util.Random;

/**
 * My Sorting Algorithms Implementations
 *
 * @author Sohum Gala
 * @version 1.0
 */

public class Sorting {

    /**
     * insertion sort.
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void insertionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new java.lang.IllegalArgumentException("input array or comparator cannot be null");
        }
        for (int i = 1; i < arr.length; i++) {
            int j = i - 1;
            T temp = arr[i];
            while (j >= 0 && comparator.compare(arr[j], temp) > 0) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = temp;
        }
    }

    /**
     * cocktail sort.
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void cocktailSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new java.lang.IllegalArgumentException("input array or comparator cannot be null");
        }
        boolean swapsMade = true;
        int startIndex = 0;
        int endIndex = arr.length - 1;
        while (swapsMade) {
            swapsMade = false;
            int stop = endIndex;
            for (int i = startIndex; i < stop; i++) {
                if (comparator.compare(arr[i], arr[i + 1]) > 0) {
                    T temp = arr[i];
                    arr[i] = arr[i + 1];
                    arr[i + 1] = temp;
                    swapsMade = true;
                    endIndex = i;
                }
            }
            if (swapsMade) {
                swapsMade = false;
                stop = startIndex;
                for (int j = endIndex; j > stop; j--) {
                    if (comparator.compare(arr[j - 1], arr[j]) > 0) {
                        T temp = arr[j];
                        arr[j] = arr[j - 1];
                        arr[j - 1] = temp;
                        swapsMade = true;
                        startIndex = j;
                    }
                }
            }
        }
    }

    /**
     * merge sort.
     * @param <T>        data type to sort
     * @param arr        the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new java.lang.IllegalArgumentException("input array or comparator cannot be null");
        }
        if (arr.length == 1 || arr.length == 0) {
            return;
        }
        int length = arr.length;
        int midInd = length / 2;
        T[] left = (T[]) new Object[midInd];
        for (int i = 0; i < midInd; i++) {
            left[i] = arr[i];
        }
        T[] right = (T[]) new Object[length - midInd];
        for (int i = 0; i < length - midInd; i++) {
            right[i] = arr[i + midInd];
        }
        mergeSort(left, comparator);
        mergeSort(right, comparator);
        int i = 0;
        int j = 0;
        while (i < left.length && j < right.length) {
            if (comparator.compare(left[i], right[j]) <= 0) {
                arr[i + j] = left[i];
                i++;
            } else {
                arr[i + j] = right[j];
                j++;
            }
        }
        while (i < left.length) {
            arr[i + j] = left[i];
            i++;
        }
        while (j < right.length) {
            arr[i + j] = right[j];
            j++;
        }
    }

    /**
     * LSD (least significant digit) radix sort.
     * @param arr the array to be sorted
     * @throws java.lang.IllegalArgumentException if the array is null
     */
    public static void lsdRadixSort(int[] arr) {
        if (arr == null) {
            throw new java.lang.IllegalArgumentException("cannot sort null array");
        }
        LinkedList<Integer>[] buckets = (LinkedList<Integer>[]) new LinkedList[19];
        for (int i = 0; i < 19; i++) {
            buckets[i] = new LinkedList<>();
        }
        int mod = 10;
        int div = 1;
        boolean cont = true;
        while (cont) {
            cont = false;
            for (int i = 0; i < arr.length; i++) {
                int bucket = arr[i] / div;
                if (bucket / 10 != 0) {
                    cont = true;
                }
                buckets[bucket % mod + 9].add(arr[i]);
            }
            int idx = 0;
            for (int i = 0; i < buckets.length; i++) {
                for (int num : buckets[i]) {
                    arr[idx++] = num;
                }
                buckets[i].clear();
            }
            div *= 10;
        }
    }

    /**
     * quick sort.
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @param rand       the Random object used to select pivots
     * @throws java.lang.IllegalArgumentException if the array or comparator or
     *                                            rand is null
     */
    public static <T> void quickSort(T[] arr, Comparator<T> comparator,
                                     Random rand) {
        if (arr == null || comparator == null || rand == null) {
            throw new java.lang.IllegalArgumentException("input array, comparator, or rand cannot be null");
        }
        quickSort(arr, 0, arr.length - 1, comparator, rand);
    }

    /**
     * private recursive helper method to perform quick sort
     * @param arr array being sorted
     * @param start starting index of iteration
     * @param end ending index of iteration
     * @param comparator Comparator used to compare data in the array
     * @param rand Random object used to select pivots
     * @param <T> data type to sort
     */
    private static <T> void quickSort(T[] arr, int start, int end, Comparator<T> comparator, Random rand) {
        if ((end - start) < 1) {
            return;
        }
        int pivotIndex = rand.nextInt(end - start + 1) + start;
        T pivotValue = arr[pivotIndex];
        arr[pivotIndex] = arr[start];
        arr[start] = pivotValue;
        int i = start + 1;
        int j = end;
        while (i <= j) {
            while (i <= j && comparator.compare(arr[i], pivotValue) <= 0) {
                i++;
            }
            while (i <= j && comparator.compare(arr[j], pivotValue) >= 0) {
                j--;
            }
            if (i <= j) {
                T temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
                i++;
                j--;
            }
        }
        T tmp = arr[start];
        arr[start] = arr[j];
        arr[j] = tmp;
        quickSort(arr, start, j - 1, comparator, rand);
        quickSort(arr, j + 1, end, comparator, rand);
    }
}
